package com.duolingo.duostories.model.rest.listener;

import com.duolingo.duostories.model.entities.Usuario;

/**
 * Created by leo on 23/02/18.
 */

public interface UsuarioRESTListener {
    void usuarioBaixado(Usuario usuario);
    void fotoBaixada(String path);
    void erro();
    void erroUsuarioNaoExiste(String username);
}
