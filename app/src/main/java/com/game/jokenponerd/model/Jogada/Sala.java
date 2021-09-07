package com.game.jokenponerd.model.Jogada;

import java.util.HashMap;

public class Sala {
    private HashMap<String, String> player1;
    private HashMap<String, String> player2;

    public HashMap<String, String> getPlayer1() {
        return player1;
    }

    public void setPlayer1(HashMap<String, String> player1) {
        this.player1 = player1;
    }

    public HashMap<String, String> getPlayer2() {
        return player2;
    }

    public void setPlayer2(HashMap<String, String> player2) {
        this.player2 = player2;
    }
}
