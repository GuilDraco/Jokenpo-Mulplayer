package com.game.jokenponerd.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.game.jokenponerd.R;
import com.game.jokenponerd.helper.GerenciadorSharedPreferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.game.jokenponerd.helper.Constantes.JOGADOR_DOIS;
import static com.game.jokenponerd.helper.Constantes.SALA_NOME;

public class SalasActivity extends AppCompatActivity {

    private ListView listViewSalas;
    private List<String> salasList;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference roomRef;

    private String salaNome;
    private String nomeJogador;
    private String jogador_dois;

    private Button criarSala;
    private TextView txtServidoresVazios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salas);

        Views();
        addRoomsEventListener();
        selecionarSala();
    }

    private void Views() {
        listViewSalas = findViewById(R.id.listViewSalas);
        criarSala = findViewById(R.id.btn_entrar_sala);
        txtServidoresVazios = findViewById(R.id.txt_servidores_vazios);
        firebaseDatabase = FirebaseDatabase.getInstance();
        nomeJogador = GerenciadorSharedPreferences.getNomeUsuario(this);
        salaNome = nomeJogador;
        salasList = new ArrayList<>();
    }

    public void criar_sala(View view) {
        if(!GerenciadorSharedPreferences.getCriouSala(this)) {
            GerenciadorSharedPreferences.setCriouSala(this, true);
            roomRef = firebaseDatabase.getReference("salas/" + salaNome + "/player1" + "/nome");
            roomRef.setValue(nomeJogador);
            irParaJogo();
        }
    }

    private void addRoomsEventListener() {
        DatabaseReference roomsRef = firebaseDatabase.getReference("salas");
        roomsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                salasList.clear();
                Iterable<DataSnapshot> rooms = snapshot.getChildren();
                for (DataSnapshot snapshots : rooms) {
                    salasList.add(snapshots.getKey());
                }
                ArrayAdapter<String> adapterSalas = new ArrayAdapter<>(SalasActivity.this, android.R.layout.simple_list_item_1, salasList);
                if(!adapterSalas.isEmpty()){
                    listViewSalas.setAdapter(adapterSalas);
                    criarSala.setVisibility(View.GONE);
                    txtServidoresVazios.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void selecionarSala(){
        listViewSalas.setOnItemClickListener((parent, view, position, id) -> {
            salaNome = salasList.get(position);
            if(!GerenciadorSharedPreferences.getCriouSala(this)) {
                roomRef = firebaseDatabase.getReference("salas/" + salaNome + "/player2" + "/nome");
                roomRef.setValue(nomeJogador);
            }
            recuperaNomeSegundoJogador();
        });
    }
    private void recuperaNomeSegundoJogador() {
        DatabaseReference roomsRef = firebaseDatabase.getReference();
        roomsRef.child("salas").child(salaNome).child("player2").child("nome").get().addOnCompleteListener(task -> {
            jogador_dois = String.valueOf(task.getResult().getValue());
            irParaJogo();
        });
    }

    public void irParaJogo() {
        Intent intent = new Intent(SalasActivity.this, JogoActivity.class);
        intent.putExtra(SALA_NOME, salaNome);
        intent.putExtra(JOGADOR_DOIS, jogador_dois);
        startActivity(intent);
        finish();
    }

}