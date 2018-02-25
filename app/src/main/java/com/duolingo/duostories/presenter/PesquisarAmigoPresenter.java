package com.duolingo.duostories.presenter;

import com.duolingo.duostories.model.entities.Usuario;
import com.duolingo.duostories.model.rest.SyncUsuarios;
import com.duolingo.duostories.model.rest.listener.UsuarioRESTListener;
import com.duolingo.duostories.model.utils.ImageUtils;
import com.duolingo.duostories.presenter.adapter.CursosAdapter;
import com.duolingo.duostories.view.PesquisarAmigoFragment;

/**
 * Created by leo on 24/02/18.
 */

public class PesquisarAmigoPresenter {
    private SyncUsuarios syncUsuarios;
    private PesquisarAmigoFragment view;

    public PesquisarAmigoPresenter(PesquisarAmigoFragment view){
        this.view = view;
        syncUsuarios = new SyncUsuarios();
    }

    public void pesquisarAmigo(String usuario){
        syncUsuarios.baixarUsuario(usuario, usuarioRESTListener);
    }

    private UsuarioRESTListener usuarioRESTListener = new UsuarioRESTListener() {
        @Override
        public void usuarioBaixado(Usuario usuario) {
            CursosAdapter cursosAdapter = new CursosAdapter(usuario.getCursos());
            view.exibirCursosDoUsuario(cursosAdapter);
            view.exibirDadosDoUsuario(usuario.getId(), usuario.getNome(), usuario.getBio(), usuario.getEmail(), usuario.getLocal());
            view.exibirScoresDoUsuario(usuario.getXp(), usuario.getMetaDiaria(), usuario.getOfensiva(), usuario.getLingots());
        }

        @Override
        public void fotoBaixada(String path) {
            view.exibirFotoDoUsuario(path);
        }

        @Override
        public void erro() {
            view.exibirMensagemErroConexao();
        }

        @Override
        public void erroUsuarioNaoExiste(String username) {
            view.exibirMensagemUsuarioNaoExiste(username);
        }
    };
}
