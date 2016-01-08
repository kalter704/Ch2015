package com.example.vasiliy.ch2015;

/**
 * Created by vasiliy on 06.01.16.
 */
public class MyUser {

    private MyUser() {
        name = "NoName";
    }

    private static class MyUserHolder {
        private final static MyUser myUser = new MyUser();
    }

    public static MyUser getInstance() {
        return MyUserHolder.myUser;
    }

    public String name;

}
