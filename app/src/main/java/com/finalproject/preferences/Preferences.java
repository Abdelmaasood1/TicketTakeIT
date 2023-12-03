package com.finalproject.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.finalproject.model.UserModel;
import com.finalproject.model.UserSettingsModel;
import com.google.gson.Gson;


import java.util.Locale;

public class Preferences {

    private static Preferences instance = null;

    private Preferences() {
    }

    public static Preferences getInstance() {
        if (instance == null) {
            instance = new Preferences();
        }
        return instance;
    }

    public void create_update_language(Context context, String lang) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("language", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lang", lang);
        editor.apply();


    }
    public void createUpdateUserData(Context context, UserModel userModel) {
        SharedPreferences preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String user_data = gson.toJson(userModel);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user_data", user_data);
        editor.apply();

    }
//    public void createUpdateOwnerData(Context context, OwnerModel ownerModel) {
//        SharedPreferences preferences = context.getSharedPreferences("owner", Context.MODE_PRIVATE);
//        Gson gson = new Gson();
//        String owner_data = gson.toJson(ownerModel);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString("owner_data", owner_data);
//        editor.apply();
//
//    }

    public void create_update_user_settings(Context context, UserSettingsModel model) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings_pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String data = new Gson().toJson(model);
        editor.putString("settings", data);
        editor.apply();


    }

    public void clearUserData(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

    }
//    public void clearOwnerData(Context context) {
//        SharedPreferences preferences = context.getSharedPreferences("owner", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.clear();
//        editor.apply();
//
//    }
    public UserSettingsModel getUserSettings(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("settings_pref", Context.MODE_PRIVATE);
        UserSettingsModel model = new Gson().fromJson(preferences.getString("settings", ""), UserSettingsModel.class);
        return model;

    }

    public String getLanguage(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("language", Context.MODE_PRIVATE);
        return preferences.getString("lang", Locale.getDefault().getLanguage());

    }

    public void setIsLanguageSelected(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("language_selected", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("selected", true);
        editor.apply();
    }

    public boolean isLanguageSelected(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("language_selected", Context.MODE_PRIVATE);
        return preferences.getBoolean("selected", false);
    }

//    public void create_update_userdata(Context context, UserModel userModel) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
//        Gson gson = new Gson();
//        String user_data = gson.toJson(userModel);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("user_data", user_data);
//        editor.apply();
//        create_update_session(context, "login");
//
//    }

    public UserModel getUserData(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String user_data = preferences.getString("user_data", "");
        UserModel userModel = gson.fromJson(user_data, UserModel.class);
        return userModel;
    }
//    public OwnerModel getOwnerData(Context context) {
//        SharedPreferences preferences = context.getSharedPreferences("owner", Context.MODE_PRIVATE);
//        Gson gson = new Gson();
//        String owner_data = preferences.getString("owner_data", "");
//        OwnerModel ownerModel = gson.fromJson(owner_data, OwnerModel.class);
//        return ownerModel;
//    }

    public void create_update_session(Context context, String session) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("state", session);
        editor.apply();


    }


    public void create_room_id(Context context, String room_id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("room", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("room_id", room_id);
        editor.apply();


    }

    public String getRoomId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("room", Context.MODE_PRIVATE);
        String room_id = preferences.getString("room_id", "logout");
        return room_id;
    }

    public String getSession(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("state", "logout");
        return session;
    }

    public void setLastVisit(Context context, String date) {
        SharedPreferences preferences = context.getSharedPreferences("visit", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("lastVisit", date);
        editor.apply();

    }

    public String getLastVisit(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("visit", Context.MODE_PRIVATE);
        return preferences.getString("lastVisit", "0");
    }

    public void clear(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.clear();
        edit.apply();
        SharedPreferences preferences2 = context.getSharedPreferences("room", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit2 = preferences2.edit();
        edit2.clear();
        edit2.apply();
        create_update_session(context, "logout");
    }

    public void clearCart(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("cart", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.clear();
        edit.apply();
    }

    public void createUpdateAppSetting(Context context, UserSettingsModel settings) {
        SharedPreferences preferences = context.getSharedPreferences("settingsEbsar", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String data = gson.toJson(settings);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("settings", data);
        editor.apply();
    }

    public UserSettingsModel getAppSetting(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("settingsEbsar", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        return gson.fromJson(preferences.getString("settings", ""), UserSettingsModel.class);
    }

}
