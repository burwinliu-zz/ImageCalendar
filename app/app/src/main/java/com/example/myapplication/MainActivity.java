package com.example.myapplication;

import android.graphics.Bitmap;

import android.graphics.SurfaceTexture;

import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;

import androidx.appcompat.app.AppCompatActivity;

import android.view.TextureView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Button;
import android.widget.ImageView;

import android.content.Intent;

import android.util.Log;

public class MainActivity extends AppCompatActivity {
    //Threads
    private HandlerThread mBackgroundThread;
    private Handler mBackgroundHandler;

    // Apps Elements
    Button btnTakePic;
    ImageView galleryPreview;
    TextureView cameraPreview;

    // Camera variables
    CameraDevice camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        galleryPreview =    findViewById(R.id.galleryPreview);
        btnTakePic =        findViewById(R.id.btnTakePic);
        cameraPreview =     findViewById(R.id.cameraPreview);



        assert galleryPreview   != null;
        assert btnTakePic       != null;
        assert cameraPreview    != null;
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

    // Housekeeping
    // Setting up the TextureView
    TextureView.SurfaceTextureListener listener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };

    // Setting up recalling of state (for camera capture)
    private final CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice camera) {
            //This is called when the camera is open
            Log.e("AndroidCameraApi", "onOpened");
            camera = camera;
            createCameraPreview();
        }

        @Override
        public void onDisconnected(CameraDevice camera) {
            camera.close();
        }

        @Override
        public void onError(CameraDevice camera, int error) {
            camera.close();
            camera = null;
        }
    };

    //Setting up for capturing the recalling of the camera device
    final CameraCaptureSession.CaptureCallback captureCallbackListener = new CameraCaptureSession.CaptureCallback() {
        @Override
        public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {

        }
    };

    protected void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("Camera Background");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }

    protected void stopBackgroundThread() {
        mBackgroundThread.quitSafely();
        try {
            mBackgroundThread.join();
            mBackgroundThread = null;
            mBackgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //response functions
    public void seeGallery(View view){
        Intent galleryIntent = new Intent(this, GalleryActivity.class);
        startActivity(galleryIntent);
    }

    public void savePicture(View view){

    }

    // helper functions
    private void openCamera(){

    }

    private void createCameraPreview(){}


    private Bitmap scaleImage(Bitmap image, int viewWidth, int viewHeight){
        // Get whatever the view contains
        double ratio;
        Bitmap result;
        if(image.getWidth() < viewWidth || image.getHeight() < viewHeight){
            if(viewWidth < viewHeight){
                ratio = viewHeight/image.getHeight();
            }
            else{
                ratio = viewWidth/image.getWidth();
            }
            result = Bitmap.createScaledBitmap(image, (int)(image.getWidth()*ratio), (int)(image.getHeight()*ratio), false);
        } else{
            result = Bitmap.createBitmap(image);
        }


        if (result.getWidth() >= result.getHeight()){
            return Bitmap.createBitmap(
                    result,
                    result.getWidth()/2 - result.getHeight()/2,
                    0,
                    result.getHeight(),
                    result.getHeight()
            );

        }else{

            return Bitmap.createBitmap(
                    result,
                    0,
                    result.getHeight()/2 - result.getWidth()/2,
                    result.getWidth(),
                    result.getWidth()
            );
        }
    }


}