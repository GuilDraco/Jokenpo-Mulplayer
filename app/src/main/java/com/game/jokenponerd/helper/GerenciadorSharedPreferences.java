package com.game.jokenponerd.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.game.jokenponerd.view.SalasActivity;

import java.util.UUID;

import static android.content.Context.MODE_PRIVATE;

public class GerenciadorSharedPreferences {
    private final static String PREFS_SESSAO = "sessao";
    private static final String NOME_USUARIO = "nome_usuario";
    private static final String ID_USUARIO = "id_usuario";
    private static final String DIFICULDADE_NERD = "is_nerd";
    private static final String CRIOU_SALA = "criou_sala";
    private static final String JOGADA_JOGADOR_UM = "jogada_jogador_um";
    private static final String JOGADA_JOGADOR_DOIS = "jogada_jogador_dois";

    public static void setNomeUsuario(Context context, String nome_usuario) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_SESSAO, MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(NOME_USUARIO, nome_usuario);
        editor.apply();
    }

    public static String getNomeUsuario(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_SESSAO, MODE_PRIVATE);
        return prefs.getString(NOME_USUARIO, null);
    }

    public static void setIdUsuario(Context context, UUID id_usuario) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_SESSAO, MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ID_USUARIO, String.valueOf(id_usuario));
        editor.apply();
    }

    public static String getIdUsuario(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_SESSAO, MODE_PRIVATE);
        return prefs.getString(ID_USUARIO, null);
    }

    public static void setIsNerd(Context context, boolean is_nerd) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_SESSAO, MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(DIFICULDADE_NERD, is_nerd);
        editor.apply();
    }

    public static boolean getIsNerd(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_SESSAO, MODE_PRIVATE);
        return prefs.getBoolean(DIFICULDADE_NERD, false);
    }

    public static void setCriouSala(Context context, boolean criou_sala) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_SESSAO, MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(CRIOU_SALA, criou_sala);
        editor.apply();
    }

    public static boolean getCriouSala(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_SESSAO, MODE_PRIVATE);
        return prefs.getBoolean(CRIOU_SALA, false);
    }

    public static void setJogadaPrimeiroJogador(Context context, String jogada_jogador_um) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_SESSAO, MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(JOGADA_JOGADOR_UM, jogada_jogador_um);
        editor.apply();
    }

    public static String getJogadaPrimeiroJogador(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_SESSAO, MODE_PRIVATE);
        return prefs.getString(JOGADA_JOGADOR_UM, "");
    }

    public static void setJogadaSegundoJogador(Context context, String jogada_jogador_dois) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_SESSAO, MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(JOGADA_JOGADOR_DOIS, jogada_jogador_dois);
        editor.apply();
    }

    public static String getJogadaSegundoJogador(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_SESSAO, MODE_PRIVATE);
        return prefs.getString(JOGADA_JOGADOR_DOIS, "");
    }
}
