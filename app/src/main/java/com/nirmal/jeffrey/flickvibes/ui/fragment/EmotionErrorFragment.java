package com.nirmal.jeffrey.flickvibes.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
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
import com.nirmal.jeffrey.flickvibes.util.Constants;
import java.util.Objects;

public class EmotionErrorFragment extends DialogFragment {

  @BindView(R.id.emotion_error_text_view)
  TextView emotionErrorTextView;
  @BindView(R.id.emotion_error_image_view)
  ImageView emotionErrorImageView;
  @BindView(R.id.go_back_button)
  CardView goBackButton;
  @BindView(R.id.emotion_error_toolbar)
  Toolbar emotionErrorToolBar;
  private Unbinder unbinder;

  public EmotionErrorFragment() {
  }

  public static EmotionErrorFragment getInstance(int errorValue) {
    EmotionErrorFragment fragment = new EmotionErrorFragment();
    Bundle bundle = new Bundle();
    bundle.putInt(Constants.EMOTION_ERROR_BUNDLE_KEY, errorValue);
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogTheme);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.dialog_emotion_error, container, false);
    unbinder = ButterKnife.bind(this, view);
    if (getArguments() != null) {
      int errorValue = (int) getArguments().get(Constants.EMOTION_ERROR_BUNDLE_KEY);
      initViews(errorValue);
    }
    return view;
  }

  @Override
  public void onStart() {
    super.onStart();

    if (getDialog() != null) {
      int width = ViewGroup.LayoutParams.MATCH_PARENT;
      int height = ViewGroup.LayoutParams.MATCH_PARENT;
      Objects.requireNonNull(getDialog().getWindow()).setLayout(width, height);
    }

  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    emotionErrorToolBar.setNavigationOnClickListener(view1 -> {
      if (getActivity() != null) {
        getActivity().finish();
      }
    });
  }

  @Override
  public void onCancel(DialogInterface dialog) {
    if (getActivity() != null) {
      getActivity().finish();
    }
  }

  private void initViews(int errorValue) {
    if (errorValue == 1) {
      emotionErrorImageView.setImageResource(R.drawable.ic_no_faces_detected);
      emotionErrorTextView.setText(R.string.no_faces_detected);
    } else if (errorValue == 2) {
      emotionErrorImageView.setImageResource(R.drawable.ic_more_than_one_face_detected);
      emotionErrorTextView.setText(R.string.many_faces_detected);
    }
    goBackButton.setOnClickListener(view -> Objects.requireNonNull(getActivity()).finish());
  }

}
