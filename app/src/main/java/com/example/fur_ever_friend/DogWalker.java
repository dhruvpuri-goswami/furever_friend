package com.example.fur_ever_friend;

public class DogWalker {
    private String name;
    private String imageUrl;
    private String mobile,EmailID,Price;
    private String Status;

    public DogWalker() {
        // Required empty constructor for Firebase
    }

    public DogWalker(String name,String mobile,String Emailid) {
        this.name = name;
        this.imageUrl="";
        this.mobile=mobile;
        this.EmailID=Emailid;
        this.Status="Available";
    }
//    public DogWalker(String imageUrl,String price){
//        this.name=this.getName();
//        this.EmailID=this.getEmailID();
//        this.Status="Available";
//        this.mobile=this.getMobile();
//        this.imageUrl=imageUrl;
//        this.Price=price;
//    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmailID() {
        return EmailID;
    }

    public void setEmailID(String emailID) {
        EmailID = emailID;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
