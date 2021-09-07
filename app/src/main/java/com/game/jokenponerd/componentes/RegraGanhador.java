package com.game.jokenponerd.componentes;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.game.jokenponerd.R;
import com.game.jokenponerd.helper.GerenciadorSharedPreferences;
import com.game.jokenponerd.model.Entity.PlacarUsuario;
import com.game.jokenponerd.model.Jogada.Jogada;
import com.game.jokenponerd.model.Jogada.Sala;
import com.game.jokenponerd.model.repositorio.PlacarUsuarioRepositorio;
import com.game.jokenponerd.view.async.SalvarPlacarTask;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.game.jokenponerd.helper.Constantes.GANHOU;
import static com.game.jokenponerd.helper.Constantes.LAGARTO;
import static com.game.jokenponerd.helper.Constantes.PAPEL;
import static com.game.jokenponerd.helper.Constantes.PEDRA;
import static com.game.jokenponerd.helper.Constantes.PERDEU;
import static com.game.jokenponerd.helper.Constantes.SPOCK;
import static com.game.jokenponerd.helper.Constantes.TESOURA;


public class RegraGanhador implements SalvarPlacarTask.SalvarDadosPlacar {

    private String jogadaPrimeiroJogador = "";
    private String jogadaSegundoJogador = "";

    protected RegraGanhador(Context context, TextView getTextResultado, RadioGroup clearRadioGroup, RadioGroup clearRadioGroupNerd, String salaNome){
        recuperaJogada(context, salaNome, getTextResultado, clearRadioGroup, clearRadioGroupNerd);
    }

    private void recuperaJogada(Context context, String salaNome, TextView getTextResultado, RadioGroup clearRadioGroup, RadioGroup clearRadioGroupNerd) {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = firebaseDatabase.getReference("salas/"+salaNome);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Sala jog = dataSnapshot.getValue(Sala.class);
                if(jog.getPlayer1()!=null){
                    jogadaPrimeiroJogador = jog.getPlayer1().get("jogada");
                    jogadaSegundoJogador = jog.getPlayer2().get("jogada");
                }
                regraGanhador(context, jogadaPrimeiroJogador, jogadaSegundoJogador, getTextResultado, clearRadioGroup, clearRadioGroupNerd);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private void regraGanhador(Context context, String jogadaPrimeiroJogador, String jogadaSegundoJogador, TextView getTextResultado, RadioGroup clearRadioGroup, RadioGroup clearRadioGroupNerd) {
        //Player dois ganhador
        PlacarUsuario placarUsuario = new PlacarUsuario();

        if((jogadaSegundoJogador.equals(PEDRA) && (jogadaPrimeiroJogador.equals(TESOURA) || jogadaPrimeiroJogador.equals(LAGARTO)) ||
                (jogadaSegundoJogador.equals(PAPEL) && (jogadaPrimeiroJogador.equals(PEDRA) || jogadaPrimeiroJogador.equals(SPOCK)) ||
                (jogadaSegundoJogador.equals(TESOURA) && (jogadaPrimeiroJogador.equals(PAPEL) || jogadaPrimeiroJogador.equals(LAGARTO)) ||
                (jogadaSegundoJogador.equals(LAGARTO) && (jogadaPrimeiroJogador.equals(SPOCK) || jogadaPrimeiroJogador.equals(PAPEL)) ||
                (jogadaSegundoJogador.equals(SPOCK) && (jogadaPrimeiroJogador.equals(TESOURA) || jogadaPrimeiroJogador.equals(PEDRA)))))))){

            if(!GerenciadorSharedPreferences.getCriouSala(context)){
                getTextResultado.setText(R.string.txt_voce_ganhou);
                placarUsuario.setPlacarUsuario(GANHOU);
                placarUsuario.setId_usuario(GerenciadorSharedPreferences.getIdUsuario(context));
                placarUsuario.setNome_usuario(GerenciadorSharedPreferences.getNomeUsuario(context));
                salvarPlacar(context, placarUsuario);
            }else {
                getTextResultado.setText(R.string.txt_voce_perdeu);
                placarUsuario.setPlacarUsuario(PERDEU);
                placarUsuario.setId_usuario(GerenciadorSharedPreferences.getIdUsuario(context));
                placarUsuario.setNome_usuario(GerenciadorSharedPreferences.getNomeUsuario(context));
                salvarPlacar(context, placarUsuario);
            }

            clearRadioGroup.clearCheck();
            clearRadioGroupNerd.clearCheck();

        //Usuário ganhador
        }else if((jogadaPrimeiroJogador.equals(PEDRA) && (jogadaSegundoJogador.equals(TESOURA) || jogadaSegundoJogador.equals(LAGARTO)) ||
                (jogadaPrimeiroJogador.equals(PAPEL) && (jogadaSegundoJogador.equals(PEDRA) || jogadaSegundoJogador.equals(SPOCK)) ||
                (jogadaPrimeiroJogador.equals(TESOURA) && (jogadaSegundoJogador.equals(PAPEL) || jogadaSegundoJogador.equals(LAGARTO)) ||
                (jogadaPrimeiroJogador.equals(LAGARTO) && (jogadaSegundoJogador.equals(SPOCK) || jogadaSegundoJogador.equals(PAPEL)) ||
                (jogadaPrimeiroJogador.equals(SPOCK) && (jogadaSegundoJogador.equals(TESOURA) || jogadaSegundoJogador.equals(PEDRA)))))))){

            if(GerenciadorSharedPreferences.getCriouSala(context)){
                getTextResultado.setText(R.string.txt_voce_ganhou);
                placarUsuario.setPlacarUsuario(GANHOU);
                placarUsuario.setId_usuario(GerenciadorSharedPreferences.getIdUsuario(context));
                placarUsuario.setNome_usuario(GerenciadorSharedPreferences.getNomeUsuario(context));
                salvarPlacar(context, placarUsuario);
            }else {
                getTextResultado.setText(R.string.txt_voce_perdeu);
                placarUsuario.setPlacarUsuario(PERDEU);
                placarUsuario.setId_usuario(GerenciadorSharedPreferences.getIdUsuario(context));
                placarUsuario.setNome_usuario(GerenciadorSharedPreferences.getNomeUsuario(context));
                salvarPlacar(context, placarUsuario);
            }

            clearRadioGroup.clearCheck();
            clearRadioGroupNerd.clearCheck();

        //Empate
        }else {
            getTextResultado.setText(R.string.txt_empatou);
            clearRadioGroup.clearCheck();
        }
    }

    public void salvarPlacar(Context context, PlacarUsuario placarUsuario){
        PlacarUsuarioRepositorio placarUsuarioRepositorio = new PlacarUsuarioRepositorio(context);
        placarUsuarioRepositorio.salvaPlacarUsuario(placarUsuario, this);
    }

    @Override
    public void salvarDadosPlacar(PlacarUsuario dadosPlacar) {

    }
}
