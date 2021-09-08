package com.game.jokenponerd.helper;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;

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

    public static AlertDialog buildAlertDialogRestart(Context context,
                                                      String titleId,
                                                      @StringRes int messageId,
                                                      @StringRes int positiveButtonId,
                                                      AlertDialog.OnClickListener onClickListenerSim) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(titleId);
        alertDialogBuilder.setMessage(messageId);
        alertDialogBuilder.setPositiveButton(positiveButtonId, onClickListenerSim);
        alertDialogBuilder.setCancelable(true);

        return alertDialogBuilder.create();
    }
}
