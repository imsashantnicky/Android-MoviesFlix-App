package com.example.moviesflix.ModelClass;

public class TopCastModelClass {
    String castImg, castName, castProfile;

    public TopCastModelClass(String castImg, String castName, String castProfile) {
        this.castImg = castImg;
        this.castName = castName;
        this.castProfile = castProfile;
    }

    public TopCastModelClass() {

    }

    public String getCastImg() {
        return castImg;
    }

    public void setCastImg(String castImg) {
        this.castImg = castImg;
    }

    public String getCastName() {
        return castName;
    }

    public void setCastName(String castName) {
        this.castName = castName;
    }

    public String getCastProfile() {
        return castProfile;
    }

    public void setCastProfile(String castProfile) {
        this.castProfile = castProfile;
    }
}
