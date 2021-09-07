package com.game.jokenponerd.view;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.game.jokenponerd.R;
import com.game.jokenponerd.componentes.HundleAcesso;


public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Handler handler = new Handler();

        HundleAcesso hundleAcesso = new HundleAcesso(this, SplashScreenActivity.this);
        hundleAcesso.verificarAcesso(handler);
    }
}