package com.example.merliora.pomodorotimer;

/**
 * Created by TZE KANG on 9/23/2016.
 */
public class Pomodoro {

    private boolean completed;
    private boolean ended;

    public Pomodoro(boolean b){
        completed = b;
        ended = false;
    }

    public void setCompleted(boolean b){
        completed = b;
    }

    public boolean getCompleted(){
        return completed;
    }

    public void setEnded(boolean b){
        ended = b;
    }

    public boolean getEnded(){
        return ended;
    }
}
