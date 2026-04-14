
package com.felixistcool.taptap;

import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Timer;
import java.util.prefs.Preferences;


public class MainActivity extends AppCompatActivity {


    Button buttonplay;

    TextView tvhighscore;

    TextView tvlastround;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tvhighscore = (TextView) findViewById(R.id.tvhighscore);

        tvlastround = (TextView) findViewById(R.id.tvlastround);

        SharedPreferences points_last_round = getSharedPreferences("KEY2", MODE_PRIVATE);
        int pointslastround =points_last_round.getInt("last_points", 0);

        tvlastround.setText(Integer.toString(pointslastround));


        SharedPreferences sharedPreferences = getSharedPreferences("KEY1", MODE_PRIVATE);
        int bestpoints = sharedPreferences.getInt("bestpoints", 0);

        tvhighscore.setText(Integer.toString(bestpoints));


        //button
        buttonplay = (Button) findViewById(R.id.buttonplay);

        buttonplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ActivityPlay öffnen
                openactivityplay();

                finish();






            }
        });
    }


    public void openactivityplay() {

        Intent intent = new Intent(this, Play.class);
        startActivity(intent);
    }

}