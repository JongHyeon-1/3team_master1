package com.icanerdogan.posedetection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;
import com.icanerdogan.posedetection.Helper.GraphicOverlay;
import com.icanerdogan.posedetection.Helper.RectOverlay;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.util.List;

import dmax.dialog.SpotsDialog;
public class MainActivityFace extends AppCompatActivity implements View.OnClickListener {
    CameraView cameraView;
    Button faceDetectButton;
    AlertDialog alertDialog;
    GraphicOverlay graphicOverlay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_face);

        ImageButton btn1 = findViewById(R.id.bm);
        cameraView = findViewById(R.id.camera);
        faceDetectButton = findViewById(R.id.detect);

        faceDetectButton.setOnClickListener(this);
        graphicOverlay = findViewById(R.id.graphic_overlay);

        btn1.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivityMaster.class);
            startActivity(intent);
        }); //object_detection2 클릭 리스너


        cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {
                showAlertDialogWithAutoDismiss();

                Bitmap bitmap = cameraKitImage.getBitmap();
                bitmap = Bitmap.createScaledBitmap(bitmap, cameraView.getWidth(), cameraView.getHeight(), false);
                cameraView.stop();

                detectFace(bitmap);

            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.detect:
                cameraView.start();
                cameraView.captureImage();
                graphicOverlay.clear();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraView.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cameraView.stop();
    }

    // Alert Dialog
    public void showAlertDialogWithAutoDismiss(){
        alertDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("잠시만 기다려 주십시오. 로드 중...")
                .setCancelable(false)
                .build();

        alertDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (alertDialog.isShowing()){
                    alertDialog.dismiss();
                    Toast.makeText(MainActivityFace.this, "얼굴이 감지되지 않음",Toast.LENGTH_LONG).show();
                    cameraView.start();
                }
            }
        }, 5000);
    }

    // Yüz Tespiti

    FaceDetectorOptions highAccuracyOpts =
            new FaceDetectorOptions.Builder()
                    .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
                    .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                    .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                    .build();

    private void detectFace(Bitmap bitmap){
        int rotationDegree = 0;

        InputImage image = InputImage.fromBitmap(bitmap, rotationDegree);

        FaceDetector detector = FaceDetection.getClient(highAccuracyOpts);

        detector.process(image)
                .addOnSuccessListener(
                        new OnSuccessListener<List<Face>>() {
                            @Override
                            public void onSuccess(List<Face> faces) {
                                drawFace(faces);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivityFace.this, "ERROR",Toast.LENGTH_SHORT).show();
                            }
                        });
    }

    // Çizim Fonksiyonu
    private void drawFace(List<Face> faces) {
        for (Face face: faces){
            Rect rect = face.getBoundingBox();
            RectOverlay rectOverlay = new RectOverlay(graphicOverlay, rect);
            graphicOverlay.add(rectOverlay);

            alertDialog.dismiss();
        }
    }



}