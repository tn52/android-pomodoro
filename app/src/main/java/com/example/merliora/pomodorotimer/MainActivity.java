package com.example.merliora.pomodorotimer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.graphics.Color;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    public static final String DEBUG = "Testing";
    public boolean isPaused = true;


    CountDownTimer countDownTimer = new CountDownTimer(30000, 1000){
        @Override
        public void onTick(long millisUntilFinished) {
            TextView countDownText = (TextView)findViewById(R.id.countdownTextView);
            countDownText.setText("Time: " + millisUntilFinished/1000);
        }

        @Override
        public void onFinish() {
            TextView countDownText = (TextView)findViewById(R.id.countdownTextView);
            countDownText.setText("TIMES UP!");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button startButton = (Button)findViewById(R.id.startButton);




        startButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        TextView countDownText = (TextView)findViewById(R.id.countdownTextView);
                        countDownTimer.start();
                    }
                }
        );


        Button resetButton = (Button)findViewById(R.id.pauseButton);

        resetButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        TextView countDownText = (TextView)findViewById(R.id.countdownTextView);
                        countDownTimer.cancel();
                    }
                }
        );

    }





}
