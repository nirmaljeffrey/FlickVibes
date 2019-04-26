package com.nirmal.jeffrey.flickvibes.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import at.blogc.android.views.ExpandableTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nirmal.jeffrey.flickvibes.R;
import com.nirmal.jeffrey.flickvibes.adapter.ReviewAdapter.ReviewListViewHolder;
import com.nirmal.jeffrey.flickvibes.model.Review;
import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewListViewHolder> {

  private ArrayList<Review> reviewArrayList;

  public ReviewAdapter() {

  }

  public void setReviewData(ArrayList<Review> reviewArrayList) {
    this.reviewArrayList = reviewArrayList;
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public ReviewListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.review_list_item, parent, false);
    return new ReviewListViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ReviewListViewHolder holder, int position) {
    Review review = reviewArrayList.get(position);
    holder.reviewerTextView.setText(review.getReviewAuthor());
    holder.reviewContentTextView.setText(review.getReviewContent());
    holder.reviewContentTextView.setInterpolator(new OvershootInterpolator());
    holder.expandCollapseButton.setOnClickListener(view -> {
      holder.expandCollapseButton
          .setImageResource(
              holder.reviewContentTextView.isExpanded() ? R.drawable.ic_expand_more_24dp
                  : R.drawable.ic_expand_less_24dp);

      holder.reviewContentTextView.toggle();
    });
    holder.reviewLayout.setOnClickListener(view -> {
      holder.expandCollapseButton.setImageResource(
          holder.reviewContentTextView.isExpanded() ? R.drawable.ic_expand_more_24dp
              : R.drawable.ic_expand_less_24dp);
      holder.reviewContentTextView.toggle();
    });

  }

  @Override
  public int getItemCount() {
    if (reviewArrayList == null) {
      return 0;
    }
    return reviewArrayList.size();
  }

  static class ReviewListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.review_constraint_layout)
    ConstraintLayout reviewLayout;
    @BindView(R.id.reviewer_name)
    TextView reviewerTextView;
    @BindView(R.id.review_content)
    ExpandableTextView reviewContentTextView;
    @BindView(R.id.review_expand_collapse_button)
    ImageView expandCollapseButton;

    ReviewListViewHolder(@NonNull View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
