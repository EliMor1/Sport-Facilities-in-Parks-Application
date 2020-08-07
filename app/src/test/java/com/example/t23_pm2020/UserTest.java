package com.example.t23_pm2020;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    private User expected = new User("User", "abc@gmail.com", 1);
     @Test
    public void getName() {
         String actual = "asdasd";

         assertFalse(actual.equals(expected.getName()));
    }

    @Test
    public void getEmail() {
        String actual = "asdasd";
        String actual1 = "asda@sd.com";
        String actual2 = "abc@gmail.com";

        assertFalse(actual.equals(expected.getEmail()));
        assertFalse(actual1.equals(expected.getEmail()));
        assertTrue(actual2.equals(expected.getEmail()));
    }

    @Test
    public void getPoints() {
        int actual = 1232;
        int actual1 = 115611;
        int actual2 = 1;

        assertFalse(actual == expected.getPoints());
        assertFalse(actual1 == expected.getPoints());
        assertTrue(actual2 == expected.getPoints());
    }

    @Test
    public void isManager() {
        boolean actual = true;
        boolean actual1 = false;


        assertFalse(actual == expected.isManager() );
        assertTrue(actual1 == expected.isManager());
    }
}
/*
     @Test
     public void testOnCreate() {


         String nameResult = ""; //myObjectUnderTest.findViewById(R.id.signupnametxt).toString();
         String userResult = ""; //myObjectUnderTest.findViewById(R.id.signupEmailtxt).gettext;
         String passResult = ""; //myObjectUnderTest.findViewById(R.id.signuppasswordtxt).getText();


         // test that fields are empty
        assertTrue(nameResult.equals(emptyString));
        assertTrue(userResult.equals(emptyString));
        assertTrue(passResult.equals(emptyString));
     }
    @Test
    public void updateUI() {

        boolean expec = false;

        boolean actual= false ;
        //test correct message
        assertTrue(!(actual && expec));
    }*/