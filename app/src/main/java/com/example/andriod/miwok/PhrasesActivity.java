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

public class PhrasesActivity extends AppCompatActivity {


    // used for playing audio files
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

    // used to create audio focus{@ sound} to be called when audio is
    // paused or released.
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

      final   ArrayList<Word> numberList = new ArrayList<>();


        numberList.add (new Word("Where are you going?", "minto wuksus",  R.drawable.where_are_you_going, R.raw.phrase_where_are_you_going));
        numberList.add(new Word("What is your name?", "tinnә oyaase'nә", R.drawable.what_is_your_name, R.raw.phrase_what_is_your_name));
        numberList.add(new Word("My name is ...", "oyaaset...", R.drawable.my_name_is, R.raw.phrase_my_name_is));
        numberList.add(new Word("How are you feeling", "michәksәs", R.drawable.how_are_you_feeling, R.raw.phrase_how_are_you_feeling));
        numberList.add(new Word("I'm feeling good", "kuchi achit", R.drawable.am_feeling_good, R.raw.phrase_im_feeling_good));
        numberList.add(new Word("Are you coming?", "әәnәs'aa?", R.drawable.are_you_coming, R.raw.phrase_are_you_coming));
        numberList.add(new Word("yes, I'm coming", "hәә’ әәnәm", R.drawable.yes_i_am_coming, R.raw.phrase_yes_im_coming));
        numberList.add(new Word("I'm coming", "әәnәm", R.drawable.i_am_coming, R.raw.phrase_im_coming));
        numberList.add(new Word("Let's go", "wo'e", R.drawable.lets_go, R.raw.phrase_lets_go));
        numberList.add(new Word("Come here", "әnni'nem", R.drawable.come_here, R.raw.phrase_come_here));



        WordAdapter adapter =
                new WordAdapter(this, numberList, R.color.category_phrases);



        ListView listView = findViewById(R.id.list);

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word number = numberList.get(position);

                // release the media player if it currently exist because we are about
                // to play another sound
                if (number.hasSound()) {

                    int result = mAudioManager.requestAudioFocus(mOnAudioFocusListener, AudioManager.STREAM_MUSIC,
                            AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                    if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {


                        mMediaplayer = MediaPlayer.create(PhrasesActivity.this, number.getMAudioResourceId());

                        mMediaplayer.start();

                        // set up a listener on the current media file
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
