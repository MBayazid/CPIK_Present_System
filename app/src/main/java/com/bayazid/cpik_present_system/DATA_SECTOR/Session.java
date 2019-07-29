package com.bayazid.cpik_present_system.DATA_SECTOR;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {private SharedPreferences preferences;

    public Session(Context context){
        preferences = PreferenceManager.getDefaultSharedPreferences( context );
    }
    //...get Users Email
    public void setEamil(String email){
        preferences.edit().putString( "email", email ).commit();
    }
    public String getEamil(){
        String email = preferences.getString( "email", "" );
        return email;
    }

    //.....get Users ImageURL
    public void setImageURL(String image){
        preferences.edit().putString( "image", image ).commit();
    }
    public String getImageURL(){
        String image = preferences.getString( "image", "" );
        return image;
    }

    //get Users Title
    public void setTitle(String title){
        preferences.edit().putString( "full_name", title ).commit();
    }
    public String getTitle(){
        String title = preferences.getString( "full_name", "" );
        return title;
    }

    //get Users NaME
    public void setName(String name){
        preferences.edit().putString( "name", name ).commit();
    }
    public String getName(){
        String name = preferences.getString( "name", "" );
        return name;
    }
    //get UsersUID
    public void setuId(String uId){
        preferences.edit().putString( "uId", uId ).commit();
    }
    public String getuId(){
        String uId = preferences.getString( "uId", "" );
        return uId;
    }

    //get Users idToken
    public void setuIdToken(String uId){
        preferences.edit().putString( "uId", uId ).commit();
    }
    public String getuIdToken(){
        String IdToken = preferences.getString( "IdToken", "" );
        return IdToken;
    }

    //get Users Contact Number
    public void setPhoneNumber(String PhoneNumber){
        preferences.edit().putString( "PhoneNumber", PhoneNumber ).commit();
    }
    public String getPhoneNumbern(){
        String PhoneNumber = preferences.getString( "PhoneNumber", "" );
        return PhoneNumber;
    }
    //get Admin Email to cheak curent email is an Admin or Not
    public void setisAdminEmail(boolean isAdminEmail){
        preferences.edit().putBoolean( "isAdminEmail", isAdminEmail ).commit();
    }
    public boolean getisAdminEmail(){
        boolean isAdminEmail = preferences.getBoolean( "isAdminEmail", false );
        return isAdminEmail;
    }

    //get Admin ID Token is an Admin or Not
    public void setIdToken(boolean IdToken){  preferences.edit().putBoolean( "IdToken", IdToken ).commit(); }
    public boolean getIdToken(){ boolean IdToken = preferences.getBoolean( "IdToken", false ); return IdToken; }

    //count total presented students
    public void setTotalStudents(int TotalStudents){
        preferences.edit().putInt( "TotalStudents", TotalStudents ).commit();
    }
    public int getTotalStudents(){
        int TotalStudents = preferences.getInt( "TotalStudents", 0 );
        return TotalStudents;
    }
}
