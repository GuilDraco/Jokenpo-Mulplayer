package com.game.jokenponerd.view.async;

import android.os.AsyncTask;

import com.game.jokenponerd.model.Dao.PlacarUsuarioDao;


public class DeletarPlacaresUsuarioTask extends AsyncTask<Void, Void, Void> {

    private final PlacarUsuarioDao placarUsuarioDao;

    public DeletarPlacaresUsuarioTask(PlacarUsuarioDao placarUsuarioDao) {
        this.placarUsuarioDao = placarUsuarioDao;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        placarUsuarioDao.deletePlacarUsuario();
        return null;
    }
}
