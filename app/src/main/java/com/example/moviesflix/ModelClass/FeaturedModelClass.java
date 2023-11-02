package com.example.moviesflix.ModelClass;

public class FeaturedModelClass {
    String image;
    String desc, imdbRating, tvShowTitle;

    public FeaturedModelClass(String image, String desc, String imdbRating, String tvShowTitle) {
        this.image = image;
        this.desc = desc;
        this.imdbRating = imdbRating;
        this.tvShowTitle = tvShowTitle;
    }

    public String getTvShowTitle() {
        return tvShowTitle;
    }

    public void setTvShowTitle(String tvShowTitle) {
        this.tvShowTitle = tvShowTitle;
    }

    public FeaturedModelClass() {

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }
}
