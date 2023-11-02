package com.example.moviesflix.ModelClass;

public class YoutubeModelClass {
    String videoId;

    public YoutubeModelClass(String videoId) {
        this.videoId = videoId;
    }

    public YoutubeModelClass(){

    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
