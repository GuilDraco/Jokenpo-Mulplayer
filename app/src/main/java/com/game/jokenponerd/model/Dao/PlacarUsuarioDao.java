package com.game.jokenponerd.model.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.game.jokenponerd.model.Entity.PlacarUsuario;

import java.util.List;


@Dao
public interface PlacarUsuarioDao {

    @Insert
    void salvarPlacarUsuario(PlacarUsuario placarUsuario);

    @Query("Select * from PlacarUsuario")
    List<PlacarUsuario> getPlacarUsuario();

    //Todo retorna todos os usuários caso precise da lista completa Obs:
    //todo Não deletar placar ao sair do apk.
    //@Query("SELECT* FROM PlacarUsuario WHERE id_usuario = :id")
    //List<PlacarUsuario> selectFotoUsuario(int id);

    @Query("DELETE FROM PlacarUsuario")
    void deletePlacarUsuario();
}
