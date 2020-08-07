package com.example.t23_pm2020;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.t23_pm2020.ui.main.AdminPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class mpd_home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpd_home);
        AdminPagerAdapter adminPagerAdapter = new AdminPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adminPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);


/*
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("locations");
        for(int i = 0; i < 167; i++){
            Report firstReport = new Report("0", 0, 1);
           // myRef.child(i + "").child("Favorites").child("fakeUID").setValue("");
             myRef.child(i + "").child("LiveReports").child("vacantParking").child("FirstReport").setValue(firstReport);
            // myRef.child(i + "").child("LiveReports").child("closed").child("FirstReport").setValue(firstReport);
        }*/


/*
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("locations");
        for(int i = 0; i < 167; i++){
            Rating dummyRating = new Rating("FirstUser", 0);
            myRef.child(i + "").child("Ratings").child("Handicap Acessibility").child("FirstRating").setValue(dummyRating);
            //myRef.child(i + "").child("Ratings").child("Strength").child("FirstRating").setValue(dummyRating);
            //myRef.child(i + "").child("Ratings").child("Strength").child("FirstRating").setValue(dummyRating);
            //myRef.child(i + "").child("LiveReports").child("closed").child("FirstReport").setValue(dummyRating);
           // getReference("locations").child(lid).child("Ratings").child("Cardio").getRef()
        }*/

    }
}