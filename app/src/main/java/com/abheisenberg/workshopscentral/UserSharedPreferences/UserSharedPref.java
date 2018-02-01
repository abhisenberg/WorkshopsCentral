package com.abheisenberg.workshopscentral.UserSharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by abheisenberg on 1/2/18.
 */

public class UserSharedPref {

    private SharedPreferences pref;
    private Context context;
    private SharedPreferences.Editor editor;

    public static final String PREF_NAME = "MyPref";
    public static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PW = "pw";

    public UserSharedPref(Context context) {
        this.context = context;
        pref = context.getApplicationContext()
                .getSharedPreferences(PREF_NAME, 0);
        editor = pref.edit();
    }

    public void createLoginSession(String name, String phone, String email, String pw) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PW, pw);
        editor.putString(email, pw);
        editor.commit();
    }

    public boolean checkAccount(String email,String pw) {
        if(pref.getString(KEY_EMAIL, "").equals(email)
                && pref.getString(email, ".").equals(pw)){
                return true;
        }

        return false;
    }

    public void checkLogin(){
        if(!this.isLoggedIn()){
            //TODO
        }
    }

    public void apply( String wsname){
        editor.putBoolean(wsname, true);
        editor.commit();
    }

    public boolean alreadyApplied(String wsname){
        return   pref.getBoolean(wsname, false);
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();

        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        return user;
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void login(){
        editor.putBoolean(IS_LOGIN, true);
        editor.commit();
    }

    public void signout(){
        editor.putBoolean(IS_LOGIN, false);
        editor.commit();
    }

}
