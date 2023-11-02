package com.example.moviesflix.ModelClass;

public class ChildRecvModelClass {
   int progress;
   double ratingText;
   String title, subTitle, childImageView;
   String movieId;

    public ChildRecvModelClass(String childImageView, String title, String subTitle, double ratingText, int progress, String movieId) {
        this.childImageView = childImageView;
        this.title = title;
        this.subTitle = subTitle;
        this.ratingText = ratingText;
        this.progress = progress;
        this.movieId = movieId;
    }

    public ChildRecvModelClass() {

    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public double getRatingText() {
        return ratingText;
    }

    public void setRatingText(int ratingText) {
        this.ratingText = ratingText;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getChildImageView() {
        return childImageView;
    }

    public void setChildImageView(String childImageView) {
        this.childImageView = childImageView;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
}
