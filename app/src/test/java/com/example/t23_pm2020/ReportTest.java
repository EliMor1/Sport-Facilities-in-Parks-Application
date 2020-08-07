package com.example.t23_pm2020;

import org.junit.Test;

import static org.junit.Assert.*;

public class ReportTest {

    private Report expicted =new Report("uid", 1212332, 12);
    @Test
    public void getUid() {
        String actual = expicted.getUid();

        assertTrue(actual.equals(expicted.getUid()));
    }

    @Test
    public void getTime() {
        long actual = expicted.getTime();

        assertTrue(actual == expicted.getTime());
    }

    @Test
    public void getType() {
        int actual = expicted.getType();

        assertTrue(actual == expicted.getType());
    }
}