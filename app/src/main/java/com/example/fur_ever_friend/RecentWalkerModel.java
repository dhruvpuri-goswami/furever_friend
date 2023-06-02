package com.example.fur_ever_friend;

public class RecentWalkerModel {
    String date;
    String time;
    String imageUrl;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public RecentWalkerModel(String date, String time,String imageUrl,String name) {
        this.date = date;
        this.time = time;
        this.imageUrl=imageUrl;
        this.name=name;

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
