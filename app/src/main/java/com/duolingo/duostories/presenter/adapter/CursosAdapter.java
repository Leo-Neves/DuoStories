package com.duolingo.duostories.presenter.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.duolingo.duostories.MyApplication;
import com.duolingo.duostories.R;
import com.duolingo.duostories.model.entities.Curso;

import io.realm.OrderedRealmCollection;
import io.realm.RealmList;

/**
 * Created by leo on 24/02/18.
 */

public class CursosAdapter extends RealmRecyclerViewAdapter<Curso> {

    public CursosAdapter(@NonNull RealmList<Curso> cursos) {
        super(cursos, false);
    }

    @Override
    protected RecyclerView.ViewHolder createHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(MyApplication.getActivity().getLayoutInflater().inflate(R.layout.row_cursos, parent, false));
    }

    @Override
    protected void showViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Curso curso = getItem(position);
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.nome.setText(curso.getIdioma());
        holder.xp.setText(String.valueOf(curso.getXp()));
        holder.logo.setImageResource(curso.getDrawableId());
    }

    @Override
    protected View getEmptyView() {
        return null;
    }

    private class ViewHolder extends RecyclerView.ViewHolder{
        TextView nome;
        TextView xp;
        ImageView logo;

        public ViewHolder(View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.tvCursoNome);
            xp = itemView.findViewById(R.id.tvCursoXp);
            logo = itemView.findViewById(R.id.ivCursoLogo);
        }
    }
}
