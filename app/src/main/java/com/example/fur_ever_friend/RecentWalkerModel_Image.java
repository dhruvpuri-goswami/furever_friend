package com.example.fur_ever_friend;

public class RecentWalkerModel_Image {
    String imageUrl;
    String name;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RecentWalkerModel_Image(String imageUrl, String name) {
        this.imageUrl = imageUrl;
        this.name = name;
    }
}
