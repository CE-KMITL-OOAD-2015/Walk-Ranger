package com.example.nattachai.walkingranger;

/**
 * Created by Nattachai on 10/15/2015.
 */
public class User {
    private String userName;
    private int step;
    private String fBcode;
    public User(){

    }
    public String getUserName(){
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getStep() {
        return step;
    }

    public String getfBcode() {
        return fBcode;
    }

    public void setfBcode(String fBcode) {
        this.fBcode = fBcode;
    }
}
