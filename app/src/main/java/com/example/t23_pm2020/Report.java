package com.example.t23_pm2020;

public class Report {
    public static final  String[] REPORT_TYPE= {"dirty","closed","noisy","maintenance","crowded","vacantParking"};
    public static final String[] REPORT_TYPE_STR_NUMBER={"0","1","2","3","4","5"};
    private String uid;
    private long time;
    private int type;
    private String noticed;
    public Report(String uid, long time, int type) {
        this.uid = uid;
        this.time = time;
        this.type = type; //0 - wrong, 1 - new update, 2 - helpful
        this.noticed = "false";
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public String getNoticed() {
        return noticed;
    }

    public void setNoticed(String noticed) {
        this.noticed = noticed;
    }

    public void setType(int type) {
        this.type = type;
    }
}
