package com.bayazid.cpik_present_system.DATA_SECTOR;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {private SharedPreferences preferences;

    public Session(Context context){
        preferences = PreferenceManager.getDefaultSharedPreferences( context );
    }
    //...Email
    public void setEamil(String email){
        preferences.edit().putString( "email", email ).commit();
    }
    public String getEamil(){
        String email = preferences.getString( "email", "" );
        return email;
    }
    //.....ImageURL
    public void setImageURL(String image){
        preferences.edit().putString( "image", image ).commit();
    }
    public String getImageURL(){
        String image = preferences.getString( "image", "" );
        return image;
    }
    //Title
    public void setTitle(String title){
        preferences.edit().putString( "full_name", title ).commit();
    }
    public String getTitle(){
        String title = preferences.getString( "full_name", "" );
        return title;
    }
//NaME
    public void setName(String name){
        preferences.edit().putString( "name", name ).commit();
    }
    public String getName(){
        String name = preferences.getString( "name", "" );
        return name;
    }
    //UID
    public void setuId(String uId){
        preferences.edit().putString( "uId", uId ).commit();
    }
    public String getuId(){
        String uId = preferences.getString( "uId", "" );
        return uId;
    }
    //idToken
    public void setuIdToken(String uId){
        preferences.edit().putString( "uId", uId ).commit();
    }
    public String getuIdToken(){
        String IdToken = preferences.getString( "IdToken", "" );
        return IdToken;
    }
    //Contact Number
    public void setPhoneNumber(String PhoneNumber){
        preferences.edit().putString( "PhoneNumber", PhoneNumber ).commit();
    }
    public String getPhoneNumbern(){
        String PhoneNumber = preferences.getString( "PhoneNumber", "" );
        return PhoneNumber;
    }
    //get Admin Email
    public void setisAdminEmail(boolean isAdminEmail){
        preferences.edit().putBoolean( "isAdminEmail", isAdminEmail ).commit();
    }
    public boolean getisAdminEmail(){
        boolean isAdminEmail = preferences.getBoolean( "isAdminEmail", false );
        return isAdminEmail;
    }
 //get Admin Email
    public void setTotalStudents(int TotalStudents){
        preferences.edit().putInt( "TotalStudents", TotalStudents ).commit();
    }
    public int getTotalStudents(){
        int TotalStudents = preferences.getInt( "TotalStudents", 0 );
        return TotalStudents;
    }
}
