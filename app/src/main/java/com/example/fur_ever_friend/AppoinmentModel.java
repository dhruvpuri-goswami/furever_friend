package com.example.fur_ever_friend;

public class AppoinmentModel {
    String date;
    String time;
    public AppoinmentModel(){}
    public AppoinmentModel(String date, String time) {
        this.date = date;
        this.time = time;
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