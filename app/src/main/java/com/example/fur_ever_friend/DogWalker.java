package com.example.fur_ever_friend;

public class DogWalker {
    private String emailID;
    private String imageUrl;
    private String mobile;
    private String name;
    private String price;
    private String status;

    public DogWalker() {
        // Required empty constructor for Firebase
    }

    public DogWalker(String Emailid,String imageUrl,String mobile,String name,String price,String status) {
        this.emailID=Emailid;
        this.imageUrl=imageUrl;
        this.mobile=mobile;
        this.name=name;
        this.price=price;
        this.status=status;
    }
//    public DogWalker(String imageUrl,String price){
//        this.name=this.getName();
//        this.EmailID=this.getEmailID();
//        this.Status="Available";
//        this.mobile=this.getMobile();
//        this.imageUrl=imageUrl;
//        this.Price=price;
//    }

    public String getmobile() {
        return mobile;
    }

    public void setmobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        emailID = emailID;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        status = status;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
