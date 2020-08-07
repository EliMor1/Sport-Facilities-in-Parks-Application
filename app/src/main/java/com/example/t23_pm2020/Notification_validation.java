package com.example.t23_pm2020;

public class Notification_validation {
    String title;
    String text;
    int id;

//class not used at the moment
    public Notification_validation(String title, String text, int id) {
        this.title=title;
        this.text=text;
        this.id = id;// 1=dirty , 2=closed , 3= Noisy
    }
    public Notification_validation( int id) {
        this.id = id;// 0=dirty , 1=closed , 2= Noisy
        setTitle(id);
        setText(id);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(int id) {
        this.title = title ;
        switch (id) {
            case 0:
                this.title = "Thanks for your help";
                break;
            case 1:
                this.title = "Thanks for helping others know ";
                break;
            case 2:
                this.title = "Thanks for letting us know";
                break;
            case 3:
                this.title = "Thanks";
                break;
            case 4:
                this.title = "Thanks for Voting";
                break;
            default:
                this.title = "Your vote was taken into consideration";
                break;
        }
    }

    public String getText() {
        return text;
    }
/*
public static final  String[] REPORT_TYPE= {"dirty","closed","noisy","maintenance","crowded"};
public static final String[] REPORT_TYPE_STR_NUMBER={"0","1","2","3","4"};
 */
    public void setText(int id) {
        switch (id) {
            case 0:
                this.text= "The park has been cleaned.";
                break;
            case 1:
                this.text= "The park is now open.";
                break;
            case 2:
                this.text= "We are working on fixing the problem.";
                break;
            case 3:
                this.text= "The park will soon be maintained.";
                break;
            case 4:
                this.text= "We all thank you for your help.";
                break;
            default:
                this.text= "Thanks for notifying us.";
                break;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
