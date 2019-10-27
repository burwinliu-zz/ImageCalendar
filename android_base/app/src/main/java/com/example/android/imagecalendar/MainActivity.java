package com.example.android.imagecalendar;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferService;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.pm.PackageManager.PERMISSION_DENIED;

public class MainActivity extends AppCompatActivity implements WelcomeDialog.WelcomeDialogListener {
    public static final int PERMISSION_REQUEST_CAMERA = 3217;
    private AWSAppSyncClient mAWSAppSyncClient;
    private static final String TAG = MainActivity.class.getSimpleName();
    static final int REQUEST_TAKE_PHOTO = 1;
    File photo = null;

    private URI mImageUri;
    private String currentPhotoPath;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openDialog();
        getApplicationContext().startService(new Intent(getApplicationContext(), TransferService.class));

        img = (ImageView) findViewById(R.id.image);
        mAWSAppSyncClient = AWSAppSyncClient.builder()
                .context(getApplicationContext())
                .awsConfiguration(new AWSConfiguration(getApplicationContext()))
                .build();
        // Initialize the AWSMobileClient if not initialized
        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {
            @Override
            public void onResult(UserStateDetails userStateDetails) {
            }

            @Override
            public void onError(Exception e) {
            }
        });


    }

    public void openDialog(){
        WelcomeDialog dialog = new WelcomeDialog();
        dialog.show(getSupportFragmentManager(), "example dialog");

    }
//    private static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
//        Matrix matrix = new Matrix();
//        switch (orientation) {
//            case ExifInterface.ORIENTATION_NORMAL:
//                return bitmap;
//            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
//                matrix.setScale(-1, 1);
//                break;
//            case ExifInterface.ORIENTATION_ROTATE_180:
//                matrix.setRotate(180);
//                break;
//            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
//                matrix.setRotate(180);
//                matrix.postScale(-1, 1);
//                break;
//            case ExifInterface.ORIENTATION_TRANSPOSE:
//                matrix.setRotate(90);
//                matrix.postScale(-1, 1);
//                break;
//            case ExifInterface.ORIENTATION_ROTATE_90:
//                matrix.setRotate(90);
//                break;
//            case ExifInterface.ORIENTATION_TRANSVERSE:
//                matrix.setRotate(-90);
//                matrix.postScale(-1, 1);
//                break;
//            case ExifInterface.ORIENTATION_ROTATE_270:
//                matrix.setRotate(-90);
//                break;
//            default:
//                return bitmap;
//        }
//        try {
//            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//            bitmap.recycle();
//
//            return bmRotated;
//        } catch (OutOfMemoryError e) {
//            e.printStackTrace();
//            return null;
//        }
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
//        Bitmap rawImage = rotateBitmap(BitmapFactory.decodeFile(photo.getAbsolutePath()), );
        Bitmap rawImage = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(photo.getAbsolutePath()), 2048, 1536, true);
        img.setImageBitmap(rawImage);
//        uploadtos3(MainActivity.this, photo);
//        Log.d("MainActivity", currentPhotoPath);

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
//    public void uploadtos3(){
//        Regions clientRegion = Regions.US_WEST_1;
//        String bucketName = "*** Bucket name ***";
//        String stringObjKeyName = "*** String object key name ***";
//        String fileObjKeyName = "*** File object key name ***";
//        String fileName = currentPhotoPath;
//
//        try {
//            //This code expects that you have AWS credentials set up per:
//            // https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html
//            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
//                    .withRegion(clientRegion)
//                    .build();
//
//            // Upload a text string as a new object.
//            s3Client.putObject(bucketName, stringObjKeyName, "Uploaded String Object");
//
//            // Upload a file as a new object with ContentType and title specified.
//            PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, new File(fileName));
//            ObjectMetadata metadata = new ObjectMetadata();
//            metadata.setContentType("plain/text");
//            metadata.addUserMetadata("x-amz-meta-title", "someTitle");
//            request.setMetadata(metadata);
//            s3Client.putObject(request);
//        } catch (AmazonServiceException e) {
//            // The call was transmitted successfully, but Amazon S3 couldn't process
//            // it, so it returned an error response.
//            e.printStackTrace();
//        }
//
//    }
//    public static void uploadtos3 (final Context context, final File file) {
//
//        if(file !=null){
//            CognitoCachingCredentialsProvider credentialsProvider;
//            credentialsProvider = new CognitoCachingCredentialsProvider(
//                    context,
//                    "your identity pool id", // Identity Pool ID
//                    Regions.US_WEST_1 // Region
//            );
//
//            AmazonS3 s3 = new AmazonS3Client(credentialsProvider);
//
//
//            TransferUtility transferUtility = new TransferUtility(s3, context);
//            final TransferObserver observer = transferUtility.upload(
//                    "bucket name",  //this is the bucket name on S3
//                    file.getName(),
//                    file,
//                    CannedAccessControlList.PublicRead //to make the file public
//            );
//            observer.setTransferListener(new TransferListener() {
//                @Override
//                public void onStateChanged(int id, TransferState state) {
//                    if (state.equals(TransferState.COMPLETED)) {
//                    } else if (state.equals(TransferState.FAILED)) {
//                        Toast.makeText(context,"Failed to upload",Toast.LENGTH_LONG).show();
//                    }
//
//                }
//
//                @Override
//                public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
//
//                }
//
//                @Override
//                public void onError(int id, Exception ex) {
//
//                }
//            });
//        }
//    }
//    public void uploadWithTransferUtility() {
//
//        TransferUtility transferUtility =
//                TransferUtility.builder()
//                        .context(getApplicationContext())
//                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
//                        .s3Client(new AmazonS3Client(AWSMobileClient.getInstance().getCredentialsProvider()))
//                        .build();
//
//        String imageName = "";
////        TransferObserver uploadObserver =
////                transferUtility.upload(
////                        "public/"+currentPhotoPath, photo);
//        TransferObserver uploadObserver =
//                transferUtility.upload(
//                        "public/test.jpg", photo);
//
//        // Attach a listener to the observer to get state update and progress notifications
//        uploadObserver.setTransferListener(new TransferListener() {
//
//            @Override
//            public void onStateChanged(int id, TransferState state) {
//                if (TransferState.COMPLETED == state) {
//                    // Handle a completed upload.
//                }
//            }
//
//            @Override
//            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
//                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
//                int percentDone = (int)percentDonef;
//
//                Log.d("YourActivity", "ID:" + id + " bytesCurrent: " + bytesCurrent
//                        + " bytesTotal: " + bytesTotal + " " + percentDone + "%");
//            }
//
//            @Override
//            public void onError(int id, Exception ex) {
//                // Handle errors
//            }
//
//        });
//
//        // If you prefer to poll for the data, instead of attaching a
//        // listener, check for the state and progress in the observer.
//        if (TransferState.COMPLETED == uploadObserver.getState()) {
//            // Handle a completed upload.
//        }
//
//        Log.d("YourActivity", "Bytes Transferrred: " + uploadObserver.getBytesTransferred());
//        Log.d("YourActivity", "Bytes Total: " + uploadObserver.getBytesTotal());
//    }


    @Override
    public void checkPermissionsCamera(){
        if(checkSelfPermission(Manifest.permission.CAMERA)==PERMISSION_DENIED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, 0);
            if (intent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                try {
                    photo = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File
                }
                // Continue only if the File was successfully created
                if (photo != null) {
                    Uri photoURI = FileProvider.getUriForFile(this,
                            "com.example.android.imagecalendar",
                            photo);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(intent, REQUEST_TAKE_PHOTO);
                }
            }
        }
    }
    @Override
    public void onRequestPermissionsResult (int requestCode, String[] permissions, int[] grantResults){
        checkPermissionsCamera();
    }

}
