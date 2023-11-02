package com.example.moviesflix.ModelClass;

public class DownloadModelClass {
    int image;
    String title, category;

    public DownloadModelClass(int image, String title, String category) {
        this.image = image;
        this.title = title;
        this.category = category;
    }

    public DownloadModelClass() {

    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
