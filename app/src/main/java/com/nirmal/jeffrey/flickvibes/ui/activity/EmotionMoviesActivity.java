package com.nirmal.jeffrey.flickvibes.ui.activity;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.nirmal.jeffrey.flickvibes.R;
import com.nirmal.jeffrey.flickvibes.emotiondetector.Emotions;
import com.nirmal.jeffrey.flickvibes.util.Constants;

public class EmotionMoviesActivity extends AppCompatActivity {

  private static final String TAG = "EmotionMoviesActivity";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_emotion_movies);
    getIncomingIntent();
  }
  private void getIncomingIntent(){
    if(getIntent().hasExtra(Constants.EMOTION_ACTIVITY_INTENT)){

      Emotions emotions =(Emotions) getIntent().getSerializableExtra(Constants.EMOTION_ACTIVITY_INTENT);
      Log.d(TAG, "getIncomingIntent: EmotionType" + emotions.name());
    }
  }
}
