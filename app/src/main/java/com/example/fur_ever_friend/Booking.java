package com.example.fur_ever_friend;

public class Booking {
    private String walkerId;
    private String date;
    private String time;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    Booking(){}
    public Booking(String walkerId,String date,String time,String userId){
        this.walkerId=walkerId;
        this.date=date;
        this.time=time;
        this.userId=userId;
    }

    public String getWalkerId() {
        return walkerId;
    }

    public void setWalkerId(String walkerId) {
        this.walkerId = walkerId;
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
