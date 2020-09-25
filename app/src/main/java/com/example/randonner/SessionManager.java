package com.example.randonner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

   SharedPreferences sharedPreferences;
   public SharedPreferences.Editor editor;
   public Context context;
   int PRIVATE_MODE=0;

    private static final String PREF_NAME = "LOGIN";

    private static final String LOGIN = "IS_LOGIN";
    public  static final String NOM ="NOM";
    public  static final String ID ="ID";
    public  static final String PRENOM ="PRENOM";
    public  static final String MAIL ="MAIL";
    public  static final String TEL ="TEL";
    public  static final String BIRTHDAY ="DATENAISSANCE";
    public  static final String PASSWORD ="PASSWORD";


    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String id,String nom,String prenom,String mail,String tel,String birthDay,String motDePasse){
        editor.putBoolean(LOGIN,true);
        editor.putString(NOM,nom);
        editor.putString(PRENOM,prenom);
        editor.putString(ID,id);
        editor.putString(MAIL,mail);
        editor.putString(TEL,tel);
        editor.putString(BIRTHDAY,birthDay);
        editor.putString(PASSWORD,motDePasse);
        editor.apply();
    }
    public boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN,false);
    }

    public void checkLogin(){
        if(!this.isLoggin()){
            Intent intent = new Intent(context,ConnectionActivity.class);
            context.startActivity(intent);
            ((UserActivity)context).finish();
        }
    }

    public HashMap<String,String> getUserDetail(){
        HashMap<String,String> user= new HashMap<>();
        user.put(ID,sharedPreferences.getString(ID,null));
        user.put(NOM,sharedPreferences.getString(NOM,null));
        user.put(PRENOM,sharedPreferences.getString(PRENOM,null));
        user.put(MAIL,sharedPreferences.getString(MAIL,null));
        user.put(TEL,sharedPreferences.getString(TEL,null));
        user.put(BIRTHDAY,sharedPreferences.getString(BIRTHDAY,null));
        user.put(PASSWORD,sharedPreferences.getString(PASSWORD,null));
        return user;
    }

    public void logout(){
        editor.clear();
        editor.commit();
        Intent intent = new Intent(context,ConnectionActivity.class);
        context.startActivity(intent);
        ((UserActivity)context).finish();
    }
}
