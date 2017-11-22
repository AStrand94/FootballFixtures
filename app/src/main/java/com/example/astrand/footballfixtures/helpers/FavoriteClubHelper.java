package com.example.astrand.footballfixtures.helpers;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.astrand.footballfixtures.R;

import java.util.HashSet;
import java.util.Set;

public class FavoriteClubHelper {

    public static boolean isFavorite(Context context, String selfLink){

        return getFavoriteSet(context).contains(selfLink);

    }

    public static void updateFavoriteClub(Context context, boolean add, String selfLink){
        if (add){
            setFavorite(context, selfLink);
        }else{
            removeAsFavorite(context, selfLink);
        }
    }

    public static Set<String> getAllFavorites(Context context){
        return context
                .getSharedPreferences(context.getString(R.string.sharedPreferences),Context.MODE_PRIVATE)
                .getStringSet(context.getString(R.string.fav_set_key),new HashSet<String>());
    }

    private static void setFavorite(Context context, String selfLink){

        SharedPreferences preferences = getSharedPreferences(context);

        Set<String> favorites = getFavoriteSet(context,preferences);

        favorites.add(selfLink);

        preferences.edit()
                .remove(context.getString(R.string.fav_set_key))
                .putStringSet(context.getString(R.string.fav_set_key),favorites)
                .apply();
    }

    private static void removeAsFavorite(Context context, String selfLink){

        SharedPreferences preferences = getSharedPreferences(context);

        Set<String> favorites = getFavoriteSet(context,preferences);

        favorites.remove(selfLink);

        preferences.edit()
                .remove(context.getString(R.string.fav_set_key))
                .putStringSet(context.getString(R.string.fav_set_key),favorites)
                .apply();
    }

    private static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences(context.getString(R.string.sharedPreferences),Context.MODE_PRIVATE);
    }

    private static Set<String> getFavoriteSet(Context context, SharedPreferences preferences){

        return preferences.getStringSet(context.getString(R.string.fav_set_key), new HashSet<String>());
    }

    private static Set<String> getFavoriteSet(Context context){
        return getFavoriteSet(context,getSharedPreferences(context));
    }

}
