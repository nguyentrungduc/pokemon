package com.example.duc.pokemonl2.Setting;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.SoundPool;

import java.io.IOException;

/**
 * Created by DUC on 25/11/2016.
 */

public class Musics {
    private SoundPool soundPool;
    private int music;

    private static Musics instance;

    private Musics(Context context){
        this.soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        loadSoundToPool(context);
    }

    public static Musics getInstance(Context context){
        return  instance;
    }

    public static void init(Context context) {
        instance = new Musics(context);
    }

    public AssetFileDescriptor loadSoundToPool(Context context) {
        try {
            music = soundPool.
                    load(context.getAssets()
                            .openFd("8_bit_mayhem.mp3"), 0);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
