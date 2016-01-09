package com.example.vasiliy.ch2015;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

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

    public static MyUser getInstance(Context ctx) {
        MyUser user = MyUserHolder.myUser;
        load(ctx);
        return user;
    }

    private static String name;

    public static String getName() {
        return name;
    }

    public static void setName(Context ctx, String name) {
        MyUser.name = name;
        save(ctx);
    }

    public static void save(Context ctx) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("name", name);
        edit.commit();
    }

    public static void load(Context ctx) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        name = prefs.getString("name", null);
    }

}
