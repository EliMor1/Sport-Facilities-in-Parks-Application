package com.example.t23_pm2020;

import junit.framework.TestCase;

import org.junit.Test;

public class RatingTest extends TestCase {
    Rating rating = new Rating("123", 5);
    @Test
    public void testGetUid() {
        assertEquals(true, rating.getUid().equals("123"));
    }

    @Test
    public void testGetScore() {
        assertEquals(5, rating.getScore());
    }
    @Test
    public void testSetScore_ScoreOverflow() {
        assertEquals(false, rating.setScore(50));
    }

    @Test
    public void testSetScore_NegativeScore() {
        assertEquals(false, rating.setScore(-10));
    }
    @Test
    public void testSetScore_NormalScore() {
        assertEquals(true, rating.setScore(3));
    }


}