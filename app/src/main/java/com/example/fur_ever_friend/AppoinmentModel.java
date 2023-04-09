package com.example.fur_ever_friend;

public class AppoinmentModel {
    String date;
    String time;
    String userId;
    String walkerId;
    Object Pickup_Location;

    public Object getPickup_Location() {
        return Pickup_Location;
    }

    public void setPickup_Location(Object pickup_Location) {
        Pickup_Location = pickup_Location;
    }



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWalkerId() {
        return walkerId;
    }

    public void setWalkerId(String walkerId) {
        this.walkerId = walkerId;
    }

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