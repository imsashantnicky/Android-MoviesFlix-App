package com.example.moviesflix.Adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviesflix.ModelClass.NewMoviesModelClass;
import com.example.moviesflix.ModelClass.YoutubeModelClass;
import com.example.moviesflix.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class NewMoviesAdapter extends RecyclerView.Adapter<NewMoviesAdapter.ViewHolder> {
    List<NewMoviesModelClass> newMoviesModelClassList;
    Context context;

    public NewMoviesAdapter(List<NewMoviesModelClass> newMoviesModelClassList, Context context) {
        this.newMoviesModelClassList = newMoviesModelClassList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewMoviesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.new_movies_recv_design, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewMoviesAdapter.ViewHolder holder, int position) {
        holder.date.setText(newMoviesModelClassList.get(position).getDate());
        holder.month.setText(newMoviesModelClassList.get(position).getMonth());
        holder.comingDate.setText(newMoviesModelClassList.get(position).getComingDate());
        holder.desc.setText(newMoviesModelClassList.get(position).getDesc());
        holder.category.setText(newMoviesModelClassList.get(position).getCategory());
        Glide.with(context).load(newMoviesModelClassList.get(position).getImage()).into(holder.image);
        //String videoId = newMoviesModelClassList.get(position).getVideoId();
        //holder.initializeYouTubePlayer(videoId);
    }

    @Override
    public int getItemCount() {
        return newMoviesModelClassList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //YouTubePlayerView youTubePlayerView;
        ImageView image;
        TextView month, date, comingDate, desc, category;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.new_movies_image);
            month = itemView.findViewById(R.id.new_movies_month);
            date = itemView.findViewById(R.id.new_movies_date);
            comingDate = itemView.findViewById(R.id.new_movies_coming_date);
            desc = itemView.findViewById(R.id.new_movies_desc);
            category = itemView.findViewById(R.id.new_movies_category);
            //youTubePlayerView = itemView.findViewById(R.id.new_movies_video_player_view);
        }

        /*
        public void initializeYouTubePlayer(String videoId) {
            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    youTubePlayer.loadVideo(videoId, 0);
                    youTubePlayer.pause();
                }
            });
        }
         */

    }


}