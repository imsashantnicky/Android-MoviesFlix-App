package com.example.moviesflix.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviesflix.ModelClass.FeaturedModelClass;
import com.example.moviesflix.R;

import java.util.List;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.ViewHolder> {
    List<FeaturedModelClass> featuredModelClassList;
    Context context;

    public FeaturedAdapter(List<FeaturedModelClass> featuredModelClassList, Context context) {
        this.featuredModelClassList = featuredModelClassList;
        this.context = context;
    }

    @NonNull
    @Override
    public FeaturedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.featured_item_recv_design, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedAdapter.ViewHolder holder, int position) {
        holder.desc.setText(featuredModelClassList.get(position).getDesc());
        holder.imdbRating.setText(featuredModelClassList.get(position).getImdbRating());
        holder.tvShowTitle.setText(featuredModelClassList.get(position).getTvShowTitle());
        Glide.with(context).load(featuredModelClassList.get(position).getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return featuredModelClassList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView desc, imdbRating, tvShowTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.featured_movie_imv);
            desc = itemView.findViewById(R.id.featured_movie_desc_tv);
            imdbRating = itemView.findViewById(R.id.featured_movie_rating_tv);
            tvShowTitle = itemView.findViewById(R.id.featured_tvshow_title_txt);
        }
    }
}
