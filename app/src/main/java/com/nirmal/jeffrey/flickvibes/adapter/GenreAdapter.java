package com.nirmal.jeffrey.flickvibes.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nirmal.jeffrey.flickvibes.R;
import com.nirmal.jeffrey.flickvibes.adapter.GenreAdapter.GenreListViewHolder;
import com.nirmal.jeffrey.flickvibes.model.Genre;
import java.util.ArrayList;

public class GenreAdapter extends RecyclerView.Adapter<GenreListViewHolder> {
  private ArrayList<Genre> genreArrayList;
  public GenreAdapter(){

  }
  public void setGenreData(ArrayList<Genre> genreArrayList){
    this.genreArrayList=genreArrayList;
    notifyDataSetChanged();
  }
  @NonNull
  @Override
  public GenreListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.genre_list_item,parent,false);

    return new GenreListViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull GenreListViewHolder holder, int position) {
Genre genre =genreArrayList.get(position);
holder.genreTextView.setText(genre.getGenreName());
  }

  @Override
  public int getItemCount() {
    if(genreArrayList==null) return 0;
    return genreArrayList.size();
  }




  static  class GenreListViewHolder extends RecyclerView.ViewHolder{
@BindView(R.id.genre_text_view)
TextView genreTextView;
   GenreListViewHolder(@NonNull View itemView) {
    super(itemView);
     ButterKnife.bind(this,itemView);
  }
}
}
