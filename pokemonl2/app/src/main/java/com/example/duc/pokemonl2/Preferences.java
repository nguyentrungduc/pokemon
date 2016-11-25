package com.example.duc.pokemonl2;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by DUC on 24/11/2016.
 */

public class Preferences {
    private static final String KEY = "pokemon";
    private static final String HIGH_SCORE_KEY = "highscore";
    private static final String IS_MUSIC_KEY = "ismusic";
    private static final String IS_EFFECTS_KEY = "iseffect";
    SharedPreferences sharedPreferences;

    private Preferences(Context context){
        this.sharedPreferences = context.getSharedPreferences(KEY,context.MODE_PRIVATE);
    }

    private static Preferences instance;

    public static Preferences getInstance() {
        return instance;
    }

    public static void init(Context context) {
        instance = new Preferences(context);
    }

    public int getHighScore() {
        return sharedPreferences.getInt(HIGH_SCORE_KEY, 0);
    }

    public void putHighScore(int highScore) {
        sharedPreferences.edit().putInt(HIGH_SCORE_KEY, highScore).commit();
    }

    public boolean getMusic(){
        return sharedPreferences.getBoolean(IS_MUSIC_KEY, false);
    }

    public void putMusic(Boolean ismusic){
        sharedPreferences.edit()
                .putBoolean(IS_MUSIC_KEY, ismusic).commit();
    }

    public boolean getEffect(){
        return sharedPreferences.getBoolean(IS_EFFECTS_KEY, false);
    }

    public void putEffect(Boolean effect){
        sharedPreferences.edit()
                .putBoolean(IS_EFFECTS_KEY, effect).commit();
    }
}
