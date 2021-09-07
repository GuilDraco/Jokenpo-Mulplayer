package com.game.jokenponerd.view.async;

import android.os.AsyncTask;

import com.game.jokenponerd.model.Dao.UsuarioDao;
import com.game.jokenponerd.model.Entity.Usuario;

public class SalvarUsuarioTask extends AsyncTask<Void, Void, Usuario> {

    private final Usuario usuario;
    private final UsuarioDao usuarioDao;
    private final SalvarDadosUsuario salvarDadosUsuario;

    public SalvarUsuarioTask(Usuario usuario, UsuarioDao usuarioDao, SalvarDadosUsuario salvarDadosUsuario) {
        this.usuario = usuario;
        this.usuarioDao = usuarioDao;
        this.salvarDadosUsuario = salvarDadosUsuario;
    }

    @Override
    protected Usuario doInBackground(Void... voids) {
        usuarioDao.salvarUsuario(usuario);
        salvarDadosUsuario.salvarDadosUsuario(usuario);
        return new Usuario();
    }

    @Override
    protected void onPostExecute(Usuario usuario) {
        super.onPostExecute(usuario);
    }

    public interface SalvarDadosUsuario{
        void salvarDadosUsuario(Usuario dadosUsuario);
    }
}

