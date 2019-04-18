package com.nirmal.jeffrey.flickvibes.ui;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import com.nirmal.jeffrey.flickvibes.R;
import com.nirmal.jeffrey.flickvibes.util.Constants;

public class MoviePredictionActivity extends AppCompatActivity {
private Bitmap bitmap;
private Uri imageUri;
@BindView(R.id.uri_textview)
  TextView uriTextView;
  private static final String TAG = "MoviePredictionActivity";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movie_prediction);
  }

  private void getIncomingIntent() {
    if (getIntent()!=null) {

      if(getIntent().hasExtra(Constants.CAMERA_ACTIVITY_INTENT)){
        bitmap = getIntent().getParcelableExtra(Constants.CAMERA_ACTIVITY_INTENT);
      }
      if(getIntent().hasExtra(Constants.GALLERY_ACTIVITY_INTENT)){
        imageUri = getIntent().getParcelableExtra(Constants.GALLERY_ACTIVITY_INTENT);
        Log.d(TAG, "imageUri "+imageUri);
        uriTextView.setText(imageUri.toString());

      }


    }
  }
}
