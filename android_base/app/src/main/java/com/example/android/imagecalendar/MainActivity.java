package com.example.android.imagecalendar;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import static android.content.pm.PackageManager.PERMISSION_DENIED;

public class MainActivity extends AppCompatActivity implements WelcomeDialog.WelcomeDialogListener {
    public static final int PERMISSION_REQUEST_CAMERA = 3217;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openDialog();
        img = (ImageView) findViewById(R.id.image);

    }

    public void openDialog(){
        WelcomeDialog dialog = new WelcomeDialog();
        dialog.show(getSupportFragmentManager(), "example dialog");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap rawImage = (Bitmap) data.getExtras().get("data");
        img.setImageBitmap(rawImage);
    }

    @Override
    public void checkPermissionsCamera(){
        if(checkSelfPermission(Manifest.permission.CAMERA)==PERMISSION_DENIED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 0);
        }
    }
    @Override
    public void onRequestPermissionsResult (int requestCode, String[] permissions, int[] grantResults){
        checkPermissionsCamera();
    }

}
