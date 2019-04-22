package com.nirmal.jeffrey.flickvibes.ui.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.nirmal.jeffrey.flickvibes.R;
import com.nirmal.jeffrey.flickvibes.emotiondetector.EmotionDetector;
import com.nirmal.jeffrey.flickvibes.emotiondetector.Emotions;
import com.nirmal.jeffrey.flickvibes.ui.fragment.EmotionResultFragment;
import com.nirmal.jeffrey.flickvibes.util.BitmapUtils;
import com.nirmal.jeffrey.flickvibes.util.Constants;
import java.io.FileNotFoundException;
import java.util.List;

public class MoviePredictionActivity extends BaseActivity {

  @BindView(R.id.face_image_view)
  ImageView faceImageView;
  @BindView(R.id.clear_button)
  FloatingActionButton clearButton;
  @BindView(R.id.detect_face_button)
  CardView detectFaceButton;

  private static final String TAG = "MoviePredictionActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movie_prediction);
    ButterKnife.bind(this);
    initClearButton();
    Bitmap bitmap = getIncomingIntent();
    initDetectFaceButton(bitmap);
  }

  private Bitmap getIncomingIntent() {
    Bitmap bitmap=null;
    if (getIntent() != null) {

      if (getIntent().hasExtra(Constants.CAMERA_ACTIVITY_INTENT)) {
        String cameraUri = getIntent().getStringExtra(Constants.CAMERA_ACTIVITY_INTENT);
        bitmap = BitmapUtils.resamplePic(this, cameraUri);
        faceImageView.setImageBitmap(bitmap);
      } else if (getIntent().hasExtra(Constants.GALLERY_ACTIVITY_INTENT)) {
        Uri imageUri = getIntent().getParcelableExtra(Constants.GALLERY_ACTIVITY_INTENT);
        try {
          bitmap = BitmapUtils.resamplePic(this, imageUri);
          faceImageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }


      }


    }
    return bitmap;
  }

  private void initClearButton(){
    clearButton.setOnClickListener(
        view -> finish());
  }
  private void initDetectFaceButton(Bitmap bitmap){
    detectFaceButton.setOnClickListener(
        view -> detectEmotions(MoviePredictionActivity.this, bitmap));
  }

  private void detectEmotions(Context context,Bitmap bitmap) {
    if (bitmap != null) {
      FirebaseVisionFaceDetector detector = EmotionDetector.getDetector();
      FirebaseVisionImage image = EmotionDetector.detectFacesFromImage(bitmap);

      Task<List<FirebaseVisionFace>> result = detector.detectInImage(image).addOnSuccessListener(
          firebaseVisionFaces -> {

            if (firebaseVisionFaces.size() == 0) {
              Toast.makeText(context, "No faces detected", Toast.LENGTH_SHORT)
                  .show();

            }

            if (firebaseVisionFaces.size() > 1) {

              Toast.makeText(context, "More than one face detected",
                  Toast.LENGTH_SHORT).show();

            } else if (firebaseVisionFaces.size() == 1) {
              FirebaseVisionFace face = firebaseVisionFaces.get(0);
             Emotions emotion = EmotionDetector.getEmotions(face);
             if(emotion!=null){
               showEmotionSuccessDialog(emotion);
             }



            }
          })
          .addOnFailureListener(e -> {
            e.printStackTrace();
            Toast.makeText(context, "Face detection failed",
                Toast.LENGTH_SHORT).show();

          });
    }
  }
  private void showEmotionSuccessDialog(Emotions emotions){
    EmotionResultFragment emotionResultFragment = EmotionResultFragment.getInstance(emotions);
    emotionResultFragment.show(getSupportFragmentManager(),"2");
  }
}
