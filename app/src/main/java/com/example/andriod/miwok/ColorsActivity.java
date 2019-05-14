package com.example.andriod.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    // start media player
    private MediaPlayer mMediaplayer;
    private AudioManager mAudioManager;
    private WordAdapter mAdapter;


    // releases audio back to start after completion.
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {

            releaseMediaplayer();

        }
    };

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mMediaplayer.pause();
                mMediaplayer.seekTo(0);
            }else if (focusChange == AudioManager.AUDIOFOCUS_GAIN){
                mMediaplayer.start();
            }else if (focusChange == AudioManager.AUDIOFOCUS_LOSS){
                mMediaplayer.stop();
                releaseMediaplayer();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

        final ArrayList<Word> numberList = new ArrayList<>();


        numberList.add (new Word("red", "wetetti", R.drawable.color_red, R.raw.color_red));
        numberList.add(new Word("mustard yellow", "chiwiiṭә", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));
        numberList.add(new Word("dusty yellow", "ṭopiisә", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        numberList.add(new Word("green", "chokokki", R.drawable.color_green, R.raw.color_green));
        numberList.add(new Word("brown", "takaakki", R.drawable.color_brown, R.raw.color_brown));
        numberList.add(new Word("gray", "topoppi", R.drawable.color_gray, R.raw.color_gray));
        numberList.add(new Word("black", "kululli", R.drawable.color_black, R.raw.color_black));
        numberList.add(new Word("white", "kelelli", R.drawable.color_white, R.raw.color_white));



        WordAdapter adapter =
                new WordAdapter(this, numberList, R.color.category_colors);



        ListView listView = findViewById(R.id.list);

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word number = numberList.get(position);

                // reset audio listener if sound already exist to play another sound
                if (number.hasSound()) {
                    releaseMediaplayer();


                    int result = mAudioManager.requestAudioFocus(mOnAudioFocusListener, AudioManager.STREAM_MUSIC,
                            AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                    if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                        //start audio player
                        mMediaplayer = MediaPlayer.create(ColorsActivity.this, number.getMAudioResourceId());

                        mMediaplayer.start();

                        //set up listener to the current media file
                        mMediaplayer.setOnCompletionListener(mCompletionListener);
                    }
                }
            }
        });



    }


    // release the media player when it completes a current audio.
    private void releaseMediaplayer(){

        if (mMediaplayer != null){
            mMediaplayer.release();

            mMediaplayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusListener);
        }
    }

    // called to clean up media file after user leaves the app
    @Override
    public void onStop(){
        super.onStop();
        releaseMediaplayer();
    }

}


