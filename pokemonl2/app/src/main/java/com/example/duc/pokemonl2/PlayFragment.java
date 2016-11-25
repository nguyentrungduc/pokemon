package com.example.duc.pokemonl2;


import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.duc.pokemonl2.Setting.Musics;
import com.example.duc.pokemonl2.Setting.SoundEffects;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlayFragment extends Fragment {

    public static final String TAG = MainActivity.class.toString();

    @BindView(R.id.tv_Score)
    TextView tvScore;
    @BindView(R.id.iv_Pokemon)
    ImageView ivPokemon;
    @BindView(R.id.tv1)
    TextView t1;
    @BindView(R.id.tv2)
    TextView t2;
    @BindView(R.id.tv3)
    TextView t3;
    @BindView(R.id.tv4)
    TextView t4;
    @BindView(R.id.ll_Play)
    LinearLayout llplay;
    @BindView(R.id.rl_1)
    RelativeLayout rl1;
    @BindView(R.id.rl_2)
    RelativeLayout rl2;
    @BindView(R.id.rl_3)
    RelativeLayout rl3;
    @BindView(R.id.rl_4)
    RelativeLayout rl4;
    @BindView(R.id.tv_Tag)
    TextView tvtag;
    @BindView(R.id.pb_Time)
    ProgressBar pbtime;
    Pokemon pokemon;
    int time;
    int score = 0;
    CountDownTimer countdown;
    ArrayList<TextView> textViews = new ArrayList<>();
    ArrayList<RelativeLayout> relativeLayouts = new ArrayList<>();

    public PlayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_play, container, false);
        ButterKnife.bind(this, view);
        init();
        countdown();
        setupUI();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        setupSetting();
    }

    public void countdown(){
        time = 0;
        pbtime.setProgress(time);
        countdown = new CountDownTimer(20000, 1000) {
            public void onTick(long millisUntilFinished) {
                time += (pbtime.getMax() / 20);
                pbtime.setProgress(time);
            }
            public void onFinish() {
                Log.d(TAG,"ok"+String.valueOf(Preferences.getInstance().getHighScore()));
                if(score > Preferences.getInstance().getHighScore()){
                    Log.d(TAG,"HighScore");
                    Preferences.getInstance().putHighScore(score);
                    Log.d(TAG, "New High Score "+String.valueOf(Preferences.getInstance().getHighScore()));
                }
                EventBus.getDefault().post(new MenuFragment());
            }

        }.start();
    }
    private void setImageBlack(Pokemon pokemon) {
        Bitmap bitmap = loadImage(pokemon);
        Bitmap b = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        for (int x = 0; x < b.getWidth(); x++) {
            for (int y = 0; y < b.getHeight(); y++) {
                if (b.getPixel(x, y) != Color.TRANSPARENT) {
                    b.setPixel(x, y, Color.BLACK);
                }
            }
        }
        ivPokemon.setImageBitmap(b);
    }

    private Bitmap loadImage(Pokemon pokemon) {
        // get input stream
        InputStream ims = null;
        try {
            ims = getContext().getAssets().open("images/"+ pokemon.getImg());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(ims);
        return bitmap;
    }

    private void setupSetting(){
//        AssetFileDescriptor afd =Musics.getInstance(getContext()).loadSoundToPool(getContext());
//        MediaPlayer player = new MediaPlayer();
//        try {
//            player.setDataSource(afd.getFileDescriptor(),
//                    afd.getStartOffset(), afd.getLength());
//            player.prepare();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (Preferences.getInstance().getMusic()) {
//            player.start();
//        }
//        if (Preferences.getInstance().getEffect()) {
//            //SoundEffects.getInstance(getContext()).loadSoundToPool(getContext());
//        }
    }

    private void setupUI(){
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/StencilStd.ttf");
        tvScore.setTypeface(tf);
        pokemon = DbHelper.getInstance().selectRandomPokemon();
        ArrayList<Pokemon> pokemonList =
                (ArrayList<Pokemon>) DbHelper.getInstance().getRandomPokeSameGen(pokemon.getGen(),pokemon.getId());
        pickquestion(pokemon,pokemonList);
    }

    public void checkResult(Pokemon pokemon, TextView tv,RelativeLayout rl){
        int index = 0;
        for(int i = 0; i < textViews.size(); i++){
            if(textViews.get(i).getText() == pokemon.getName()){
                index = i;
            }
        }
        ivPokemon.setImageBitmap(loadImage(pokemon));
        tvtag.setText(pokemon.getTag()+" "+pokemon.getName());
        tv.setBackgroundColor(Color.parseColor("#00FFFFFF"));
        if(tv.getText() == pokemon.getName()){
            score++;
            tvScore.setText(String.valueOf(score));
            correct(rl);
        }
        else {
            relativeLayouts.get(index).setBackgroundResource(R.drawable.custom_view_circular_green);
            incorrect(rl);
        }
        countdown = new CountDownTimer(300, 1000) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                setupUI();
            }

        }.start();
        for(TextView tv1 : textViews){
            tv1.setEnabled(false);
        }

    }

    public void correct(RelativeLayout rl){
        rl.setBackgroundResource(R.drawable.custom_view_circular_green);
    }

    public  void incorrect(RelativeLayout rl){
        rl.setBackgroundResource(R.drawable.custom_view_circular_red);
    }

    public void init(){
        textViews.add(t1);
        textViews.add(t2);
        textViews.add(t3);
        textViews.add(t4);
        relativeLayouts.add(rl1);
        relativeLayouts.add(rl2);
        relativeLayouts.add(rl3);
        relativeLayouts.add(rl4);
    }

    private void pickquestion(Pokemon pokemon, ArrayList<Pokemon> pokemonList) {
        reset();
        Random rand = new Random();
        llplay.setBackgroundColor(Color.parseColor(pokemon.getColor()));
        int  n = rand.nextInt(4);
        switch (n){
            case 0:{
                t1.setText(pokemon.getName());
                break;
            }
            case 1:{
                t2.setText(pokemon.getName());
                break;
            }
            case 2:{
                t3.setText(pokemon.getName());
                break;
            }
            case 3:{
                t4.setText(pokemon.getName());
                break;
            }
        }
        int i = 0;
        for(TextView t : textViews){
            if(t.getText()==""){
                t.setText(pokemonList.get(i).getName());
                i++;
            }
        }
        setImageBlack(pokemon);
    }


    @OnClick(R.id.tv1)
    public void check1(){
        checkResult(pokemon,t1,rl1);
    }

    @OnClick(R.id.tv2)
    public void check2(){
        checkResult(pokemon,t2,rl2);
    }
    @OnClick(R.id.tv3)
    public void check3(){
        checkResult(pokemon,t3,rl3);
    }
    @OnClick(R.id.tv4)
    public void check4(){
        checkResult(pokemon,t4,rl4);
    }

    public void reset(){
        for(RelativeLayout rl : relativeLayouts){
            rl.setBackgroundResource(R.drawable.custom_view_circular_white);
        }
        for(TextView tv : textViews){
            tv.setText("");
            tv.setEnabled(true);
        }
        tvtag.setText("");
    }

}
