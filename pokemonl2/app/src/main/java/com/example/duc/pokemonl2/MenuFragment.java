package com.example.duc.pokemonl2;


import android.graphics.Typeface;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.duc.pokemonl2.Setting.SoundEffects;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {
    private int highscore = 0;
    @BindView(R.id.btn_Play)
    ImageView btnPlay;
    @BindView(R.id.btn_Setting)
    Button btnSetting;
    @BindView(R.id.tv_HighScore)
    TextView tvHighScore;
    @BindView(R.id.tv_HighScoreText)
    TextView tvHighScoreText;

    public static final String TAG = MainActivity.class.getSimpleName();
    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        ButterKnife.bind(this, view);
        setupUI();

        // Inflate the layout for this fragment
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        btnPlay.setLayoutParams(new RelativeLayout.LayoutParams(width*4/10,width*4/10));
        btnPlay.setY(height/8*5-width/5);
        btnPlay.setX(width / 2 - width/5);

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
        int highScore = Preferences.getInstance().getHighScore();
        tvHighScore.setText(String.format("%s", highScore));

    }

    private void setupUI(){
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/StencilStd.ttf");
        Typeface tf1 = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/PoplarStd.ttf");
        tvHighScore.setTypeface(tf);
        tvHighScoreText.setTypeface(tf1);
    }
    @OnClick(R.id.btn_Setting)
    public void onClickSetting(){
        EventBus.getDefault().post(new SettingFragment());
    }

    @OnClick(R.id.btn_Play)
    public void onClickPlay(){
        EventBus.getDefault().post(new PlayFragment());
    }

}
