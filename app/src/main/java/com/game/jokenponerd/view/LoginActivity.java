package com.game.jokenponerd.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.game.jokenponerd.R;
import com.game.jokenponerd.helper.GerenciadorSharedPreferences;
import com.game.jokenponerd.model.Entity.Usuario;
import com.game.jokenponerd.model.repositorio.UsuarioRepositorio;
import com.game.jokenponerd.view.async.SalvarUsuarioTask;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;


public class LoginActivity extends AppCompatActivity implements SalvarUsuarioTask.SalvarDadosUsuario{

    private TextInputLayout inputLayout;
    private TextInputEditText campoUsuario;
    private String nomeUsuario;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        view();
    }

    private void view() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        inputLayout = findViewById(R.id.inputLayout);
        campoUsuario = findViewById(R.id.editUser);
    }

    public void login(View view) {
        inputLayout.setError("");
        nomeUsuario = Objects.requireNonNull(campoUsuario.getText()).toString().trim();
        verificaPreenchimentoNomeUsuario();
    }

    private void verificaPreenchimentoNomeUsuario() {
        boolean estaPreenchido = true;
        if(nomeUsuario.isEmpty()){
            inputLayout.setError(getString(R.string.txt_erro_preencha_campo_usuario));
            estaPreenchido = false;
        }
        if(estaPreenchido){
            salvarNomeUsuario();
        }
    }

    private void salvarNomeUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNomeUsuario(nomeUsuario);
        usuario.setIdUsuario(UUID.randomUUID());
        salvarUsuario(usuario);
    }

    public void salvarUsuario(Usuario usuario){
        UsuarioRepositorio usuarioRepositorio = new UsuarioRepositorio(this);
        usuarioRepositorio.salvaDadosUsuario(usuario, this);
    }

    @Override
    public void salvarDadosUsuario(Usuario dadosUsuario) {
        GerenciadorSharedPreferences.setNomeUsuario(this, dadosUsuario.getNomeUsuario());
        GerenciadorSharedPreferences.setIdUsuario(this, dadosUsuario.getIdUsuario());

        databaseReference = firebaseDatabase.getReference("game_jokenpo/");
        databaseReference.child("Usuario")
                .child("nome_usuario: "+dadosUsuario.getNomeUsuario())
                .child("id_usuario: "+dadosUsuario.getIdUsuario())
                .setValue(true);

        verificaDadosBanco();
    }

    public void verificaDadosBanco(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Intent intent = new Intent(LoginActivity.this, SalasActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LoginActivity.this, R.string.txt_msg_erro_conexao, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void sair_app(View view) {
        finishAffinity();
    }
}