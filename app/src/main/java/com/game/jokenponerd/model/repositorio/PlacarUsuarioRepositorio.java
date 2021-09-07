package com.game.jokenponerd.model.repositorio;

import android.content.Context;

import com.game.jokenponerd.model.Dao.PlacarUsuarioDao;
import com.game.jokenponerd.model.Entity.PlacarUsuario;
import com.game.jokenponerd.model.JokenpoNerdDataBase;
import com.game.jokenponerd.view.async.DeletarPlacaresUsuarioTask;
import com.game.jokenponerd.view.async.RecuperarPlacarUsuarioTask;
import com.game.jokenponerd.view.async.SalvarPlacarTask;


public class PlacarUsuarioRepositorio {
    private final PlacarUsuarioDao placarUsuarioDao;

    public PlacarUsuarioRepositorio(Context context) {
        placarUsuarioDao = JokenpoNerdDataBase.getInstance(context)
                .getPlacarUsuarioDao();
    }

    public void salvaPlacarUsuario(PlacarUsuario placarUsuario, SalvarPlacarTask.SalvarDadosPlacar salvarDadosPlacar){
        if(placarUsuario != null) {
            new SalvarPlacarTask(placarUsuario, placarUsuarioDao, salvarDadosPlacar)
                    .execute();
        }
    }

    public void recuperarPlacarUsuario(Context context, RecuperarPlacarUsuarioTask.RecuperarDadosPlacar recuperarDadosPlacar){
        if(placarUsuarioDao!=null){
            new RecuperarPlacarUsuarioTask(context, placarUsuarioDao, recuperarDadosPlacar).execute();
        }
    }

    public void deletarPlacaresUsuario(){
        if(placarUsuarioDao!=null){
            new DeletarPlacaresUsuarioTask(placarUsuarioDao).execute();
        }
    }
}
