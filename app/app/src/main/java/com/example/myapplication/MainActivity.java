package com.example.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.view.SurfaceView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

// Begin user defined inports
import android.widget.Button;
import android.widget.ImageView;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.BitmapDrawable;
import android.content.Intent;

import java.util.NoSuchElementException;

public class MainActivity extends AppCompatActivity {
    Button btnTakePic;
    ImageView galleryPreview;
    SurfaceView cameraPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        galleryPreview = findViewById(R.id.galleryPreview);
        btnTakePic = findViewById(R.id.btnTakePic);
        cameraPreview = findViewById(R.id.cameraPreview);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //response functions
    public void seeGallery(View view){
        Intent galleryIntent = new Intent(this, GalleryActivity.class);
        startActivity(galleryIntent);
    }

    public void savePicture(View view){
    }

    // Setter functions
    private void scaleImage(ImageView view, int viewSize) throws NoSuchElementException{
        // Get whatever the view contains
        Bitmap bitmap;
        int width, height;

        try{
            Drawable drawing = view.getDrawable();
            bitmap = ((BitmapDrawable) drawing).getBitmap();
        }catch (NullPointerException e) {
            throw new NoSuchElementException("No drawable on given view");
        }
        try {
            width = bitmap.getWidth();
            height = bitmap.getHeight();
        } catch (NullPointerException e) {
            throw new NoSuchElementException("Can't find bitmap on given view/drawable");
        }


    }
}