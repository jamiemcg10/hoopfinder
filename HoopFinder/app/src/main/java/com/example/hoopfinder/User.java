package com.example.hoopfinder;

public class User {
    String user_id;
    String user_email;
    String user_pwd;

    public User(){

    }

    public User(String user_id, String user_email, String user_pwd ){
        this.user_id =user_id;
        this.user_pwd = user_pwd;
        this.user_email = user_email;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getUser_pwd() {
        return user_pwd;
    }
}
