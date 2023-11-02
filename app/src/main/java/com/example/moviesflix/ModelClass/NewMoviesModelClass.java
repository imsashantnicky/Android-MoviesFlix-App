package com.example.moviesflix.ModelClass;

public class NewMoviesModelClass {
    String image, videoId;
    String month, date, comingDate, desc, category;

    public NewMoviesModelClass(String image, String videoId, String month, String date, String comingDate, String desc, String category) {
        this.image = image;
        this.month = month;
        this.date = date;
        this.comingDate = comingDate;
        this.desc = desc;
        this.category = category;
        this.videoId = videoId;
    }

    public NewMoviesModelClass() {

    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComingDate() {
        return comingDate;
    }

    public void setComingDate(String comingDate) {
        this.comingDate = comingDate;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
