package com.example.t23_pm2020;

import org.junit.Test;

import static org.junit.Assert.*;

public class Notification_validationTest {

    @Test
    public void getTitle() {
        String y;
        Notification_validation minNoty=new Notification_validation(0);

        for(int i=0;i<10000 && true==true;i++,y=minNoty.getTitle());

        String expected ="Thanks for your help";
        String actual = minNoty.getTitle();

        assertEquals(expected,actual);

        Notification_validation newNoty=new Notification_validation(1);

         expected ="Thanks for helping others know ";
         actual = newNoty.getTitle();

        assertEquals(expected,actual);

        Notification_validation maxNoty=new Notification_validation(4);

        expected ="Thanks for Voting";
        actual = maxNoty.getTitle();

        assertEquals(expected,actual);

        Notification_validation overFlowNoty=new Notification_validation(100);

        expected ="Your vote was taken into consideration";
        actual = overFlowNoty.getTitle();

        assertEquals(expected,actual);
    }

    @Test
    public void setTitle() {
        String y;
        Notification_validation minNoty=new Notification_validation("","",1);

        for(int i=0;i<10000 && true==true;i++,y=minNoty.getTitle());
        minNoty.setTitle(0);

        String expected ="Thanks for your help";
        String actual = minNoty.getTitle();

        assertEquals(expected,actual);

        Notification_validation newNoty=new Notification_validation("","",1);

        newNoty.setTitle(1);

        expected ="Thanks for helping others know ";
        actual = newNoty.getTitle();

        assertEquals(expected,actual);

        Notification_validation maxNoty=new Notification_validation("","",1);

        maxNoty.setTitle(4);

        expected ="Thanks for Voting";
        actual = maxNoty.getTitle();

        assertEquals(expected,actual);

        Notification_validation overFlowNoty=new Notification_validation("","",1);

        overFlowNoty.setTitle(300);

        expected ="Your vote was taken into consideration";
        actual = overFlowNoty.getTitle();

        assertEquals(expected,actual);
    }

    @Test
    public void getText() {
        String y;
        Notification_validation minNoty=new Notification_validation(0);

        for(int i=0;i<10000 && true==true;i++,y=minNoty.getText());

        String expected ="The park has been cleaned.";
        String actual = minNoty.getText();

        assertEquals(expected,actual);

        Notification_validation newNoty=new Notification_validation(1);

        expected ="The park is now open.";
        actual = newNoty.getText();

        assertEquals(expected,actual);

        Notification_validation maxNoty=new Notification_validation(4);

        expected ="We all thank you for your help.";
        actual = maxNoty.getText();

        assertEquals(expected,actual);

        Notification_validation overFlowNoty=new Notification_validation(100);

        expected ="Thanks for notifying us.";
        actual = overFlowNoty.getText();

        assertEquals(expected,actual);
    }

    @Test
    public void setText() {
        String y;
        Notification_validation minNoty=new Notification_validation("this is the test ","test 123",1);

        minNoty.setText(0);

        String expected ="The park has been cleaned.";
        String actual = minNoty.getText();

        assertEquals(expected,actual);

        Notification_validation newNoty=new Notification_validation("this is the test ","test 123",1);

        newNoty.setText(1);

        expected ="The park is now open.";
        actual = newNoty.getText();

        assertEquals(expected,actual);

        Notification_validation maxNoty=new Notification_validation("this test ","test 123",1);

        maxNoty.setText(4);

        expected ="We all thank you for your help.";
        actual = maxNoty.getText();

        assertEquals(expected,actual);

        Notification_validation overFlowNoty=new Notification_validation("final test ","test 123",1);

        overFlowNoty.setText(3000);

        expected ="Thanks for notifying us.";
        actual = overFlowNoty.getText();

        assertEquals(expected,actual);
    }
}