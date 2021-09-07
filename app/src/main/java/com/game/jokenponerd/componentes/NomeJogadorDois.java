package com.game.jokenponerd.componentes;

import android.content.Context;

import com.game.jokenponerd.R;

import java.util.Random;


public class NomeJogadorDois {
    public String nomeJogadorDois(Context context) {
        int random = new Random().nextInt(10);
        String[] opcoes = context.getResources().getStringArray(R.array.nomes_jogador_dois);
        return opcoes[random];
    }
}
