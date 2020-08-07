package com.example.t23_pm2020;

import org.junit.Test;

import static org.junit.Assert.*;

public class locationTest {
    private location expected = new location(1, "str",  "neighborhood",  "name", 1.1, 1.2, "Type");

    @Test
    public void testStreet() {
        String actual = "str";

        assertTrue(actual.equals(expected.getStreet()));

    }

    @Test
    public void testNeighborhood() {
        String actual = "neighborhood";

        assertTrue(actual.equals(expected.getNeighborhood()));
    }

    @Test
    public void testName() {
        String actual = "name";

        assertTrue(actual.equals(expected.getName()));
    }

    @Test
    public void testLat() {
        double actual = 1.1;

        assertTrue(actual == expected.getLat());
    }

    @Test
    public void testLon() {
        double actual = 1.2;

        assertTrue(actual == expected.getLon());
    }
    @Test
    public void testType() {
        String actual = "Type";

        assertTrue(actual.equals(expected.getType()));
    }


}
