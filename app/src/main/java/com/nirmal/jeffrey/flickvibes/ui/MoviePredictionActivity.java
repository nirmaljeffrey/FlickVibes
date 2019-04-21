package com.nirmal.jeffrey.flickvibes.ui;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nirmal.jeffrey.flickvibes.R;
import com.nirmal.jeffrey.flickvibes.util.BitmapUtils;
import com.nirmal.jeffrey.flickvibes.util.Constants;
import java.io.FileNotFoundException;

public class MoviePredictionActivity extends AppCompatActivity {
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
    getIncomingIntent();
  }

  private void getIncomingIntent() {
    if (getIntent()!=null) {

      if(getIntent().hasExtra(Constants.CAMERA_ACTIVITY_INTENT)){
        String camerUri = getIntent().getStringExtra(Constants.CAMERA_ACTIVITY_INTENT);
        Bitmap cameraBitmap = BitmapUtils.resamplePic(this, camerUri);
        faceImageView.setImageBitmap(cameraBitmap);
      }else if(getIntent().hasExtra(Constants.GALLERY_ACTIVITY_INTENT)){
        Uri imageUri = getIntent().getParcelableExtra(Constants.GALLERY_ACTIVITY_INTENT);
        Bitmap galleryBitmap = null;
        try {
          galleryBitmap = BitmapUtils.resamplePic(this, imageUri);
          faceImageView.setImageBitmap(galleryBitmap);
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }


      }


    }
  }
}
