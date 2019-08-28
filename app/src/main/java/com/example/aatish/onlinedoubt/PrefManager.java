package com.example.aatish.onlinedoubt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    //pref mode
    int PRIVATE_MODE = 0;

    private static final String pref_name = "student_login";
    int is_login;
    public static final String key_enrollment = "enrollment";
    public static final String key_password = "password";
    public PrefManager(Context context){
        this.context = context;
        pref = this.context.getSharedPreferences(pref_name,PRIVATE_MODE);
    }

    //create login session
    public void LoginSession(String enrollment, String password){
        //storing login value as true
        is_login = 1;
        //editor.putString(is_login, is_login1);
        //editor.putBoolean(is_login, true);
        editor.putString(key_enrollment, enrollment);
        editor.putString(key_password, password);
        //commit changes
        editor.commit();
    }

    public void checklogin(){
        String xyz = isLoggedIn();
        if (xyz == "true") {
            Intent i = new Intent(context,example.class);
            context.startActivity(i);
        }
        else {
            Intent i = new Intent(context, student_login.class);
            //closing all the activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //add new flag to start new activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }

    public String isLoggedIn(){
        String a,b;
        if (is_login == 1){
            a = "true";
            return a;
        }
        else {
            b = "false";
            return b;
        }
    }

    /*public HashMap<String, String> getUser(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(key_enrollment, pref.getString(key_enrollment, null));
        user.put(key_password, pref.getString(key_password, null));
        return user;
    }*/

    public void logoutUser(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context,main_login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
