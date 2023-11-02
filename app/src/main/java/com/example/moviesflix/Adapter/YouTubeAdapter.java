package com.example.moviesflix.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesflix.ModelClass.YoutubeModelClass;
import com.example.moviesflix.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import java.util.List;


public class YouTubeAdapter extends RecyclerView.Adapter<YouTubeAdapter.YouTubeViewHolder> {

    private List<YoutubeModelClass> videoIds;
    private Context context;

    public YouTubeAdapter(List<YoutubeModelClass> videoIds, Context context) {
        this.videoIds = videoIds;
        this.context = context;
    }

    @NonNull
    @Override
    public YouTubeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.youtube_video_recv_design, parent, false);
        return new YouTubeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YouTubeViewHolder holder, int position) {
        String videoId = videoIds.get(position).getVideoId();
        holder.initializeYouTubePlayer(videoId);
    }

    @Override
    public int getItemCount() {
        return videoIds.size();
    }

    public class YouTubeViewHolder extends RecyclerView.ViewHolder {
        YouTubePlayerView youTubePlayerView;
        public YouTubeViewHolder(@NonNull View itemView) {
            super(itemView);
            youTubePlayerView = itemView.findViewById(R.id.youtube_player_view);
        }
        public void initializeYouTubePlayer(String videoId) {
            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    youTubePlayer.loadVideo(videoId, 0);
                    youTubePlayer.pause();
                }
            });
        }
    }

}


