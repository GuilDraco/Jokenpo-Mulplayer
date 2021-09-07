package com.game.jokenponerd.view.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.game.jokenponerd.model.Dao.PlacarUsuarioDao;
import com.game.jokenponerd.model.Entity.PlacarUsuario;

import java.util.List;

import static com.game.jokenponerd.helper.Constantes.GANHOU;

public class RecuperarPlacarUsuarioTask extends AsyncTask<Void, Integer, List<PlacarUsuario>> {

    private final PlacarUsuarioDao placarUsuarioDao;
    private final RecuperarDadosPlacar recuperarDadosPlacar;
    private final ProgressDialog dialogoProgresso;
    private Float mediaVitorias;
    private Integer tVitorias = 0;
    private Integer contador = 0;

    public RecuperarPlacarUsuarioTask(Context context, PlacarUsuarioDao placarUsuarioDao, RecuperarDadosPlacar recuperarDadosPlacar) {
        this.placarUsuarioDao = placarUsuarioDao;
        this.recuperarDadosPlacar = recuperarDadosPlacar;

        dialogoProgresso = new ProgressDialog(context);
        dialogoProgresso.setTitle("Aguarde");
        dialogoProgresso.setMessage("Estamos calculando...");
        dialogoProgresso.setIndeterminate(false);
        dialogoProgresso.setMax(100);
        dialogoProgresso.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialogoProgresso.setCancelable(false);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        try {
            if (dialogoProgresso != null) {
                dialogoProgresso.show();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        dialogoProgresso.setProgress(progress[0]);
    }

    @Override
    protected List<PlacarUsuario> doInBackground(Void... voids) {
        List<PlacarUsuario> placar = placarUsuarioDao.getPlacarUsuario();
        if(placar!=null && placar.size() > 1){
            for(PlacarUsuario p: placar){
                if(p.getPlacarUsuario().equals(GANHOU)){
                    contador++;
                    tVitorias = tVitorias +1;
                    publishProgress((contador*100) / placar.size());
                }
            }
            mediaVitorias = (float) ((tVitorias*100)/placar.size());
        }
        return placarUsuarioDao.getPlacarUsuario();
    }

    @Override
    protected void onPostExecute(List<PlacarUsuario> placarUsuarios) {
        super.onPostExecute(placarUsuarios);
        try {
            if (dialogoProgresso != null) {
                dialogoProgresso.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        recuperarDadosPlacar.recuperarDadosPlacar(placarUsuarios, mediaVitorias);
    }

    public interface RecuperarDadosPlacar{
        void recuperarDadosPlacar(List<PlacarUsuario> placarUsuarios, Float mediaVitorias);
    }
}
