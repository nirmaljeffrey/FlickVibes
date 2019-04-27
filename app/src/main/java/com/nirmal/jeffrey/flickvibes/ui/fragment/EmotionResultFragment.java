package com.nirmal.jeffrey.flickvibes.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.nirmal.jeffrey.flickvibes.R;
import com.nirmal.jeffrey.flickvibes.emotiondetector.Emotions;
import com.nirmal.jeffrey.flickvibes.ui.activity.EmotionMoviesActivity;
import com.nirmal.jeffrey.flickvibes.ui.activity.MoviePredictionActivity;
import com.nirmal.jeffrey.flickvibes.util.Constants;
import java.util.Objects;

public class EmotionResultFragment extends DialogFragment {

@BindView(R.id.emotion_result_image_view)
  ImageView emotionResultImage;
@BindView(R.id.emotion_results_text_view)
  TextView emotionResultsText;
@BindView(R.id.movie_recommendation_button)
  CardView movieRecommendButton;
@BindView(R.id.emotion_toolbar)
Toolbar emotionFragmentToolBar;
private Unbinder unbinder;
  private static final String TAG = "EmotionResultFragment";
  public EmotionResultFragment() {
  }

  public static EmotionResultFragment getInstance(Emotions emotion){
    Log.d(TAG, "showEmotionSuccessDialog fragment:  dialog Fragment Created ");
    EmotionResultFragment fragment = new EmotionResultFragment();
    Bundle bundle =  new Bundle();
    bundle.putSerializable(Constants.EMOTION_BUNDLE_KEY,emotion);
    fragment.setArguments(bundle);

    return fragment;
  }


  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(TAG, "onCreate: showEmotionSuccessDialog");
    setStyle(DialogFragment.STYLE_NORMAL,R.style.DialogTheme);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.dialog_emotion_result,container);
   unbinder= ButterKnife.bind(this,view);
    Log.d(TAG, "onCreateView: showEmotionSuccessDialog");
    if(getArguments()!=null){
     Emotions emotion = (Emotions)getArguments().get(Constants.EMOTION_BUNDLE_KEY);
     if (emotion!=null) {
       Log.d(TAG, "showEmotionSuccessDialog fragment: Emotion received by dialog "+emotion.name());
       initMovieRecommendButton(emotion);
       initViews(emotion);
     }
    }



    return view;
  }

  @Override
  public void onStart() {
    super.onStart();

    if ( getDialog() != null) {
      int width = ViewGroup.LayoutParams.MATCH_PARENT;
      int height = ViewGroup.LayoutParams.MATCH_PARENT;
       Objects.requireNonNull(getDialog().getWindow()).setLayout(width, height);
    }

  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    emotionFragmentToolBar.setNavigationOnClickListener(view1 -> {
     dismiss();
     if(getActivity()!=null) {
       if (getActivity() instanceof MoviePredictionActivity) {
         MoviePredictionActivity predictionActivity = (MoviePredictionActivity) getActivity();
         predictionActivity.showActivityLayout(true);
       }

     }
    });

  }

  private void initViews(Emotions emotions){
    switch (emotions){
      case HAPPY:
        emotionResultImage.setImageResource(R.drawable.ic_happy_feeling);
        emotionResultsText.setText(R.string.happy_emotion);
        break;
      case SAD:
        emotionResultImage.setImageResource(R.drawable.ic_sad_feeling);
        emotionResultsText.setText(R.string.sad_emotion);
        break;
      case NORMAL:
        emotionResultImage.setImageResource(R.drawable.ic_sad_feeling);
        emotionResultsText.setText(R.string.sad_emotion);
        break;

    }
  }

  private void initMovieRecommendButton(Emotions emotion){
    movieRecommendButton.setOnClickListener(view -> {
      Intent intent = new Intent(getActivity(), EmotionMoviesActivity.class);
      intent.putExtra(Constants.EMOTION_ACTIVITY_INTENT,emotion);
      startActivity(intent);
    });
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }
}
