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

public class FamilyActivity extends AppCompatActivity {

    // plays audio
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


        numberList.add (new Word("father", "әpә", R.drawable.family_father, R.raw.family_father));
        numberList.add(new Word("mother", "әṭa", R.drawable.family_mother, R.raw.family_mother));
        numberList.add(new Word("son", "angsi", R.drawable.family_son, R.raw.family_son));
        numberList.add(new Word("daughter", "tune", R.drawable.family_daughter, R.raw.family_daughter));
        numberList.add(new Word("older brother", "taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        numberList.add(new Word("younger brother", "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        numberList.add(new Word("older sister", "teṭe", R.drawable.family_older_sister, R.raw.family_older_sister));
        numberList.add(new Word("younger sister", "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        numberList.add(new Word("grandmother", "ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        numberList.add(new Word("grandfather", "paapa", R.drawable.family_grandfather, R.raw.family_grandfather));



        WordAdapter adapter =
                new WordAdapter(this, numberList, R.color.category_family);



        ListView listView = findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word number = numberList.get(position);

                // release media player to play another sound if there is
                // a sound already playing
                if (number.hasSound()) {
                    releaseMediaplayer();
                    int result = mAudioManager.requestAudioFocus(mOnAudioFocusListener, AudioManager.STREAM_MUSIC,
                            AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                    if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {


                        // start playing sound
                        mMediaplayer = MediaPlayer.create(FamilyActivity.this, number.getMAudioResourceId());

                        mMediaplayer.start();

                        // release media player when sound has played completely
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

