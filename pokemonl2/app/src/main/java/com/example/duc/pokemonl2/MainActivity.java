package com.example.duc.pokemonl2;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.example.duc.pokemonl2.Setting.Musics;
import com.example.duc.pokemonl2.Setting.SoundEffects;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    SoundPool sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Preferences.init(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        DbHelper.init(this);
        ButterKnife.bind(this);
        SoundEffects.init(this);
        Musics.init(this);
        changeFragment(new MenuFragment(),false);
        SoundEffects.getInstance(this).playSoundCorrect();
    }

    public void changeFragment(Fragment fragment, boolean addtoBackStack){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().
                beginTransaction().replace(R.id.fl_container, fragment);
        if(addtoBackStack){
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    @Subscribe
    public void onEvent(Fragment fragment){
        changeFragment(fragment,true);
    }


}
