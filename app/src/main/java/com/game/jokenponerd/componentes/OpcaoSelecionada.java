package com.game.jokenponerd.componentes;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.game.jokenponerd.R;
import com.game.jokenponerd.helper.GerenciadorSharedPreferences;
import com.game.jokenponerd.model.Jogada.Jogada;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.game.jokenponerd.helper.Constantes.LAGARTO;
import static com.game.jokenponerd.helper.Constantes.PAPEL;
import static com.game.jokenponerd.helper.Constantes.PEDRA;
import static com.game.jokenponerd.helper.Constantes.SPOCK;
import static com.game.jokenponerd.helper.Constantes.TESOURA;


public class OpcaoSelecionada {

    public void opcaoSelecionada(Context context, String escolhaJogador, ImageView getImageViewJogadorUm, ImageView getImageViewJogadorDois,
                                 TextView getTextViewResultado, RadioGroup radioGroup, RadioGroup radioGroupNerd, FirebaseDatabase firebaseDatabase, String salaNome) {

        apresentaEscolhaJogador(context, escolhaJogador, getImageViewJogadorUm, getImageViewJogadorDois, firebaseDatabase, salaNome, getTextViewResultado, radioGroup, radioGroupNerd);

    }

    private void apresentaEscolhaJogador(Context context, String escolhaJogador, ImageView getImageViewJogadorUm, ImageView getImageViewJogadorDois, FirebaseDatabase firebaseDatabase, String salaNome, TextView getTextViewResultado, RadioGroup radioGroup, RadioGroup radioGroupNerd) {
        DatabaseReference databaseReference;
        if(GerenciadorSharedPreferences.getCriouSala(context)){
            escolhaJogador(escolhaJogador, getImageViewJogadorUm);
            databaseReference = firebaseDatabase.getReference("salas/" + salaNome + "/" + "player1" + "/jogada");
            databaseReference.setValue(escolhaJogador);

            recuperaJogada(context, firebaseDatabase, salaNome, getImageViewJogadorDois, "player2", getTextViewResultado, radioGroup, radioGroupNerd);
        }else {
            escolhaJogador(escolhaJogador, getImageViewJogadorDois);
            databaseReference = firebaseDatabase.getReference("salas/" + salaNome +"/" +"player2"+ "/jogada");
            databaseReference.setValue(escolhaJogador);

            recuperaJogada(context, firebaseDatabase, salaNome, getImageViewJogadorUm, "player1", getTextViewResultado, radioGroup, radioGroupNerd);
        }
    }

    private void recuperaJogada(Context context, FirebaseDatabase firebaseDatabase, String salaNome, ImageView getImageView, String player, TextView getTextViewResultado, RadioGroup radioGroup, RadioGroup radioGroupNerd) {

        DatabaseReference ref = firebaseDatabase.getReference("salas/"+salaNome+"/"+player);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Jogada jog = dataSnapshot.getValue(Jogada.class);
                if(jog.getJogada()!=null){
                    escolhaJogador(jog.getJogada(), getImageView);
                    new RegraGanhador(context, getTextViewResultado, radioGroup, radioGroupNerd, salaNome);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private void escolhaJogador(String escolhaJogador, ImageView getImage) {
        switch (escolhaJogador){
            case PEDRA:
                getImage.setImageResource(R.drawable.img_pedra);
                break;
            case PAPEL:
                getImage.setImageResource(R.drawable.img_papel);
                break;
            case TESOURA:
                getImage.setImageResource(R.drawable.img_tesoura);
                break;
            case LAGARTO:
                getImage.setImageResource(R.drawable.img_lagarto);
                break;
            case SPOCK:
                getImage.setImageResource(R.drawable.img_spock);
                break;
        }

    }

    /*private void escolhaJogadorDois(ImageView getImage, String escolhaJogadorDois) {
        switch (escolhaJogadorDois){
            case PEDRA:
                getImage.setImageResource(R.drawable.img_pedra);
                break;
            case PAPEL:
                getImage.setImageResource(R.drawable.img_papel);
                break;
            case TESOURA:
                getImage.setImageResource(R.drawable.img_tesoura);
                break;
            case LAGARTO:
                getImage.setImageResource(R.drawable.img_lagarto);
                break;
            case SPOCK:
                getImage.setImageResource(R.drawable.img_spock);
                break;
        }
    }*/

    /*private String brainJogadorDois(Context context) {
        int random = new Random().nextInt(3);
        String[] opcoes = context.getResources().getStringArray(R.array.opcoes);
        return opcoes[random];
    }

    private String brainJogadorDoisNerd(Context context) {
        int random = new Random().nextInt(5);
        String[] opcoes = context.getResources().getStringArray(R.array.opcoesNerd);
        return opcoes[random];
    }*/
}
