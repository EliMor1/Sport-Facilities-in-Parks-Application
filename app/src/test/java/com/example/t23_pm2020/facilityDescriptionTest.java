package com.example.t23_pm2020;

import junit.framework.TestCase;

public class facilityDescriptionTest extends TestCase {
    facilityDescription fd = new facilityDescription();
    public void testOneDecimalAVGWholeResult() {
        assertEquals(true, fd.oneDecimalAVG(9, 3).equals("3"));
    }
    public void testOneDecimalAVGDivideByZero() {
        assertEquals(true, fd.oneDecimalAVG(5, 0).equals("∞"));
    }
    public void testOneDecimalAVGZeroLen() {
        assertEquals(true, fd.oneDecimalAVG(0, 3).equals("0"));
    }
    public void testOneDecimalAVGDecimal() {
        //non-rounded answer is 2.6
        assertEquals(true, fd.oneDecimalAVG(13, 5).equals("2.6"));
    }
    public void testOneDecimalAVGUpperDecimal() {
        //non-rounded answer is 21.16666667
        assertEquals(true, fd.oneDecimalAVG(127, 6).equals("21.2"));
    }
    public void testOneDecimalAVGLowerDecimal() {
        //non-rounded answer is 14.1111111
        assertEquals(true, fd.oneDecimalAVG(127, 9).equals("14.1"));
    }
    public void testOneDecimalAVGMiddleDecimal() {
        //non-rounded answer is 31.25
        assertEquals(true, fd.oneDecimalAVG(125, 4).equals("31.2"));
    }
    public void testOneDecimalAVGNegativeSum() {
        assertEquals(true, fd.oneDecimalAVG(-9, 3).equals("-3"));
    }
    public void testOneDecimalAVGNegativeLen() {
        assertEquals(true, fd.oneDecimalAVG(9, -3).equals("-3"));
    }
    public void testOneDecimalAVGBothNegative() {
        assertEquals(true, fd.oneDecimalAVG(-9, -3).equals("3"));
    }
}