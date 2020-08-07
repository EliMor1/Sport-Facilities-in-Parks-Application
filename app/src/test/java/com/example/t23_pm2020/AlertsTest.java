package com.example.t23_pm2020;

import junit.framework.TestCase;

import org.junit.Test;

public class AlertsTest extends TestCase {
    @Test
    public void testNewInstance() {
        assertEquals(Alerts.class, Alerts.newInstance().getClass() );
    }
    Alerts alerts = Alerts.newInstance();
    @Test
    public void testGetThreshFromInput_empty() {
        assertEquals(0, alerts.getThreshFromInput(""));
    }
    @Test
    public void testGetThreshFromInput_normal() {
        assertEquals(5, alerts.getThreshFromInput("5"));
    }
    @Test
    public void testGetThreshFromInput_negative() {
        assertEquals(4, alerts.getThreshFromInput("-4"));
    }
    @Test
    public void testGetThreshFromInput_small_double() {
        assertEquals(4, alerts.getThreshFromInput("4.2"));
    }
    @Test
    public void testGetThreshFromInput_big_double() {
        assertEquals(4, alerts.getThreshFromInput("4.9"));
    }
    @Test
    public void testGetThreshFromInput_middle_double() {
        assertEquals(4, alerts.getThreshFromInput("4.5"));
    }
    @Test
    public void testGetThreshFromInput_negative_small_double() {
        assertEquals(4, alerts.getThreshFromInput("-4.2"));
    }
    @Test
    public void testGetThreshFromInput_negative_big_double() {
        assertEquals(4, alerts.getThreshFromInput("-4.9"));
    }
    @Test
    public void testGetThreshFromInput_negative_middle_double() {
        assertEquals(4, alerts.getThreshFromInput("-4.5"));
    }
    @Test
    public void testGetThreshFromInput_critical_high_double() {
        assertEquals(1, alerts.getThreshFromInput("1.999999999"));
    }
    @Test
    public void testGetThreshFromInput_critical_low_double() {
        assertEquals(1, alerts.getThreshFromInput("1.000000001"));
    }
    @Test
    public void testAddNewReport_above_thresh() {
        assertEquals(true, alerts.addNewReport(2,5, "crowded", 123, "a", "b", "name", 3.5,4.6, "type"));
    }
    @Test
    public void testAddNewReport_below_thresh() {
        assertEquals(false, alerts.addNewReport(4,1, "crowded", 123, "a", "b", "name", 3.5,4.6, "type"));
    }
    @Test
    public void testAddNewReport_equal_thresh() {
        assertEquals(true, alerts.addNewReport(3,3, "crowded", 123, "a", "b", "name", 3.5,4.6, "type"));
    }

}