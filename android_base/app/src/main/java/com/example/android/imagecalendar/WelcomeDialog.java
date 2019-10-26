package com.example.android.imagecalendar;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import static android.support.v4.content.PermissionChecker.PERMISSION_DENIED;
import static android.support.v4.content.PermissionChecker.checkSelfPermission;


public class WelcomeDialog extends AppCompatDialogFragment {
    public static final int PERMISSION_REQUEST_CAMERA = 3217;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.howto_prompt, null);

        final Context c = getContext();

        builder.setView(view)
                .setPositiveButton("Start", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(checkSelfPermission(c, Manifest.permission.CAMERA)==PERMISSION_DENIED){
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
                        }
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 0);
                    }
//                    @Override
//                    public void onRequestPermissionsResult (int requestCode,
//                                                            String[] permissions,
//                                                            int[] grantResults){
//
//                    }

                });

        return builder.create();
    }

}
