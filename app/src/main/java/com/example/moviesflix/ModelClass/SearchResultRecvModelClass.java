package com.example.moviesflix.ModelClass;

public class SearchResultRecvModelClass {
   String title, subTitle, childImageView;
   String movieId;

    public SearchResultRecvModelClass(String childImageView, String title, String subTitle, String movieId) {
        this.childImageView = childImageView;
        this.title = title;
        this.subTitle = subTitle;
        this.movieId = movieId;
    }

    public SearchResultRecvModelClass() {

    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
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
