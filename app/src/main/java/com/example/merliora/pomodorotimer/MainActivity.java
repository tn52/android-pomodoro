package com.example.merliora.pomodorotimer;

//import android.os.CountDownTimer;
import android.content.Context;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.graphics.Color;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View;
import android.widget.Chronometer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    public static final String DEBUG = "Testing";
    public boolean isPaused = true;
    public ArrayList<Pomodoro> completedPomodoroList = new ArrayList<Pomodoro>();
    public ArrayList<Pomodoro> incompletePomodoroList = new ArrayList<Pomodoro>();
    public Pomodoro currentPomodoro;

    Chronometer chronometer;

    CountDownTimerPausable countDownTimer = new CountDownTimerPausable(1500, 1000){

        GregorianCalendar gc = new GregorianCalendar();
        Date time = gc.getTime();
        SimpleDateFormat shortDateFormat = new SimpleDateFormat("EEE MMM dd");

        @Override
        public void onTick(long millisUntilFinished) {
            TextView countDownText = (TextView)findViewById(R.id.countdownTextView);
            countDownText.setText("Time: " + String.format("%d:%02d", millisUntilFinished/1000 / 60, millisUntilFinished/1000 % 60));
        }

        @Override
        public void onFinish() {
            TextView countDownText = (TextView)findViewById(R.id.countdownTextView);
            countDownText.setText("TIMES UP!");
            currentPomodoro.setCompleted(true);
            completedPomodoroList.add(currentPomodoro);

            TextView completedText = (TextView)findViewById(R.id.completedTextView);
            completedText.setText("Complete: " + completedPomodoroList.size());
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.setBase(SystemClock.elapsedRealtime());

        Button startButton = (Button)findViewById(R.id.startButton);

        startButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v) {
                        countDownTimer.start();
                        chronometer.start();
                        if (currentPomodoro==null || currentPomodoro.getEnded()){
                            currentPomodoro = new Pomodoro(false);
                        }
                    }
                }
        );


        Button pauseButton = (Button)findViewById(R.id.pauseButton);

        pauseButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        countDownTimer.pause();
                        chronometer.stop();
                    }
                }
        );


        Button resetButton = (Button)findViewById(R.id.resetButton);

        resetButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        countDownTimer.pause();
                        countDownTimer.reset();
                        TextView countDownText = (TextView)findViewById(R.id.countdownTextView);
                        countDownText.setText("Time: " + String.format("%d:%02d", countDownTimer.millisRemaining/1000 / 60, countDownTimer.millisRemaining/1000 % 60));
                        chronometer.setBase(SystemClock.elapsedRealtime());

                        if(currentPomodoro!=null) {
                            currentPomodoro.setEnded(true);
                            if (currentPomodoro.getCompleted() == false) {
                                incompletePomodoroList.add(currentPomodoro);
                            } else {
//                                completedPomodoroList.add(currentPomodoro);
                            }
                        }

                        TextView incompletedText = (TextView)findViewById(R.id.incompleteTextView);
                        incompletedText.setText("Incompleted: " + incompletePomodoroList.size());

                    }

                }
        );


    }

    public void updateNumPomodoros(){
        TextView completedText = (TextView)findViewById(R.id.completedTextView);
        completedText.setText("Complete: " + completedPomodoroList.size());
        TextView incompletedText = (TextView)findViewById(R.id.incompleteTextView);
        incompletedText.setText("Incompleted: " + incompletePomodoroList.size());
    }



}
