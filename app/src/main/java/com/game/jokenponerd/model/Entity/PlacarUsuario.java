package com.game.jokenponerd.model.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "PlacarUsuario")

public class PlacarUsuario{

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_banco")
    private int id_banco;

    @ColumnInfo(name = "id_usuario")
    private String id_usuario;

    @ColumnInfo(name = "nome_usuario")
    private String nome_usuario;

    @ColumnInfo(name = "placar_usuario")
    private String placarUsuario;

    public int getId_banco() {
        return id_banco;
    }

    public void setId_banco(int id_banco) {
        this.id_banco = id_banco;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNome_usuario() {
        return nome_usuario;
    }

    public void setNome_usuario(String nome_usuario) {
        this.nome_usuario = nome_usuario;
    }

    public String getPlacarUsuario() {
        return placarUsuario;
    }

    public void setPlacarUsuario(String placarUsuario) {
        this.placarUsuario = placarUsuario;
    }
}
