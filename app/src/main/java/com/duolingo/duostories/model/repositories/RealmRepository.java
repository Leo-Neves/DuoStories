package com.duolingo.duostories.model.repositories;

import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.UUID;

import br.agr.terras.materialdroid.utils.RandomString;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.exceptions.RealmMigrationNeededException;

/**
 * Created by leo on 31/05/16.
 */
public class RealmRepository<T extends RealmObject> {
    protected Realm realm;
    protected Class<T> clazz;

    public RealmRepository(Class<T> clazz) {
        this.clazz = clazz;
        init();
    }

    public RealmRepository() {
        init();
    }

    private void init() {
        try {
            realm = Realm.getDefaultInstance();
        } catch (RealmMigrationNeededException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void salvar(final T object) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(object);
            }
        });
    }

    public void salvarSemTransaction(T object) {
        realm.copyToRealmOrUpdate(object);
    }

    public void deleteAllEntities() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.removeAllChangeListeners();
        realm.commitTransaction();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    public T novo(String chavePrimaria) {
        if (chavePrimaria == null)
            return realm.createObject(clazz);
        else
            return realm.createObject(clazz, chavePrimaria);
    }

    public void atualizarObjeto(AtualizacaoThread atualizacaoThread) {
        realm.beginTransaction();
        atualizacaoThread.podeAtualizar();
        realm.commitTransaction();
    }

    public void cancelarTransactions() {
        if (realm.isInTransaction())
            realm.cancelTransaction();
    }

    public T procurarPorId(String id) {
        return realm.where(clazz).equalTo("id", id).findFirst();
    }

    public void deletar(String id) {
        realm.beginTransaction();
        procurarPorId(id).deleteFromRealm();
        realm.commitTransaction();
    }

    public RealmResults<T> procurarTodos() {
        return realm.where(clazz).findAll();
    }

    public interface AtualizacaoThread {
        void podeAtualizar();
    }

}
