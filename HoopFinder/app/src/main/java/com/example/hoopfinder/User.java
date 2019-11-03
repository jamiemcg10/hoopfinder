package com.example.hoopfinder;

public class User {
    String user_id;
    String user_email;
    String user_pwd;
    String user_phone_number;

    public User(){

    }

    public User(String user_id, String user_email, String user_pwd, String user_phone_number){
        this.user_id =user_id;
        this.user_pwd = user_pwd;
        this.user_email = user_email;
        this.user_phone_number = user_phone_number;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public String getUser_email() {
        return this.user_email;
    }

    public String getUser_pwd() {
        return this.user_pwd;
    }

    public String getUser_phone_number() { return this.user_phone_number; }
}
