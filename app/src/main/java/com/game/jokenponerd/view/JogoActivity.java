package com.game.jokenponerd.view;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.game.jokenponerd.R;
import com.game.jokenponerd.componentes.NomeJogadorDois;
import com.game.jokenponerd.componentes.OpcaoSelecionada;
import com.game.jokenponerd.helper.GerenciadorDialogos;
import com.game.jokenponerd.helper.GerenciadorSharedPreferences;
import com.game.jokenponerd.model.Jogada.Jogada;
import com.game.jokenponerd.model.repositorio.PlacarUsuarioRepositorio;
import com.game.jokenponerd.model.repositorio.UsuarioRepositorio;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import static com.game.jokenponerd.helper.Constantes.JOGADOR_DOIS;
import static com.game.jokenponerd.helper.Constantes.LAGARTO;
import static com.game.jokenponerd.helper.Constantes.PAPEL;
import static com.game.jokenponerd.helper.Constantes.PEDRA;
import static com.game.jokenponerd.helper.Constantes.SALA_NOME;
import static com.game.jokenponerd.helper.Constantes.SPOCK;
import static com.game.jokenponerd.helper.Constantes.TESOURA;


public class JogoActivity extends AppCompatActivity{

    private final OpcaoSelecionada opcaoSelecionada = new OpcaoSelecionada();
    private ImageView getImageViewJogadorDois, getImageViewJogadorUm;
    private TextView getTextView;
    private RadioGroup radioGroup;
    private RadioGroup radioGroupNerd;
    private Button btnJokenpo;
    private String escolhaJogador;
    private ConstraintLayout constraintLayoutNerd;

    private FirebaseDatabase firebaseDatabase;

    private String nomeSegundoJogador;
    private String salaNome;
    private String jogador_um;
    private TextView nomeJogarUm;
    private TextView nomeJogadorDois;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jogo_activity);
        firebaseDatabase = FirebaseDatabase.getInstance();
        verificarBundle();
    }
//
    private void verificarBundle() {
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            nomeSegundoJogador = bundle.getString(JOGADOR_DOIS);
            salaNome = bundle.getString(SALA_NOME);
        }
        recuperaNomePrimeiroJogador();
    }

    private void recuperaNomePrimeiroJogador() {
        if(salaNome!=null) {
            DatabaseReference roomsRef = firebaseDatabase.getReference();
            roomsRef.child("salas").child(salaNome).child("player1").child("nome").get().addOnCompleteListener(task -> {
                jogador_um = String.valueOf(Objects.requireNonNull(task.getResult()).getValue());
                recuperaNomeSegundoJogador();
            });
        }
    }

    private void recuperaNomeSegundoJogador() {
        if(salaNome!=null) {
            DatabaseReference roomsRef = firebaseDatabase.getReference();
            roomsRef.child("salas").child(salaNome).child("player2").child("nome").get().addOnCompleteListener(task -> {
                nomeSegundoJogador = String.valueOf(Objects.requireNonNull(task.getResult()).getValue());
                if (nomeSegundoJogador.equals("null")) {
                    nomeSegundoJogador = casoJogadorDoisNull();
                }
                view(jogador_um, nomeSegundoJogador);
            });
        }
    }

    public String casoJogadorDoisNull(){
        NomeJogadorDois nomeJogadorDois = new NomeJogadorDois();
        return nomeJogadorDois.nomeJogadorDois(this);
    }

    public void verificaNomeJogadorListener(){
        if(GerenciadorSharedPreferences.getCriouSala(this)){
            addRoomsEventListener("player1", nomeJogarUm);
        }else {
            addRoomsEventListener("player2", nomeJogadorDois);
        }
    }

    private void addRoomsEventListener(String player, TextView tvJogador) {
        if(salaNome!=null) {
            DatabaseReference ref = firebaseDatabase.getReference("salas/" + salaNome + "/" + player);
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Jogada nomeJogador = dataSnapshot.getValue(Jogada.class);
                    assert nomeJogador != null;
                    tvJogador.setText(nomeJogador.getNome());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }
    }

    private void view(String nomeUsuario, String nomeSegundoJogador) {
        firebaseDatabase = FirebaseDatabase.getInstance();

        getImageViewJogadorUm = findViewById(R.id.img_jogador_um);
        nomeJogarUm = findViewById(R.id.txt_jogador_1);
        nomeJogarUm.setText(nomeUsuario);

        getImageViewJogadorDois = findViewById(R.id.img_jogador_dois);
        nomeJogadorDois = findViewById(R.id.txt_jogador_2);
        nomeJogadorDois.setText(nomeSegundoJogador);

        getTextView = findViewById(R.id.txt_resultado);
        radioGroup = findViewById(R.id.areaRadioGroup);
        radioGroupNerd = findViewById(R.id.areaRadioGroupNerd);
        constraintLayoutNerd = findViewById(R.id.constraintLayoutNerd);

        btnJokenpo = findViewById(R.id.btn_jokenpo);
        setOpcaoSelecionada();

        verificaDificuldade();
        verificaNomeJogadorListener();
        jokenpo();
    }

    private void verificaDificuldade() {
        if(GerenciadorSharedPreferences.getIsNerd(this)) {
            constraintLayoutNerd.setVisibility(View.VISIBLE);
            setOpcaoSelecionadaNerd();
        }else {
            constraintLayoutNerd.setVisibility(View.GONE);
        }
    }

    private void setOpcaoSelecionadaNerd() {
        radioGroupNerd.setOnCheckedChangeListener((group, checkedId) -> {
            if(checkedId==R.id.radioButtonLagarto){
                escolhaJogador = LAGARTO;
            }else {
                escolhaJogador = SPOCK;
            }
        });
    }

    public void setOpcaoSelecionada(){
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {

            if(checkedId==R.id.radioButtonPedra){
                escolhaJogador = PEDRA;
            }else if(checkedId==R.id.radioButtonPapel){
                escolhaJogador = PAPEL;
            }else if(checkedId==R.id.radioButtonTesoura){
                escolhaJogador = TESOURA;
            }else if(checkedId==R.id.radioButtonLagarto){
                escolhaJogador = LAGARTO;
            }else if(checkedId==R.id.radioButtonSpock){
                escolhaJogador = SPOCK;
            }
        });
    }

    public void jokenpo(){
        btnJokenpo.setOnClickListener(v -> {
            if(getImageViewJogadorUm !=null && getImageViewJogadorDois !=null && escolhaJogador !=null) {

                opcaoSelecionada.opcaoSelecionada(JogoActivity.this, escolhaJogador, getImageViewJogadorUm, getImageViewJogadorDois, getTextView,
                        radioGroup, radioGroupNerd, firebaseDatabase, salaNome);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null) {

            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.menu_placar, menu);

            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(R.string.app_name);

            ColorDrawable corFundo = new ColorDrawable(this.getColor(R.color.colorPrimary));
            actionBar.setBackgroundDrawable(corFundo);
            actionBar.setElevation(0);

        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.acao_nerd);
        if(!GerenciadorSharedPreferences.getIsNerd(this)){
            item.setTitle(R.string.txt_habilitar_nerd);
        }else {
            item.setTitle(R.string.txt_desabilitar_nerd);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //sairTelaJogo();
        finishAffinity();
    }

    public void sairTelaJogo(){
        if(salaNome!=null) {
            DatabaseReference databaseReference;
            if (GerenciadorSharedPreferences.getCriouSala(this)) {
                databaseReference = firebaseDatabase.getReference("salas/" + salaNome + "/" + "player1" + "/jogada");
            } else {
                databaseReference = firebaseDatabase.getReference("salas/" + salaNome + "/" + "player2" + "/jogada");
            }
            databaseReference.setValue(null);
        }
    }

    public void mostrarPlacar(MenuItem item) {
        Intent intent = new Intent(this, PlacarActivity.class);
        startActivity(intent);
    }

    public void sairAplicativo(MenuItem item) {
        AlertDialog.OnClickListener ficarAplicativo = (dialog, which) -> dialog.dismiss();
        AlertDialog.OnClickListener sairAplicativo = (dialog, which) -> apagarDadosBanco();
        AlertDialog alertDialog = GerenciadorDialogos.buildAlertDialog(this,
                R.string.aviso_sair_apk,
                R.string.txt_sair_apk,
                R.string.txt_sair_sim,
                sairAplicativo,
                R.string.txt_nao_sair,
                ficarAplicativo);
        alertDialog.show();
    }

    public void habilitarVersaoNerd(MenuItem item) {
        GerenciadorSharedPreferences.setIsNerd(this, !GerenciadorSharedPreferences.getIsNerd(this));
        Intent intent = new Intent(this, JogoActivity.class);
        startActivity(intent);
        finish();
    }

    public void apagarDadosBanco(){
        GerenciadorSharedPreferences.setNomeUsuario(this, null);
        GerenciadorSharedPreferences.setIsNerd(this, false);

        UsuarioRepositorio usuarioRepositorio = new UsuarioRepositorio(this);
        usuarioRepositorio.deletarUsuarios();

        PlacarUsuarioRepositorio placarUsuarioRepositorio = new PlacarUsuarioRepositorio(this);
        placarUsuarioRepositorio.deletarPlacaresUsuario();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        sairTelaJogo();
    }
}