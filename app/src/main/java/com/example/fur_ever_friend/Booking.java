package com.example.fur_ever_friend;

public class Booking {
    private String walkerName;
    private String date;
    private String time;
    Booking(){}
    public Booking(String walkerName,String date,String time){
        this.walkerName=walkerName;
        this.date=date;
        this.time=time;
    }

    public String getWalkerName() {
        return walkerName;
    }

    public void setWalkerName(String walkerName) {
        this.walkerName = walkerName;
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
