package com.game.jokenponerd.componentes;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.game.jokenponerd.R;
import com.game.jokenponerd.helper.GerenciadorDialogos;
import com.game.jokenponerd.helper.GerenciadorSharedPreferences;
import com.game.jokenponerd.model.Entity.PlacarUsuario;
import com.game.jokenponerd.model.repositorio.PlacarUsuarioRepositorio;
import com.game.jokenponerd.view.async.SalvarPlacarTask;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.game.jokenponerd.helper.Constantes.GANHOU;
import static com.game.jokenponerd.helper.Constantes.LAGARTO;
import static com.game.jokenponerd.helper.Constantes.PAPEL;
import static com.game.jokenponerd.helper.Constantes.PEDRA;
import static com.game.jokenponerd.helper.Constantes.PERDEU;
import static com.game.jokenponerd.helper.Constantes.SPOCK;
import static com.game.jokenponerd.helper.Constantes.TESOURA;


public class RegraGanhador implements SalvarPlacarTask.SalvarDadosPlacar {

    protected RegraGanhador(Context context, FirebaseDatabase firebaseDatabase, String salaNome, ImageView getImageViewUm, ImageView getImageViewDois,
                            String jogadaPrimeiroJogador, String jogadaSegundoJogador, TextView getTextResultado, RadioGroup clearRadioGroup, RadioGroup clearRadioGroupNerd){
        regraGanhador(context, firebaseDatabase, salaNome, getImageViewUm, getImageViewDois, jogadaPrimeiroJogador, jogadaSegundoJogador, getTextResultado, clearRadioGroup, clearRadioGroupNerd);
    }

    private void regraGanhador(Context context, FirebaseDatabase firebaseDatabase, String salaNome, ImageView getImageViewJogadorUm, ImageView getImageViewJogadorDois, String jogadaPrimeiroJogador, String jogadaSegundoJogador, TextView getTextResultado,
                               RadioGroup clearRadioGroup, RadioGroup clearRadioGroupNerd) {
        //Player dois ganhador
        PlacarUsuario placarUsuario = new PlacarUsuario();

        if(salaNome!=null && jogadaPrimeiroJogador!=null && jogadaSegundoJogador!=null) {
            if ((jogadaSegundoJogador.equals(PEDRA) && (jogadaPrimeiroJogador.equals(TESOURA) || jogadaPrimeiroJogador.equals(LAGARTO)) ||
                    (jogadaSegundoJogador.equals(PAPEL) && (jogadaPrimeiroJogador.equals(PEDRA) || jogadaPrimeiroJogador.equals(SPOCK)) ||
                            (jogadaSegundoJogador.equals(TESOURA) && (jogadaPrimeiroJogador.equals(PAPEL) || jogadaPrimeiroJogador.equals(LAGARTO)) ||
                                    (jogadaSegundoJogador.equals(LAGARTO) && (jogadaPrimeiroJogador.equals(SPOCK) || jogadaPrimeiroJogador.equals(PAPEL)) ||
                                            (jogadaSegundoJogador.equals(SPOCK) && (jogadaPrimeiroJogador.equals(TESOURA) || jogadaPrimeiroJogador.equals(PEDRA)))))))) {

                if (!GerenciadorSharedPreferences.getCriouSala(context)) {
                    //getTextResultado.setText(R.string.txt_voce_ganhou);
                    dialogJogarNovament(context, firebaseDatabase, salaNome, getImageViewJogadorUm, getImageViewJogadorDois, "Você Ganhou! :)");
                    placarUsuario.setPlacarUsuario(GANHOU);
                } else {
                    //getTextResultado.setText(R.string.txt_voce_perdeu);
                    dialogJogarNovament(context, firebaseDatabase, salaNome, getImageViewJogadorUm, getImageViewJogadorDois, "Você perdeu! :(");
                    placarUsuario.setPlacarUsuario(PERDEU);
                }
                placarUsuario.setId_usuario(GerenciadorSharedPreferences.getIdUsuario(context));
                placarUsuario.setNome_usuario(GerenciadorSharedPreferences.getNomeUsuario(context));
                salvarPlacar(context, placarUsuario);

                clearRadioGroup.clearCheck();
                clearRadioGroupNerd.clearCheck();

                //Usuário ganhador
            } else if ((jogadaPrimeiroJogador.equals(PEDRA) && (jogadaSegundoJogador.equals(TESOURA) || jogadaSegundoJogador.equals(LAGARTO)) ||
                    (jogadaPrimeiroJogador.equals(PAPEL) && (jogadaSegundoJogador.equals(PEDRA) || jogadaSegundoJogador.equals(SPOCK)) ||
                            (jogadaPrimeiroJogador.equals(TESOURA) && (jogadaSegundoJogador.equals(PAPEL) || jogadaSegundoJogador.equals(LAGARTO)) ||
                                    (jogadaPrimeiroJogador.equals(LAGARTO) && (jogadaSegundoJogador.equals(SPOCK) || jogadaSegundoJogador.equals(PAPEL)) ||
                                            (jogadaPrimeiroJogador.equals(SPOCK) && (jogadaSegundoJogador.equals(TESOURA) || jogadaSegundoJogador.equals(PEDRA)))))))) {

                if (GerenciadorSharedPreferences.getCriouSala(context)) {
                    //getTextResultado.setText(R.string.txt_voce_ganhou);
                    dialogJogarNovament(context, firebaseDatabase, salaNome, getImageViewJogadorUm, getImageViewJogadorDois, "Você Ganhou! :)");
                    placarUsuario.setPlacarUsuario(GANHOU);
                } else {
                    //getTextResultado.setText(R.string.txt_voce_perdeu);
                    dialogJogarNovament(context, firebaseDatabase, salaNome, getImageViewJogadorUm, getImageViewJogadorDois, "Você perdeu! :(");
                    placarUsuario.setPlacarUsuario(PERDEU);
                }
                placarUsuario.setId_usuario(GerenciadorSharedPreferences.getIdUsuario(context));
                placarUsuario.setNome_usuario(GerenciadorSharedPreferences.getNomeUsuario(context));
                salvarPlacar(context, placarUsuario);

                clearRadioGroup.clearCheck();
                clearRadioGroupNerd.clearCheck();

                //Empate
            } else {
                //getTextResultado.setText(R.string.txt_empatou);
                dialogJogarNovament(context, firebaseDatabase, salaNome, getImageViewJogadorUm, getImageViewJogadorDois, "Empatou, vamos outra? :)");
                clearRadioGroup.clearCheck();
            }
        }
    }


    public void salvarPlacar(Context context, PlacarUsuario placarUsuario){
        PlacarUsuarioRepositorio placarUsuarioRepositorio = new PlacarUsuarioRepositorio(context);
        placarUsuarioRepositorio.salvaPlacarUsuario(placarUsuario, this);
    }

    @Override
    public void salvarDadosPlacar(PlacarUsuario dadosPlacar) {

    }

    public void dialogJogarNovament(Context context, FirebaseDatabase firebaseDatabase, String salaNome, ImageView getImageViewJogadorUm, ImageView getImageViewJogadorDois, String resultado) {
        AlertDialog.OnClickListener limparDadosJogada = (dialog, which) -> limparDadosJogador(firebaseDatabase, salaNome, getImageViewJogadorUm, getImageViewJogadorDois, dialog);

        AlertDialog alertDialog = GerenciadorDialogos.buildAlertDialogRestart(context,
                resultado,
                R.string.limpar_dados_jogada,
                R.string.txt_claro,
                limparDadosJogada);
        alertDialog.show();
    }

    public void limparDadosJogador(FirebaseDatabase firebaseDatabase, String salaNome, ImageView getImageViewJogadorUm, ImageView getImageViewJogadorDois, DialogInterface dialogInterface){
        DatabaseReference databaseReference1;
        DatabaseReference databaseReference2;

        databaseReference1 = firebaseDatabase.getReference("salas/" + salaNome + "/" + "player1" + "/jogada");
        databaseReference2 = firebaseDatabase.getReference("salas/" + salaNome +"/" +"player2"+ "/jogada");

        databaseReference1.setValue(null);
        databaseReference2.setValue(null);

        getImageViewJogadorUm.setImageResource(R.drawable.img_vazio);
        getImageViewJogadorDois.setImageResource(R.drawable.img_vazio);
        dialogInterface.dismiss();
    }
}
