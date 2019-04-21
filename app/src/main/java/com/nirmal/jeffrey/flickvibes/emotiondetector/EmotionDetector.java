package com.nirmal.jeffrey.flickvibes.emotiondetector;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import java.util.List;

public class EmotionDetector {

  private static final String TAG = "EmotionDetector";


  public static void detectFaces(Context context, Bitmap bitmap) {

    FirebaseVisionFaceDetectorOptions options = new FirebaseVisionFaceDetectorOptions.Builder()
        .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
        .setPerformanceMode(FirebaseVisionFaceDetectorOptions.ACCURATE)
        .build();

    FirebaseVisionImage image =FirebaseVisionImage.fromBitmap(bitmap);
    FirebaseVisionFaceDetector detector = FirebaseVision.getInstance().getVisionFaceDetector(options);
    Task<List<FirebaseVisionFace>> result= detector.detectInImage(image).addOnSuccessListener(
        firebaseVisionFaces -> {

          if(firebaseVisionFaces.size()==0){
            Toast.makeText(context.getApplicationContext(), "No faces detected", Toast.LENGTH_SHORT).show();

          }

          if(firebaseVisionFaces.size()>1){

            Toast.makeText(context.getApplicationContext(), "More than one face detected", Toast.LENGTH_SHORT).show();

          }else if (firebaseVisionFaces.size()==1){
            FirebaseVisionFace face = firebaseVisionFaces.get(0);
                  getClassification(face);


          }
        })
        .addOnFailureListener(e -> {
          e.printStackTrace();
          Toast.makeText(context.getApplicationContext(), "Face detection failed", Toast.LENGTH_SHORT).show();

        });


  }

  private static void getClassification(FirebaseVisionFace face){
    float smilingProb;
    if(face.getSmilingProbability()!=FirebaseVisionFace.UNCOMPUTED_PROBABILITY){
      smilingProb =face.getSmilingProbability();
      Log.d(TAG, "getClassification: smiling prob "+ smilingProb);

    }
  }

  private enum Emotions{
    HAPPY,
    SAD,
    NORMAL
  }




}
