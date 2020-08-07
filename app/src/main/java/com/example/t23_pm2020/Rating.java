package com.example.t23_pm2020;

public class Rating {
    private String uid;
    private int score;

    public Rating(String uid) {
        this.uid = uid;

    }
    public Rating(String uid,int score) {
        this.uid = uid;
        setScore(score);

    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getScore() {
        return score;
    }

    public boolean setScore(int score) {
        if(score<=5 && score >=0) {
            this.score = score;
            return true;
        }
        else return false;
    }


}
