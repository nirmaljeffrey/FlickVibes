package com.nirmal.jeffrey.flickvibes.emotiondetector;

import android.graphics.Bitmap;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.nirmal.jeffrey.flickvibes.util.Constants;

public class EmotionDetector {

  public static FirebaseVisionImage detectFacesFromImage(Bitmap bitmap) {
    return FirebaseVisionImage.fromBitmap(bitmap);
  }


  public static FirebaseVisionFaceDetector getDetector() {
    FirebaseVisionFaceDetectorOptions options = new FirebaseVisionFaceDetectorOptions.Builder()
        .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
        .setPerformanceMode(FirebaseVisionFaceDetectorOptions.ACCURATE)
        .build();
    return FirebaseVision
        .getInstance().getVisionFaceDetector(options);
  }


  public static Emotions getEmotions(FirebaseVisionFace face) {

    if (face.getSmilingProbability() != FirebaseVisionFace.UNCOMPUTED_PROBABILITY) {

      boolean happy = Constants.HAPPINESS_PROB <= face.getSmilingProbability();
      boolean sad = Constants.SADNESS_PROB >= face.getSmilingProbability();
      boolean normal =
          ((Constants.HAPPINESS_PROB > face.getSmilingProbability()) && (face.getSmilingProbability()
              > Constants.SADNESS_PROB));
      if (normal) {
        return Emotions.NORMAL;
      }else if (happy) {
        return Emotions.HAPPY;
      } else if (sad) {
        return Emotions.SAD;
      }
    }
    return null;
  }


}
