package com.duolingo.duostories.model.entities;

import com.duolingo.duostories.MyApplication;
import com.duolingo.duostories.R;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by leo on 23/02/18.
 */

public class Curso extends RealmObject{
    @PrimaryKey
    private String id;
    private String linguaNativa;
    private String idioma;
    private String codigo;
    private int xp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLinguaNativa() {
        return linguaNativa;
    }

    public void setLinguaNativa(String linguaNativa) {
        this.linguaNativa = linguaNativa;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getDrawableId(){
        int drawable_id = MyApplication.getActivity().getResources().getIdentifier("curso_"+codigo,"drawable","com.duolingo.duostories");
        return (drawable_id==0) ? R.drawable.curso_de : drawable_id;
    }
}
