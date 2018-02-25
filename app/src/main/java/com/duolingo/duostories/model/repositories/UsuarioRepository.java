package com.duolingo.duostories.model.repositories;

import com.duolingo.duostories.model.entities.Usuario;

/**
 * Created by leo on 23/02/18.
 */

public class UsuarioRepository extends RealmRepository<Usuario> {

    public UsuarioRepository(){
        super(Usuario.class);
    }

}
