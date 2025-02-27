package com.example.opencvtest;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.view.PreviewView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ingenieriiajhr.jhrCameraX.BitmapResponse;
import com.ingenieriiajhr.jhrCameraX.CameraJhr;
import com.ingenieriiajhr.jhrCameraX.ImageProxyResponse;

import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity {

    CameraJhr cameraJhr;
    ImageView imgBitmap;
    PreviewView previewImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        if(OpenCVLoader.initDebug()) Log.d("OPEN2023CV","SUCCESS");
        else Log.d("OPEN2023CV","ERROR");
        //init cameraJHR
        cameraJhr = new CameraJhr(this);
        imgBitmap = findViewById(R.id.imgBitmap);
        previewImg = findViewById(R.id.previewImg);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (cameraJhr.allpermissionsGranted() && !cameraJhr.getIfStartCamera()){
            startCameraJhr();
        }else{
            cameraJhr.noPermissions();
        }
    }

    private void startCameraJhr() {
        cameraJhr.addlistenerBitmap(new BitmapResponse() {
            @Override
            public void bitmapReturn(@Nullable Bitmap bitmap) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imgBitmap.setImageBitmap(bitmap);
                    }
                });

            }
        });

        cameraJhr.initBitmap();
        cameraJhr.start(0, 0, previewImg, true, false, true);
    }
}