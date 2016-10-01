package com.example.merliora.pomodorotimer;


/**
 * Created by TZE KANG on 9/23/2016.
 */
public class Pomodoro {

    private String creationTime;
    private boolean completed;
    private boolean ended;

    public Pomodoro(String creationTime, boolean b){
        this.creationTime = creationTime;
        this.completed = b;
        this.ended = false;
    }

    public String getCreationTime() { return creationTime;}

    public void setCreationTime(String creationTime) { this.creationTime = creationTime;}

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
