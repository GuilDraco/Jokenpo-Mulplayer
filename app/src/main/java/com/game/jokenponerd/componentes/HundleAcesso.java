package com.game.jokenponerd.componentes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.game.jokenponerd.helper.GerenciadorSharedPreferences;
import com.game.jokenponerd.view.JogoActivity;
import com.game.jokenponerd.view.LoginActivity;
import com.game.jokenponerd.view.SalasActivity;

public class HundleAcesso {

    private final Context context;
    private final Activity activity;

    public HundleAcesso(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void verificarAcesso(Handler handler) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(GerenciadorSharedPreferences.getNomeUsuario(context) == null){
                    mostrarLogin();
                }else {
                    mostrarJogo();
                }
            }
        }, 3000);
    }

    private void mostrarJogo() {
        Intent intent = new Intent(context, SalasActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    private void mostrarLogin() {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
}
