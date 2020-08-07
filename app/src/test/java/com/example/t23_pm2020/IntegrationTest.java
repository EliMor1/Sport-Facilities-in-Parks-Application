package com.example.t23_pm2020;

import androidx.fragment.app.Fragment;

import com.example.t23_pm2020.ui.main.AdminPagerAdapter;
import com.example.t23_pm2020.ui.main.GuestPagerAdapter;
import com.example.t23_pm2020.ui.main.LoggedPagerAdapter;

import junit.framework.TestCase;

import org.junit.Test;

public class IntegrationTest extends TestCase {
    //Alerts aggregation tests
    @Test
    public void testAlertsWithRecycleView_MinimumThresh_Integration() {
        int minThresh = 0;
        Alerts alerts = Alerts.newInstance();
        for(int i =0; i < 5; i++){
            alerts.addNewReport(minThresh, i, "noisy" + i, i, "street" + i , "neighborhood" + i, "location" + i, (double) i, (double) i, "type" + i);
        }
        locationRecyclerViewAdapter adapter = new locationRecyclerViewAdapter(alerts.getContext(), alerts.alertsList);
        assertEquals(5, adapter.getItemCount());
    }
    @Test
    public void testAlertsWithRecycleView_MaximumThresh_Integration() {
        int maxThresh = 4;
        Alerts alerts = Alerts.newInstance();
        for(int i =0; i < 5; i++){
            alerts.addNewReport(maxThresh, i, "noisy" + i, i, "street" + i , "neighborhood" + i, "location" + i, (double) i, (double) i, "type" + i);
        }
        locationRecyclerViewAdapter adapter = new locationRecyclerViewAdapter(alerts.getContext(), alerts.alertsList);
        assertEquals(1, adapter.getItemCount());
    }
    @Test
    public void testAlertsWithRecycleView_OverMaximumThresh_Integration() {
        int maxThresh = 10;
        Alerts alerts = Alerts.newInstance();
        for(int i =0; i < 5; i++){
            alerts.addNewReport(maxThresh, i, "noisy" + i, i, "street" + i , "neighborhood" + i, "location" + i, (double) i, (double) i, "type" + i);
        }
        locationRecyclerViewAdapter adapter = new locationRecyclerViewAdapter(alerts.getContext(), alerts.alertsList);
        assertEquals(0, adapter.getItemCount());
    }
    //tab transition integration tests
    @Test
    public void testLoggedSearchTabTransition_Integration() {
        loggedHome logged = new loggedHome();
        LoggedPagerAdapter loggedPager = new LoggedPagerAdapter(logged, logged.getSupportFragmentManager());
        Fragment returnedFragment = loggedPager.getItem(0);
        assertEquals(searchLocations.class, returnedFragment.getClass());
    }
    @Test
    public void testLoggedAllTabTransition_Integration() {
        loggedHome logged = new loggedHome();
        LoggedPagerAdapter loggedPager = new LoggedPagerAdapter(logged, logged.getSupportFragmentManager());
        Fragment returnedFragment = loggedPager.getItem(1);
        assertEquals(allLocations.class, returnedFragment.getClass());
    }
    @Test
    public void testLoggedFavoritesTabTransition_Integration() {
        loggedHome logged = new loggedHome();
        LoggedPagerAdapter loggedPager = new LoggedPagerAdapter(logged, logged.getSupportFragmentManager());
        Fragment returnedFragment = loggedPager.getItem(2);
        assertEquals(Favorites.class, returnedFragment.getClass());
    }
    @Test
    public void testLoggedOptionsTabTransition_Integration() {
        loggedHome logged = new loggedHome();
        LoggedPagerAdapter loggedPager = new LoggedPagerAdapter(logged, logged.getSupportFragmentManager());
        Fragment returnedFragment = loggedPager.getItem(3);
        assertEquals(Options.class, returnedFragment.getClass());
    }
    @Test
    public void testAdminSearchTabTransition_Integration() {
        mpd_home mpd = new mpd_home();
        AdminPagerAdapter adminPager = new AdminPagerAdapter(mpd, mpd.getSupportFragmentManager());
        Fragment returnedFragment = adminPager.getItem(0);
        assertEquals(searchLocations.class, returnedFragment.getClass());
    }
    @Test
    public void testAdminAllTabTransition_Integration() {
        mpd_home mpd = new mpd_home();
        AdminPagerAdapter adminPager = new AdminPagerAdapter(mpd, mpd.getSupportFragmentManager());
        Fragment returnedFragment = adminPager.getItem(1);
        assertEquals(allLocations.class, returnedFragment.getClass());
    }
    @Test
    public void testAdminFavoritesTabTransition_Integration() {
        mpd_home mpd = new mpd_home();
        AdminPagerAdapter adminPager = new AdminPagerAdapter(mpd, mpd.getSupportFragmentManager());
        Fragment returnedFragment = adminPager.getItem(2);
        assertEquals(Favorites.class, returnedFragment.getClass());
    }
    @Test
    public void testAdminAlertsTabTransition_Integration() {
        mpd_home mpd = new mpd_home();
        AdminPagerAdapter adminPager = new AdminPagerAdapter(mpd, mpd.getSupportFragmentManager());
        Fragment returnedFragment = adminPager.getItem(3);
        assertEquals(Alerts.class, returnedFragment.getClass());
    }
    @Test
    public void testAdminOptionsTabTransition_Integration() {
        mpd_home mpd = new mpd_home();
        AdminPagerAdapter adminPager = new AdminPagerAdapter(mpd, mpd.getSupportFragmentManager());
        Fragment returnedFragment = adminPager.getItem(4);
        assertEquals(Options.class, returnedFragment.getClass());
    }
    @Test
    public void testGuestSearchTabTransition_Integration() {
        GuestHome guest = new GuestHome();
        GuestPagerAdapter guestPager = new GuestPagerAdapter(guest, guest.getSupportFragmentManager());
        Fragment returnedFragment = guestPager.getItem(0);
        assertEquals(searchLocations.class, returnedFragment.getClass());
    }
    @Test
    public void testGuestAllTabTransition_Integration() {
        GuestHome guest = new GuestHome();
        GuestPagerAdapter guestPager = new GuestPagerAdapter(guest, guest.getSupportFragmentManager());
        Fragment returnedFragment = guestPager.getItem(1);
        assertEquals(allLocations.class, returnedFragment.getClass());
    }
}