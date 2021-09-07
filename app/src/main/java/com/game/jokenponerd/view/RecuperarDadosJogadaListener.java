package com.game.jokenponerd.view;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.game.jokenponerd.model.Entity.PlacarUsuario;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public interface RecuperarDadosJogadaListener {
    void recuperarDadosJogada(Context context, PlacarUsuario placarUsuario, String escolhaJogador, ImageView getImageViewJogadorUm, ImageView getImageViewJogadorDois,
                              TextView getTextView, RadioGroup radioGroup, RadioGroup radioGroupNerd, DatabaseReference databaseReference, FirebaseDatabase firebaseDatabase, String salaNome);
}
