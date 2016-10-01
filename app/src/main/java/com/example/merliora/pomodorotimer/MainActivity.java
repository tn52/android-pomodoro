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

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    MyDBHandler dbHandler;

    public static final int COUNTDOWN_DURATION_MINUTES = 25;
    public static final int INTERVAL_DURATION_SECONDS = 1;
    public static final String DEBUG = "Testing";
    public boolean isPaused = true;
    public ArrayList<Pomodoro> completedPomodoroList = new ArrayList<Pomodoro>();
    public ArrayList<Pomodoro> incompletePomodoroList = new ArrayList<Pomodoro>();
    public Pomodoro currentPomodoro;
    public TextView debugText;

    Chronometer chronometer;

    CountDownTimerPausable countDownTimer = new CountDownTimerPausable(COUNTDOWN_DURATION_MINUTES*60*1000, INTERVAL_DURATION_SECONDS*1000){

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

        dbHandler = new MyDBHandler(this, null, null, 1);

        Button startButton = (Button)findViewById(R.id.startButton);

        debugText = (TextView)findViewById(R.id.debugTextView);
        debugText.setText("debug: " + 5);

        startButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v) {
                        countDownTimer.start();
                        chronometer.start();
                        if (currentPomodoro==null || currentPomodoro.getEnded()){
                            Long timeStampLong = System.currentTimeMillis()/1000;
                            String timeStamp = timeStampLong.toString();
                            currentPomodoro = new Pomodoro(timeStamp, false);
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

                                dbHandler.addPomodoro(currentPomodoro);
                                int count = dbHandler.getNumPomodoro();
//                                TextView debugText = (TextView)findViewById(R.id.debugTextView);
                                System.out.println("******COUNT******" + count);
//                                debugText.setText(count);


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
