package com.example.fur_ever_friend;

public class DogWalker {
    private String name;
    private String imageUrl;

    public DogWalker() {
        // Required empty constructor for Firebase
    }

    public DogWalker(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
