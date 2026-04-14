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

import java.util.Random;
import java.util.prefs.Preferences;

public class Play extends AppCompatActivity implements View.OnClickListener {

    //buttons
    Button button1;
    Button button2;
    Button button3;
    Button button4;

    //Imageviews
    ImageView iv1;
    ImageView iv2;
    ImageView iv3;
    ImageView iv4;

    //Textviews
    TextView tv1;
    TextView tv2;
    TextView tvtime;
    TextView tvpoints;


    //Generierte Zufallszahl
    int level;

    // Int Aktuelle Punkte
    int pointcounter = 0;

    //bester Puktestand
    int best;


    int timerstatus;

    int k = 0;

    //punkte der letzten runde
    int points_last;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);


        tv1 = (TextView) findViewById(R.id.tv1);

        tv1.setText(Integer.toString(pointcounter));

        tv2 = (TextView) findViewById(R.id.tv2);

        tvtime = (TextView) findViewById(R.id.tvtime);
        tvtime.setText("time:");


        tvpoints = (TextView) findViewById(R.id.tvpoints);
        tvpoints.setText("points:");


        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);


        SharedPreferences sharedPreferences = getSharedPreferences("KEY1", MODE_PRIVATE);
        best = sharedPreferences.getInt("bestpoints", 0);

        SharedPreferences points_last_round = getSharedPreferences("KEY2", MODE_PRIVATE);
        points_last = points_last_round.getInt("last_points",0 );


        //Spiel starten
        play();

        //timeraufrufen
        timer();


    }

    //spielzeit berechnen
    public void timer() {
        //timer
        new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tv2.setText("" + millisUntilFinished / 1000);

                if (k == 0) {
                    timerstatus = 1;
                    k++;
                }
            }


            @Override
            public void onFinish() {

                //Überprüft ob der timer noch laufen soll
                if (timerstatus == 1) {

                    if (pointcounter > best) {

                        //beste punktespeichern
                        best = pointcounter;

                        savebest();
                    }
                    //Toast massage anzeigen
                    Toast.makeText(Play.this, "time out", Toast.LENGTH_SHORT).show();

                    safe_last_points();

                    close();
                }

            }
            //timer starten
        }.start();


    }


    protected void play() {

        // generate random nummber
        int randomnumber = randomnumber();


        if (randomnumber == 1) {


            button1.setBackgroundResource(R.drawable.greenbutton_layout);
            button2.setBackgroundResource(R.drawable.blackbuttoncolor_layout);
            button3.setBackgroundResource(R.drawable.blackbuttoncolor_layout);
            button4.setBackgroundResource(R.drawable.blackbuttoncolor_layout);


            level = 1;


        }


        if (randomnumber == 2) {

            button2.setBackgroundResource(R.drawable.greenbutton_layout);
            button1.setBackgroundResource(R.drawable.blackbuttoncolor_layout);
            button3.setBackgroundResource(R.drawable.blackbuttoncolor_layout);
            button4.setBackgroundResource(R.drawable.blackbuttoncolor_layout);

            level = 2;


        }


        if (randomnumber == 3) {
            button3.setBackgroundResource(R.drawable.greenbutton_layout);
            button1.setBackgroundResource(R.drawable.blackbuttoncolor_layout);
            button2.setBackgroundResource(R.drawable.blackbuttoncolor_layout);
            button4.setBackgroundResource(R.drawable.blackbuttoncolor_layout);

            level = 3;


        }

        if (randomnumber == 4) {

            button4.setBackgroundResource(R.drawable.greenbutton_layout);
            button1.setBackgroundResource(R.drawable.blackbuttoncolor_layout);
            button2.setBackgroundResource(R.drawable.blackbuttoncolor_layout);
            button3.setBackgroundResource(R.drawable.blackbuttoncolor_layout);

            level = 4;
        }
    }


    public static int randomnumber() {

        //Zufallszahl generieren
        int x;
        Random rand = new Random();
        x = rand.nextInt(4) + 1;

        //Zufallszahl wird zurückgegeben
        return x;
    }

    //randomzahl wird ausgewärtet
    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.button1:
                if (level == 1) {
                    right();


                } else {
                    notright();

                }
                break;


            case R.id.button2:
                if (level == 2) {
                    right();

                } else {
                    notright();

                }
                break;

            case R.id.button3:
                if (level == 3) {
                    right();

                } else {
                    notright();

                }
                break;

            case R.id.button4:
                if (level == 4) {
                    right();

                } else {
                    notright();
                    break;
                }
        }
    }


    //wenn richtiges Feld getippt wurede

    public void right() {

        //Aktuelle Punkte um 1 erhöhen
        pointcounter++;
        //Aktuelle Punkte anzeigen
        tv1.setText(Integer.toString(pointcounter));
        //weiter spielen
        play();

    }

    //wenn falsches Feld getippt wurde
    public void notright() {

        timerstatus = 2;

        if (pointcounter > best) {

            //beste punktespeichern
            best = pointcounter;
        }

        savebest();
        safe_last_points();

        //Toast massage anzeigen
        Toast.makeText(Play.this, "you lose", Toast.LENGTH_SHORT).show();

        close();
    }

    //beste Punkte speichern
    public void savebest() {


        SharedPreferences sharedPref = getSharedPreferences("KEY1", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("bestpoints", best);
        editor.commit();
    }

    //punkte der runde speichern
    private void safe_last_points(){
        SharedPreferences points_last_round = getSharedPreferences("KEY2", MODE_PRIVATE);
        SharedPreferences.Editor last_points_ed = points_last_round.edit();
        last_points_ed.putInt("last_points", pointcounter);
        last_points_ed.commit();
    }

    //Fenster schließen
    private void close() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        finish();
    }
}



