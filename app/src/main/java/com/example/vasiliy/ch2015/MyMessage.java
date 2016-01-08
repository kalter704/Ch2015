package com.example.vasiliy.ch2015;

/**
 * Created by vasiliy on 06.01.16.
 */
public class MyMessage {
    String who;
    String what;

    public MyMessage(String who, String what) {
        this.who = who;
        this.what = what;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }
}
