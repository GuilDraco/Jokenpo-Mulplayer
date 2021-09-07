package com.game.jokenponerd.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.game.jokenponerd.BuildConfig;
import com.game.jokenponerd.model.Dao.PlacarUsuarioDao;
import com.game.jokenponerd.model.Dao.UsuarioDao;
import com.game.jokenponerd.model.Entity.PlacarUsuario;
import com.game.jokenponerd.model.Entity.Usuario;

import static com.game.jokenponerd.model.HistoricoMigracao.TODAS_MIGRACOES;

@Database(entities = {PlacarUsuario.class, Usuario.class}, version = 1, exportSchema = false)

@TypeConverters({UUIDConverter.class})
public abstract class JokenpoNerdDataBase extends RoomDatabase {
    public abstract PlacarUsuarioDao getPlacarUsuarioDao();
    public abstract UsuarioDao getUsuarioDao();

    public static synchronized JokenpoNerdDataBase getInstance(Context context) {
        return Room
                .databaseBuilder(context, JokenpoNerdDataBase.class, BuildConfig.APPLICATION_ID+".bd")
                .addMigrations(TODAS_MIGRACOES)
                .build();
    }
}
