package com.example.t23_pm2020;

import junit.framework.TestCase;

public class OptionsTest extends TestCase {
    public void testNewInstance() {
        assertEquals(Options.class,Options.newInstance().getClass());
    }
    Options options = Options.newInstance();
    public void testValidatePassTwoShortNumerical() {
        assertEquals(-1, options.validatePass("12", "12") );
    }
    public void testValidatePassTwoShortAlphabetical() {
        assertEquals(-1, options.validatePass("cd", "cd") );
    }
    public void testValidatePassTwoShortMixed() {
        assertEquals(-1, options.validatePass("a1", "a1") );
    }
    public void testValidatePassLeftShort() {
        assertEquals(0, options.validatePass("12", "1234") );
    }
    public void testValidatePassRightShort() {
        assertEquals(0, options.validatePass("1234", "12") );
    }
    public void testValidatePassTwoDifferentSameLen() {
        assertEquals(0, options.validatePass("abcd", "1234") );
    }
    public void testValidatePassTwoDifferentNotSameLen() {
        assertEquals(0, options.validatePass("abcd", "1234xyz") );
    }
    public void testValidatePassTwoSameAlphabeticalMinLen() {
        assertEquals(1, options.validatePass("abcd", "abcd") );
    }
    public void testValidatePassTwoSameNumericalMinLen() {
        assertEquals(1, options.validatePass("1234", "1234") );
    }
    public void testValidatePassTwoSameMixedMinLen() {
        assertEquals(1, options.validatePass("ab34", "ab34") );
    }
    public void testValidatePassTwoSameMixedGreaterThanMinLen() {
        assertEquals(1, options.validatePass("abcd1234", "abcd1234") );
    }


}