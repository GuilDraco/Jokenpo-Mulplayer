package com.game.jokenponerd.view.async;

import android.os.AsyncTask;

import com.game.jokenponerd.model.Dao.UsuarioDao;


public class DeletarUsuariosTask extends AsyncTask<Void, Void, Void> {

    private final UsuarioDao usuarioDao;

    public DeletarUsuariosTask(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        usuarioDao.deleteUsuarios();
        return null;
    }
}
