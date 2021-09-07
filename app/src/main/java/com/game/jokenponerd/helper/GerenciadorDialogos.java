package com.game.jokenponerd.helper;

import android.app.Activity;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;

public class GerenciadorDialogos {

    public static AlertDialog buildAlertDialog(Activity activity,
                                               @StringRes int titleId,
                                               @StringRes int messageId,
                                               @StringRes int positiveButtonId,
                                               AlertDialog.OnClickListener onClickListenerSim,
                                               @StringRes int negativeButtonId,
                                               AlertDialog.OnClickListener onClickListenerNao) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setTitle(titleId);
        alertDialogBuilder.setMessage(messageId);
        alertDialogBuilder.setPositiveButton(positiveButtonId, onClickListenerSim);
        alertDialogBuilder.setNegativeButton(negativeButtonId, onClickListenerNao);
        alertDialogBuilder.setCancelable(true);

        return alertDialogBuilder.create();
    }
}
