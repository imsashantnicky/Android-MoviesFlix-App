package com.example.moviesflix.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviesflix.Activity.MoviesDetailsActivity;
import com.example.moviesflix.ModelClass.SearchResultRecvModelClass;
import com.example.moviesflix.ModelClass.SearchResultRecvModelClass;
import com.example.moviesflix.R;

import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {
    List<SearchResultRecvModelClass> searchResultRecvModelClasses;
    Context context;

    public SearchResultAdapter(List<SearchResultRecvModelClass> searchResultRecvModelClasses, Context context) {
        this.searchResultRecvModelClasses = searchResultRecvModelClasses;
        this.context = context;
    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(String movieId);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public SearchResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.searched_item_recv_design, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultAdapter.ViewHolder holder, int position) {

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image);

        holder.movieTitle.setText(searchResultRecvModelClasses.get(position).getTitle());
        holder.subTitle.setText(searchResultRecvModelClasses.get(position).getSubTitle());
        Glide.with(context).load(searchResultRecvModelClasses.get(position).getChildImageView())
                .apply(requestOptions).into(holder.childImageView);
        holder.movie_id.setText(searchResultRecvModelClasses.get(position).getMovieId());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    Intent intent = new Intent(context, MoviesDetailsActivity.class);
                    intent.putExtra("movie_id", searchResultRecvModelClasses.get(position).getMovieId());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchResultRecvModelClasses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView childImageView;
        TextView movieTitle, subTitle, movie_id;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            childImageView = (ImageView) itemView.findViewById(R.id.child_recv_imv);
            movieTitle = (TextView) itemView.findViewById(R.id.child_recv_title_tv);
            subTitle = (TextView) itemView.findViewById(R.id.child_recv_subtitle_tv);
            movie_id = itemView.findViewById(R.id.movie_id);
        }
    }
}
