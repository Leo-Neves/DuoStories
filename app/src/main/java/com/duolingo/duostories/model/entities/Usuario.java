package com.duolingo.duostories.model.entities;

import java.util.Locale;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by leo on 23/02/18.
 */

public class Usuario extends RealmObject{
    @PrimaryKey
    private String id;
    private String nome;
    private String email;
    private String bio;
    private String local;
    private int xp;
    private int lingots;
    private int ofensiva;
    private int metaDiaria;
    private boolean plus;
    private String imagemUrl;
    private RealmList<Curso> cursos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getLingots() {
        return lingots;
    }

    public void setLingots(int lingots) {
        this.lingots = lingots;
    }

    public int getOfensiva() {
        return ofensiva;
    }

    public void setOfensiva(int ofensiva) {
        this.ofensiva = ofensiva;
    }

    public int getMetaDiaria() {
        return metaDiaria;
    }

    public void setMetaDiaria(int metaDiaria) {
        this.metaDiaria = metaDiaria;
    }

    public boolean isPlus() {
        return plus;
    }

    public void setPlus(boolean plus) {
        this.plus = plus;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public RealmList<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(RealmList<Curso> cursos) {
        this.cursos = cursos;
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH,"Nome: %s\n" +
                "Id: %s\n" +
                "Email: %s\n" +
                "Bio: %s\n" +
                "Local: %s\n" +
                "Xp: %d\n" +
                "Lingots: %d\n" +
                "Meta diária: %d\n" +
                "Ofensiva: %d\n" +
                "isPlus: %s\n" +
                "FotoURL: %s\n" +
                "Total de cursos: %d",nome, id, email, bio, local, xp, lingots, metaDiaria, ofensiva, plus, imagemUrl, cursos!=null?cursos.size():0);
    }
}
