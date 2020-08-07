package com.example.t23_pm2020;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.t23_pm2020.ui.main.LoggedPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.t23_pm2020.Notification.NOTICE_CHANNEL_1_ID;

public class loggedHome extends AppCompatActivity {
    private NotificationManagerCompat notificationManager;//Notification test
    TextView pageTitle;
    TextView pointsAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_logged_home);
        LoggedPagerAdapter loggedPagerAdapter = new LoggedPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(loggedPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        pageTitle = findViewById(R.id.name_user);
        pointsAmount = findViewById(R.id.points_view);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("users").child(mAuth.getCurrentUser().getUid()).getRef();
        System.out.println("user loginHome");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                final DatabaseReference msgRef =database.getReference("message").getRef();
                msgRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String tempUid =mAuth.getCurrentUser().getUid();
                       // System.out.println("my is is: "+tempUid);
                        for (DataSnapshot ds :dataSnapshot.getChildren()) {
                            if(ds.getKey().equals(tempUid)){
                                for(DataSnapshot DS : ds.getChildren())
                                    for (String STR: Report.REPORT_TYPE_STR_NUMBER)
                                        if (DS.getKey().equals(STR) )
                                            notifyUser(
                                                new Notification_validation(
                                                        DS.child("title").getValue().toString(),
                                                        DS.child("text").getValue().toString(),
                                                        Integer.parseInt(DS.child("id").getValue().toString())));
                                ds.getRef().removeValue();
                                //System.out.println("The blog post titled  has been deleted");
                                break;

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                pageTitle.setText(dataSnapshot.child("name").getValue().toString());
                pointsAmount.setText("points: "+dataSnapshot.child("points").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
private void notifyUser(Notification_validation obj){
    //System.out.println("user notified function");
    notificationManager =NotificationManagerCompat.from(this);//Notification test
    android.app.Notification notification = new NotificationCompat.Builder(loggedHome.this,NOTICE_CHANNEL_1_ID) //Notification test
            .setSmallIcon(R.drawable.ic_done)
            .setContentTitle(obj.title)
            .setContentText(obj.text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .build();
    notificationManager.notify(obj.id,notification);

}
}