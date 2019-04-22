package com.nirmal.jeffrey.flickvibes.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.nirmal.jeffrey.flickvibes.R;
import com.nirmal.jeffrey.flickvibes.emotiondetector.Emotions;
import com.nirmal.jeffrey.flickvibes.ui.activity.EmotionMoviesActivity;
import com.nirmal.jeffrey.flickvibes.util.Constants;
import java.util.Objects;

public class EmotionResultFragment extends DialogFragment {
@BindView(R.id.emotion_result_image_view)
  ImageView emotionResultImage;
@BindView(R.id.emotion_results_text_view)
  TextView emotionResultsText;
@BindView(R.id.movie_recommendation_button)
  CardView movieRecommendButton;
private Unbinder unbinder;

  public EmotionResultFragment() {
  }

  public static EmotionResultFragment getInstance(Emotions emotion){
    EmotionResultFragment fragment = new EmotionResultFragment();
    Bundle bundle =  new Bundle();
    bundle.putSerializable(Constants.EMOTION_BUNDLE_KEY,emotion);
    fragment.setArguments(bundle);

    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.dialog_emotion_result,container,false);
    ButterKnife.bind(this,view);
    if(getArguments()!=null){
     Emotions emotion = (Emotions)getArguments().getSerializable(Constants.EMOTION_BUNDLE_KEY);
      initMovieRecommendButton(Objects.requireNonNull(container).getContext(),emotion);
      initViews(emotion);
    }



    return view;
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

  private void initMovieRecommendButton(Context context,Emotions emotion){
    movieRecommendButton.setOnClickListener(view -> {
      Intent intent = new Intent(context, EmotionMoviesActivity.class);
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
