package com.duolingo.duostories.presenter.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import br.agr.terras.aurora.log.Logger;
import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollection;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmResults;
import kotlin.jvm.Throws;

/**
 * Created by leo on 10/05/17.
 */

public abstract class RealmRecyclerViewAdapter<T extends RealmModel>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int EMPTY_LIST_TYPE = 56497812;
    private boolean hasAutoUpdates;
    private OrderedRealmCollectionChangeListener listener;
    private RecyclerView recyclerView;
    @NonNull
    private OrderedRealmCollection<T> adapterData;

    private OrderedRealmCollectionChangeListener createListener() {
        return new OrderedRealmCollectionChangeListener() {
            @Override
            public void onChange(Object collection, OrderedCollectionChangeSet changeSet) {
                if (hasAutoUpdates) {
                    // null Changes means the async query returns the first time.
                    if (changeSet == null) {
                        notifyDataSetChanged();
                        return;
                    }
                    // For deletions, the adapter has to be notified in reverse order.
                    OrderedCollectionChangeSet.Range[] deletions = changeSet.getDeletionRanges();
                    for (int i = deletions.length - 1; i >= 0; i--) {
                        OrderedCollectionChangeSet.Range range = deletions[i];
                        notifyItemRangeRemoved(range.startIndex, range.length);
                    }

                    OrderedCollectionChangeSet.Range[] insertions = changeSet.getInsertionRanges();
                    for (OrderedCollectionChangeSet.Range range : insertions) {
                        notifyItemRangeInserted(range.startIndex, range.length);
                    }

                    OrderedCollectionChangeSet.Range[] modifications = changeSet.getChangeRanges();
                    for (OrderedCollectionChangeSet.Range range : modifications) {
                        notifyItemRangeChanged(range.startIndex, range.length);
                    }
                }
            }
        };
    }

    public RealmRecyclerViewAdapter(@NonNull OrderedRealmCollection<T> data, boolean autoUpdate) {
        String errorUnmanaged = "Only use this adapter with managed RealmCollection, " +
                "for un-managed lists you can just use the RealmRecyclerViewAdapter";
        if (!data.isManaged())
            if (autoUpdate)
                throw new IllegalStateException(errorUnmanaged);
            else
                Logger.w(errorUnmanaged);
        this.adapterData = data;
        this.hasAutoUpdates = autoUpdate;
        this.listener = hasAutoUpdates ? createListener() : null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==EMPTY_LIST_TYPE && getEmptyView()!=null)
        return new EmptyViewHolder(getEmptyView());
        return createHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        if (!adapterData.isEmpty() || getEmptyView()==null)
            showViewHolder(viewHolder, position);
    }

    @Override
    public int getItemViewType(int position){
        if (getEmptyView()!=null && adapterData.isEmpty())
            return EMPTY_LIST_TYPE;
        return getViewType(position);
    }

    protected RecyclerView.ViewHolder createHolder(ViewGroup parent, int viewType){
        return null;
    }

    protected void showViewHolder(RecyclerView.ViewHolder viewHolder, int position){}

    protected int getViewType(int position){ return 0;}

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
        if (hasAutoUpdates && isDataValid()) {
            //noinspection ConstantConditions
            addListener(adapterData);
        }
    }

    @Override
    public void onDetachedFromRecyclerView(final RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        if (isDataValid() && listener!=null) {
            //noinspection ConstantConditions
            removeListener(adapterData);
        }
    }

    /**
     * Returns the current ID for an item. Note that item IDs are not stable so you cannot rely on the item ID being the
     * same after notifyDataSetChanged() or {@link #updateData(OrderedRealmCollection)} has been called.
     *
     * @param index position of item in the adapter.
     * @return current item ID.
     */
    @Override
    public long getItemId(final int index) {
        return index;
    }

    @Override
    public int getItemCount() {
        //noinspection ConstantConditions
        boolean hasEmptyView = getEmptyView()!=null;
        return isDataValid() ? adapterData.isEmpty()?hasEmptyView?1:0:adapterData.size() : hasEmptyView?1:0;
    }

    /**
     * Returns the item associated with the specified position.
     * Can return {@code null} if provided Realm instance by {@link OrderedRealmCollection} is closed.
     *
     * @param index index of the item.
     * @return the item at the specified position, {@code null} if adapter data is not valid.
     */
    @SuppressWarnings("WeakerAccess")
    public T getItem(int index) {
        //noinspection ConstantConditions
        return isDataValid() ? adapterData.get(index) : null;
    }

    /**
     * Returns data associated with this adapter.
     *
     * @return adapter data.
     */
    @NonNull
    public OrderedRealmCollection<T> getData() {
        return adapterData;
    }

    /**
     * Updates the data associated to the Adapter. Useful when the query has been changed.
     * If the query does not change you might consider using the automaticUpdate feature.
     *
     * @param data the new {@link OrderedRealmCollection} to display.
     */
    @SuppressWarnings("WeakerAccess")
    public void updateData(@Nullable OrderedRealmCollection<T> data) {
        if (hasAutoUpdates) {
            if (isDataValid()) {
                //noinspection ConstantConditions
                removeListener(adapterData);
            }
            if (data != null) {
                addListener(data);
            }
        }

        this.adapterData = data;
        notifyDataSetChanged();
    }

    public void setAutoUpdate(boolean hasAutoUpdates){
        this.hasAutoUpdates = hasAutoUpdates;
        if (listener==null && hasAutoUpdates) {
            listener = createListener();
            addListener(adapterData);
        }
    }

    public RecyclerView getRecyclerView(){
        return recyclerView;
    }

    private void addListener(@NonNull OrderedRealmCollection<T> data) {
        if (data instanceof RealmResults) {
            RealmResults<T> results = (RealmResults<T>) data;
            //noinspection unchecked
            results.addChangeListener(listener);
        } else if (data instanceof RealmList) {
            RealmList<T> list = (RealmList<T>) data;
            //noinspection unchecked
            list.addChangeListener(listener);
        } else {
            throw new IllegalArgumentException("RealmCollection not supported: " + data.getClass());
        }
    }

    private void removeListener(@NonNull OrderedRealmCollection<T> data) {
        if (data instanceof RealmResults) {
            RealmResults<T> results = (RealmResults<T>) data;
            //noinspection unchecked
            results.removeChangeListener(listener);
        } else if (data instanceof RealmList) {
            RealmList<T> list = (RealmList<T>) data;
            //noinspection unchecked
            list.removeChangeListener(listener);
        } else {
            throw new IllegalArgumentException("RealmCollection not supported: " + data.getClass());
        }
    }

    private boolean isDataValid() {
        return adapterData.isValid();
    }

    /**
     * Returns a view to show if the list is empty.
     *
     * @return view of empty list.
     */
    protected View getEmptyView(){
        return null;
    }

    private class EmptyViewHolder extends RecyclerView.ViewHolder{

        EmptyViewHolder(View itemView) {
            super(itemView);

        }
    }
}