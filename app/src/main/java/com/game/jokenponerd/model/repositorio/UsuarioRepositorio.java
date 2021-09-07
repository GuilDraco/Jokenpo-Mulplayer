package com.game.jokenponerd.model.repositorio;

import android.content.Context;

import com.game.jokenponerd.model.Dao.UsuarioDao;
import com.game.jokenponerd.model.Entity.Usuario;
import com.game.jokenponerd.model.JokenpoNerdDataBase;
import com.game.jokenponerd.view.async.DeletarUsuariosTask;
import com.game.jokenponerd.view.async.SalvarUsuarioTask;


public class UsuarioRepositorio {
    private final UsuarioDao usuarioDao;

    public UsuarioRepositorio(Context context) {
        usuarioDao = JokenpoNerdDataBase.getInstance(context)
                .getUsuarioDao();
    }

    public void salvaDadosUsuario(Usuario usuario, SalvarUsuarioTask.SalvarDadosUsuario salvarDadosUsuario){
        if(usuario != null) {
            new SalvarUsuarioTask(usuario, usuarioDao, salvarDadosUsuario)
                    .execute();
        }
    }

    public void deletarUsuarios(){
        if(usuarioDao!=null){
            new DeletarUsuariosTask(usuarioDao).execute();
        }
    }
}
