package com.game.jokenponerd.view;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.game.jokenponerd.R;
import com.game.jokenponerd.model.Entity.PlacarUsuario;
import com.game.jokenponerd.model.repositorio.PlacarUsuarioRepositorio;
import com.game.jokenponerd.view.adapter.PlacarAdapter;
import com.game.jokenponerd.view.async.RecuperarPlacarUsuarioTask;

import java.util.List;


public class PlacarActivity extends AppCompatActivity implements RecuperarPlacarUsuarioTask.RecuperarDadosPlacar {

    private RecyclerView placarRecyclerView;
    private List<PlacarUsuario> getPlacarUsuario;
    private TextView txtMediaVitorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placar_activy);
        view();
    }

    private void view() {
        placarRecyclerView = findViewById(R.id.recycler_view_placar);
        txtMediaVitorias = findViewById(R.id.percentual_vitoria);

        recuperarDadosUsuario();
    }

    public void recuperarDadosUsuario(){
        PlacarUsuarioRepositorio placarUsuarioRepositorio = new PlacarUsuarioRepositorio(this);
        placarUsuarioRepositorio.recuperarPlacarUsuario(this,this);
    }

    @Override
    public void recuperarDadosPlacar(List<PlacarUsuario> placarUsuarios, Float mediaVitorias) {
        if(mediaVitorias != null){
            txtMediaVitorias.setText(String.valueOf(mediaVitorias));
        }
        if(placarUsuarios!=null){
            getPlacarUsuario = placarUsuarios;
            carregarAdapter();
        }
    }

    private void carregarAdapter(){
        PlacarAdapter placarAdapter = new PlacarAdapter(getPlacarUsuario);
        placarRecyclerView.setHasFixedSize(true);
        placarRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        placarRecyclerView.setAdapter(placarAdapter);
    }
}