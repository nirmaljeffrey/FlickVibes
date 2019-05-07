package com.nirmal.jeffrey.flickvibes.ui.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentManager;
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
import com.nirmal.jeffrey.flickvibes.ui.fragment.EmotionErrorFragment;
import com.nirmal.jeffrey.flickvibes.ui.fragment.EmotionResultFragment;
import com.nirmal.jeffrey.flickvibes.util.BitmapUtils;
import com.nirmal.jeffrey.flickvibes.util.Constants;
import java.io.FileNotFoundException;
import java.util.List;

public class MoviePredictionActivity extends BaseActivity {

  private static final int NO_FACES_DETECTED = 1;
  private static final int MORE_THAN_ONE_FACE_DETECTED = 2;
  @BindView(R.id.face_image_view)
  ImageView faceImageView;
  @BindView(R.id.clear_button)
  FloatingActionButton clearButton;
  @BindView(R.id.detect_face_button)
  CardView detectFaceButton;
  @BindView(R.id.movie_predictions_coordinator_layout)
  CoordinatorLayout moviePredictionLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movie_prediction);
    ButterKnife.bind(this);
    //Method from base activity to show progress bar

    initClearButton();
    Bitmap bitmap = getIncomingIntent();
    initDetectFaceButton(bitmap);
  }

  private Bitmap getIncomingIntent() {
    Bitmap bitmap = null;
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

  private void initClearButton() {
    clearButton.setOnClickListener(
        view -> finish());
  }

  private void initDetectFaceButton(Bitmap bitmap) {
    detectFaceButton.setOnClickListener(
        view -> {
          showActivityLayout(false);
          showProgressBar(true);
          detectEmotions(MoviePredictionActivity.this, bitmap);
        });
  }

  private void detectEmotions(Context context, Bitmap bitmap) {
    if (bitmap != null) {
      FirebaseVisionFaceDetector detector = EmotionDetector.getDetector();
      FirebaseVisionImage image = EmotionDetector.detectFacesFromImage(bitmap);
      Task<List<FirebaseVisionFace>> result = detector.detectInImage(image).addOnSuccessListener(
          firebaseVisionFaces -> {
            if (firebaseVisionFaces.size() == 0) {
              showProgressBar(false);
              showEmotionErrorDialog(NO_FACES_DETECTED);
            }
            if (firebaseVisionFaces.size() > 1) {
              showProgressBar(false);
              showEmotionErrorDialog(MORE_THAN_ONE_FACE_DETECTED);
            } else if (firebaseVisionFaces.size() == 1) {
              FirebaseVisionFace face = firebaseVisionFaces.get(0);
              Emotions emotion = EmotionDetector.getEmotions(face);
              if (emotion != null) {
                showProgressBar(false);
                showEmotionSuccessDialog(emotion);
              }
            }
          })
          .addOnFailureListener(e -> {
            e.printStackTrace();
            showProgressBar(false);
            showActivityLayout(true);
            Toast.makeText(context, R.string.face_detection_failed, Toast.LENGTH_SHORT).show();
          });
    }
  }

  private void showEmotionSuccessDialog(Emotions emotions) {
    FragmentManager fragmentManager = getSupportFragmentManager();
    EmotionResultFragment emotionResultFragment = EmotionResultFragment.getInstance(emotions);
    emotionResultFragment.show(fragmentManager, Constants.EMOTION_DIALOG_TAG);

  }

  private void showEmotionErrorDialog(int errorValue) {
    FragmentManager fragmentManager = getSupportFragmentManager();
    EmotionErrorFragment fragment = EmotionErrorFragment.getInstance(errorValue);
    fragment.show(fragmentManager, Constants.EMOTION_ERROR_DIALOG_TAG);
  }


  public void showActivityLayout(boolean visibility) {
    moviePredictionLayout.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);

  }
}
