package com.example.andriod.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    private MediaPlayer mMediaplayer;
    private AudioManager mAudioManager;
    private Adapter mAdapter;




        // releases audio back to start after completion.
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {

            releaseMediaplayer();

        }
    };


    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
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


        numberList.add(new Word("one", "lutti", R.drawable.number_one, R.raw.number_one));
        numberList.add(new Word("two", "ottiko", R.drawable.number_two, R.raw.number_two));
        numberList.add(new Word("three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        numberList.add(new Word("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        numberList.add(new Word("five", "massokka", R.drawable.number_five, R.raw.number_five));
        numberList.add(new Word("six", "temmokka", R.drawable.number_six, R.raw.number_six));
        numberList.add(new Word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        numberList.add(new Word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        numberList.add(new Word("nine", "wo'e", R.drawable.number_nine, R.raw.number_nine));
        numberList.add(new Word("ten", "na'aacha", R.drawable.number_ten, R.raw.number_ten));


        WordAdapter adapter =
                new WordAdapter(this, numberList, R.color.category_numbers);

        // find and set list view for each of the item {@number list}
        ListView listView = findViewById(R.id.list);

        listView.setAdapter(adapter);


        // set a click listener to play an item when the item is clicked.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Word number = numberList.get(position);

               // release the media player if it currently exist because
                //we are about to play another sound.
                if (number.hasSound()) {
                    releaseMediaplayer();

                    int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC,
                            AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                    if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {


                        // create and set up the {@link mediaplayer} for the audio resource associated
                        // with the current word.
                        mMediaplayer = MediaPlayer.create(NumbersActivity.this, number.getMAudioResourceId());

                        // start the audio file.
                        mMediaplayer.start();

                        // set up a listener on the current media file
                        mMediaplayer.setOnCompletionListener(mCompletionListener);


                    }

                }
            }
        });


    }

    // called to clean up media file after user leaves the app
    @Override
    public void onStop(){
        super.onStop();
        releaseMediaplayer();
    }

    // release the media player when it completes a current audio.
    private void releaseMediaplayer(){

        if (mMediaplayer != null){
            mMediaplayer.release();

            mMediaplayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }






}





