package com.samagra.odktest.data.models;

public class SMS {
    private String phoneNo;
    private String status;
    private String timeStamp;


    public SMS() {}

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    SMS(String phoneNo, String status, String timeStamp){
        this.phoneNo = phoneNo;
        this.status = status;
        this.timeStamp = timeStamp;
    }
}
