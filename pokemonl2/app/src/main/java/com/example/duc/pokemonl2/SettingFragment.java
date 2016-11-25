package com.example.duc.pokemonl2;


import android.content.res.AssetFileDescriptor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.duc.pokemonl2.Setting.Musics;
import com.example.duc.pokemonl2.Setting.SoundEffects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {

    public static final String TAG = MainActivity.class.toString();

    CheckBox[] checkBoxes;
    ImageView[] imageViews;
    ArrayList<CheckBox> listCheckBox;
    ArrayList<ImageView> listImageView;
    @BindView(R.id.tv_Title)
    TextView tvTitle;
    @BindView(R.id.gen1)
    ImageView igen1;
    @BindView(R.id.gen2)
    ImageView igen2;
    @BindView(R.id.gen3)
    ImageView igen3;
    @BindView(R.id.gen4)
    ImageView igen4;
    @BindView(R.id.gen5)
    ImageView igen5;
    @BindView(R.id.gen6)
    ImageView igen6;
    @BindView(R.id.cb1)
    CheckBox c1;
    @BindView(R.id.cb2)
    CheckBox c2;
    @BindView(R.id.cb3)
    CheckBox c3;
    @BindView(R.id.cb4)
    CheckBox c4;
    @BindView(R.id.cb5)
    CheckBox c5;
    @BindView(R.id.cb6)
    CheckBox c6;
    @BindView(R.id.sound)
    Switch swsound;
    @BindView(R.id.music)
    Switch swmusic;

    private MediaPlayer player;
    public SettingFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, view);
        checkBoxes = new CheckBox[]{
                c1, c2, c3, c4, c5, c6
        };
        imageViews = new ImageView[]{
                igen1, igen2, igen3, igen4, igen5, igen6
        };
        listCheckBox = new ArrayList<>(Arrays.asList(checkBoxes));
        listImageView = new ArrayList<>(Arrays.asList(imageViews));
        setupUI();
        addListener();
        return view;
    }




    private void addListener() {
        for (int i = 0; i < listCheckBox.size(); i++) {
            final CheckBox checkBox = listCheckBox.get(i);
            final ImageView imageView = listImageView.get(i);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (checkBox.isChecked()) {
                        imageView.setAlpha((float) 1);
                    } else {
                        imageView.setAlpha((float) 0.3);
                    }
                }
            });
        }
    }

    private void startSound(String filename){
        try {
            AssetFileDescriptor afd = getActivity().getAssets().openFd(filename);
            player = new MediaPlayer();
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.start();
    }

    private void setupUI(){
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/PoplarStd.ttf");
        tvTitle.setTypeface(tf);
        if (Preferences.getInstance().getMusic()) {
            swmusic.setChecked(true);
        }
        if(!Preferences.getInstance().getMusic()){
            swmusic.setChecked(false);
        }
        if(Preferences.getInstance().getEffect()){
            swsound.setChecked(true);
        }
        if(!Preferences.getInstance().getEffect()){
            swsound.setChecked(false);
        }
        swmusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Log.d(TAG,"on");
                    Musics.getInstance(getContext()).loadSoundToPool(getContext());
                    Preferences.getInstance().putMusic(true);
                }
                else{

                    Preferences.getInstance().putMusic(false);
                }
            }
        });
        swsound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Log.d(TAG,"ON");
                    startSound("sounds/uiclick.wav");
                    Preferences.getInstance().putEffect(true);
                }
                else{
                    Preferences.getInstance().putEffect(false);
                }
            }
        });
    }


}
