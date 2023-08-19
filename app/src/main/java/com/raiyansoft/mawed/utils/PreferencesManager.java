package com.raiyansoft.mawed.utils;

import android.app.Activity;
import android.content.SharedPreferences;

import com.raiyansoft.mawed.model.auth.User;
import com.raiyansoft.mawed.model.sections.SectionData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class PreferencesManager {

    private static SharedPreferences sharedPreferences = null;

    private static void setSharedPreferences(Activity activity) {
        if (sharedPreferences == null)
            sharedPreferences = activity.getSharedPreferences("Mawed", Activity.MODE_PRIVATE);
    }

    public static void saveAppData(Activity activity, String key, String value){
        setSharedPreferences(activity);
        if (sharedPreferences != null){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, value);
            editor.apply();
        }else
            setSharedPreferences(activity);
    }

    public static String loadAppData(Activity activity, String key){
        setSharedPreferences(activity);
        if (sharedPreferences != null)
            return sharedPreferences.getString(key, "");
        else{
            setSharedPreferences(activity);
            return null;
        }
    }

    public static void saveUserToken(Activity activity, String key, String value){
        setSharedPreferences(activity);
        if (sharedPreferences != null){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, value);
            editor.apply();
        }else
            setSharedPreferences(activity);
    }

    public static String loadUserToken(Activity activity, String key){
        setSharedPreferences(activity);
        if (sharedPreferences != null)
            return sharedPreferences.getString(key, "");
        else{
            setSharedPreferences(activity);
            return null;
        }
    }

    public static void saveUserData(Activity activity, String key, User user){
        setSharedPreferences(activity);
        if (sharedPreferences != null){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, new Gson().toJson(user));
            editor.apply();
        }else
            setSharedPreferences(activity);
    }

    public static User getUserData(Activity activity, String key){
        setSharedPreferences(activity);
        if (sharedPreferences != null){
            String userData = sharedPreferences.getString(key, "");
            return new Gson().fromJson(userData, User.class);
        }else{
            setSharedPreferences(activity);
            return null;
        }
    }

    public static void saveSections(Activity activity, String key, List<SectionData> sections){
        setSharedPreferences(activity);
        if (sharedPreferences != null){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, new Gson().toJson(sections));
            editor.apply();
        }else
            setSharedPreferences(activity);
    }

    public static List<SectionData> getSections(Activity activity, String key){
        setSharedPreferences(activity);
        if (sharedPreferences != null){
            String sectionsJson = sharedPreferences.getString(key, "");
            List<SectionData> sections = new Gson().fromJson(sectionsJson, new TypeToken<List<SectionData>>(){}.getType());
            return sections;
        }else{
            setSharedPreferences(activity);
            return null;
        }
    }

    public static void saveLanguage(Activity activity, String value){
        setSharedPreferences(activity);
        if (sharedPreferences != null){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Const.KEY_LANGUAGE, value);
            editor.apply();;
        }else
            setSharedPreferences(activity);
    }

    public static String loadLanguage(Activity activity, String key){
        setSharedPreferences(activity);
        String language = "";
        if(sharedPreferences != null){
            language = sharedPreferences.getString(key, HelperMethods.getDeviceLanguage());
            return language;
        }else {
            setSharedPreferences(activity);
            return language;
        }
    }

    public static void saveStartedScreensStatus(Activity activity, String key, boolean isSave) {
        setSharedPreferences(activity);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(key, isSave);
            editor.apply();
        } else
            setSharedPreferences(activity);
    }

    public static boolean isSaveStarted(Activity activity, String key) {
        setSharedPreferences(activity);
        if (sharedPreferences != null)
            return sharedPreferences.getBoolean(key, false);
        else {
            setSharedPreferences(activity);
            return false;
        }
    }

    public static void clear(Activity activity, String key){
        setSharedPreferences(activity);
        if (sharedPreferences != null){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(key);
            editor.apply();
        }else
            setSharedPreferences(activity);
    }

    public static void clear(Activity activity){
        setSharedPreferences(activity);
        if (sharedPreferences != null){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
        }else
            setSharedPreferences(activity);
    }

}