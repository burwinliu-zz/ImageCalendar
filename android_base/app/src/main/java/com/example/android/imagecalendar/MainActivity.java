package com.example.android.imagecalendar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
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
}
