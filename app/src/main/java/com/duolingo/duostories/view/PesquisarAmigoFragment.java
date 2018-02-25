package com.duolingo.duostories.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.duolingo.duostories.R;
import com.duolingo.duostories.presenter.PesquisarAmigoPresenter;
import com.duolingo.duostories.presenter.adapter.CursosAdapter;
import com.duolingo.duostories.view.base.BaseFragment;
import com.duolingo.duostories.view.contents.ContentUsuario;

import br.agr.terras.aurora.log.Logger;
import br.agr.terras.materialdroid.SearchView;

/**
 * Created by leo on 23/02/18.
 */

public class PesquisarAmigoFragment extends BaseFragment{
    private PesquisarAmigoPresenter presenter;
    private ContentUsuario contentUsuario;
    private TextView tvUsuarioNaoExiste;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView==null){
            configurarComponentes(inflater, container);
            configurarPresenter();
            configurarSearchView();
        }
        return rootView;
    }

    private void configurarComponentes(LayoutInflater inflater, ViewGroup container){
        rootView = inflater.inflate(R.layout.fragment_pesquisar_amigos, container, false);
        contentUsuario = new ContentUsuario(rootView);
        tvUsuarioNaoExiste = rootView.findViewById(R.id.tvUsuarioNaoExiste);
        searchView = rootView.findViewById(R.id.svAmigos);
    }

    private void configurarPresenter() {
        presenter = new PesquisarAmigoPresenter(this);
    }

    private void configurarSearchView() {
        searchView.setOnSearchListener(new SearchView.OnSearchListener() {
            @Override
            public void onSearchAction(String currentQuery) {
                Logger.d("Pesquisando agora: %s", currentQuery);
                presenter.pesquisarAmigo(currentQuery);
            }
        });
    }

    public void exibirDadosDoUsuario(String username, String nome, String bio, String email, String local) {
        contentUsuario.setId(username);
        contentUsuario.setNome(nome);
        contentUsuario.setTvBio(bio);
        contentUsuario.setEmail(email);
        contentUsuario.setLocal(local);
    }

    public void exibirScoresDoUsuario(int xp, int metaDiaria, int diasDeOfensiva, int lingots) {
        contentUsuario.setXp(xp);
        contentUsuario.setOfensiva(diasDeOfensiva);
        contentUsuario.setLingots(lingots);
    }

    public void exibirFotoDoUsuario(String path) {
        contentUsuario.setFoto(path);
    }

    public void exibirMensagemErroConexao() {
        contentUsuario.setVisibility(false);
        tvUsuarioNaoExiste.setVisibility(View.VISIBLE);
    }

    public void exibirMensagemUsuarioNaoExiste(String username) {
        contentUsuario.setVisibility(false);
        tvUsuarioNaoExiste.setVisibility(View.VISIBLE);
        tvUsuarioNaoExiste.setText(String.format(getString(R.string.usuario_nao_encontrado), username));
    }

    public void exibirCursosDoUsuario(CursosAdapter cursosAdapter) {
        contentUsuario.setCursosAdapter(cursosAdapter);
    }
}
