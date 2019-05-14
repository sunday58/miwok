package com.example.andriod.miwok;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");


        TextView numbers =  findViewById(R.id.numbers);
        TextView family = findViewById(R.id.family);
        TextView color = findViewById(R.id.colors);
        TextView phrases = findViewById(R.id.phrases);


        phrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phraseIntent = new Intent(MainActivity.this, PhrasesActivity.class);

                startActivity(phraseIntent);
            }
        });


color.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Intent colors = new Intent(MainActivity.this, ColorsActivity.class);

        startActivity(colors);

    }
});

        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent family = new Intent(MainActivity.this, FamilyActivity.class);

                startActivity(family);

            }
        });
        numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent numbers = new Intent(MainActivity.this, NumbersActivity.class);

                startActivity(numbers);

            }
        });

    }





}
