package com.example.android.imagecalendar;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;



public class WelcomeDialog extends AppCompatDialogFragment {
    private WelcomeDialogListener listener;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.howto_prompt, null);

        builder.setView(view)
                .setPositiveButton("Start", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.checkPermissionsCamera();
                    }
                });

        return builder.create();
    }

    public interface WelcomeDialogListener {
        void checkPermissionsCamera();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            listener = (WelcomeDialogListener) context;
        } catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement WelcomeDialogListener");
        }
    }
}
