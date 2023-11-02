package com.example.moviesflix.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviesflix.Activity.MoviesDetailsActivity;
import com.example.moviesflix.ModelClass.ChildRecvModelClass;
import com.example.moviesflix.R;

import java.util.List;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ViewHolder> {
    List<ChildRecvModelClass> childRecvModelClassList;
    Context context;

    public ChildAdapter(List<ChildRecvModelClass> childRecvModelClassList, Context context) {
        this.childRecvModelClassList = childRecvModelClassList;
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
    public ChildAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.child_recv_design, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildAdapter.ViewHolder holder, int position) {

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image);

        holder.movieTitle.setText(childRecvModelClassList.get(position).getTitle());
        holder.subTitle.setText(childRecvModelClassList.get(position).getSubTitle());
        holder.ratingText.setText(String.valueOf(childRecvModelClassList.get(position).getRatingText()));
        holder.progress.setProgress(childRecvModelClassList.get(position).getProgress());
        Glide.with(context).load(childRecvModelClassList.get(position).getChildImageView())
                .apply(requestOptions).into(holder.childImageView);
        holder.movie_id.setText(childRecvModelClassList.get(position).getMovieId());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    Intent intent = new Intent(context, MoviesDetailsActivity.class);
                    intent.putExtra("movie_id", childRecvModelClassList.get(holder.getAdapterPosition()).getMovieId());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return childRecvModelClassList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView childImageView;
        TextView movieTitle, subTitle, ratingText, movie_id;
        ProgressBar progress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            childImageView = (ImageView) itemView.findViewById(R.id.child_recv_imv);
            movieTitle = (TextView) itemView.findViewById(R.id.child_recv_title_tv);
            subTitle = (TextView) itemView.findViewById(R.id.child_recv_subtitle_tv);
            ratingText = (TextView) itemView.findViewById(R.id.rating_txt);
            progress = (ProgressBar) itemView.findViewById(R.id.rating_progressbar);
            movie_id = itemView.findViewById(R.id.movie_id);
        }
    }
}
