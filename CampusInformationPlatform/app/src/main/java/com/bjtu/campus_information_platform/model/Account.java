package com.bjtu.campus_information_platform.model;

import android.graphics.Bitmap;

public class Account {

    private Integer id;

    private String nickname;

    private String email;

    private Bitmap UserPhoto;

    private Bitmap UserBackground;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Bitmap getUserPhoto() {
        return UserPhoto;
    }

    public void setUserPhoto(Bitmap userPhoto) {
        UserPhoto = userPhoto;
    }

    public Bitmap getUserBackground() {
        return UserBackground;
    }

    public void setUserBackground(Bitmap userBackground) {
        UserBackground = userBackground;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", UserPhoto=" + UserPhoto +
                ", UserBackground=" + UserBackground +
                '}';
    }
}
