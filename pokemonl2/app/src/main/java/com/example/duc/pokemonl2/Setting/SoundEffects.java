package com.example.duc.pokemonl2.Setting;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by DUC on 25/11/2016.
 */

public class SoundEffects {
    private SoundPool soundPool;
    private int soundCorrect;
    private int soundIncorrect;
    private int soundClick;
    private Boolean soundEffectOn = true;
    private static SoundEffects instance;
    
    private SoundEffects(Context context){
        this.soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        loadSoundToPool(context);
    }

    public static SoundEffects getInstance(Context context){
        return  instance;
    }

    public static void init(Context context) {
        instance = new SoundEffects(context);
    }

    public void loadSoundToPool(Context context) {
        try {
            soundCorrect = soundPool.
                    load(context.getAssets()
                            .openFd("8_bit_mayhem.mp3"), 0);
            soundIncorrect = soundPool.
                    load(context.getAssets()
                            .openFd("sounds/firered_00a3.wav"), 0);
            soundCorrect = soundPool.
                    load(context.getAssets()
                            .openFd("sounds/uiclick.wav"), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playSoundCorrect(){
        soundPool.play(soundCorrect,1,1,0,0,1);
    }



}
