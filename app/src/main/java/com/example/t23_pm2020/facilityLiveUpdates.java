package com.example.t23_pm2020;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class facilityLiveUpdates extends AppCompatActivity {

    public static final  String[] REPORT_TYPE=Report.REPORT_TYPE;// {"dirty","closed","noisy"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facility_live_updates);
        Button[] valid_btns={   findViewById(R.id.validate_dirty_btn),
                                findViewById(R.id.validate_closed_btn),
                                findViewById(R.id.validate_noisy_btn),
                                findViewById(R.id.validate_maintenance_btn),
                                findViewById(R.id.validate_crowded_btn),
                findViewById(R.id.validate_vacantParking_btn)
        };
        for (Button B:valid_btns) {
            B.setVisibility(View.INVISIBLE);
        }


        final View rootView = findViewById(R.id.root_updates_layout);
        final TextView facilityName = findViewById(R.id.facility_name2);
        final ArrayList<Report> reportsList = new ArrayList<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        Bundle extras = getIntent().getExtras();
        final String lid;
        final boolean IsManager;
        final boolean IsLogged;
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        ImageButton back_btn = findViewById(R.id.back_btn2);
        Button goto_description = findViewById(R.id.goto_description);
        if(extras != null) {
            IsManager = extras.getBoolean("IsManager");
            IsLogged = extras.getBoolean("IsLogged");
            lid = extras.getString("lid");
            goto_description.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(facilityLiveUpdates.this, facilityDescription.class);
                    intent.putExtra("lid",lid);
                    intent.putExtra("IsManager", IsManager);
                    intent.putExtra("IsLogged", IsLogged);
                    startActivity(intent);
                }
            });
            back_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(IsManager) {
                        Intent intent = new Intent(facilityLiveUpdates.this, mpd_home.class);
                        startActivity(intent);
                    } else if (IsLogged){
                        Intent intent = new Intent(facilityLiveUpdates.this, loggedHome.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(facilityLiveUpdates.this, GuestHome.class);
                        startActivity(intent);
                    }
                }
            });
            final DatabaseReference myRef = database.getReference("locations").child(lid);
            final DatabaseReference FevRef = database.getReference("locations");
           final Button Dirty_Wrong = findViewById(R.id.dirty_wrong);
           final Button Dirty_Helpful = findViewById(R.id.dirty_helpful);
           final Button Dirty = findViewById(R.id.dirty);
           final Button Closed = findViewById(R.id.closed);
           final Button Closed_Wrong = findViewById(R.id.closed_wrong);
           final Button Closed_Helpful = findViewById(R.id.closed_helpful);
           final Button Noisy = findViewById(R.id.noisy);
           final Button Noisy_Wrong = findViewById(R.id.noisy_wrong);
           final Button Noisy_Helpful = findViewById(R.id.noisy_helpful);
           final Button Crowded = findViewById(R.id.crowded);
            final Button VacantParking_Wrong = findViewById(R.id.vacantParking_wrong);
            final Button VacantParking_Helpful = findViewById(R.id.vacantParking_helpful);
            final Button VacantParking = findViewById(R.id.vacantParking);
            final Button Crowded_Wrong = findViewById(R.id.crowded_wrong);
            final Button Crowded_Helpful = findViewById(R.id.crowded_helpful);
           final ImageButton Favorites = findViewById(R.id.add_favorites);
            Favorites.setVisibility(View.GONE);
           final Button[] maintenance={findViewById(R.id.maintenance_wrong_btn),findViewById(R.id.maintenance_btn),findViewById(R.id.maintenance_helpful_btn)};
            if(IsManager || IsLogged) {
                Favorites.setVisibility(View.VISIBLE);
                // vacantParking
                VacantParking_Wrong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final DatabaseReference vacantParking_wrong_ref = database.getReference("locations").child(lid).child("LiveReports").child("vacantParking").getRef();
                        Report vacantParking_wrong_report = new Report(mAuth.getCurrentUser().getUid(), (long) (new Date().getTime() / 1000), 0);
                        vacantParking_wrong_ref.child(mAuth.getCurrentUser().getUid()).setValue(vacantParking_wrong_report);

                    }
                });
                VacantParking_Helpful.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final DatabaseReference vacantParking_helpful_ref = database.getReference("locations").child(lid).child("LiveReports").child("vacantParking").getRef();
                        Report vacantParking_helpful_report = new Report(mAuth.getCurrentUser().getUid(), (long) (new Date().getTime() / 1000), 2);
                        vacantParking_helpful_ref.child(mAuth.getCurrentUser().getUid()).setValue(vacantParking_helpful_report);

                    }
                });
                VacantParking.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final DatabaseReference vacantParking_ref = database.getReference("locations").child(lid).child("LiveReports").child("vacantParking").getRef();
                        Report vacantParking_report = new Report(mAuth.getCurrentUser().getUid(), (long) (new Date().getTime() / 1000), 1);
                        vacantParking_ref.child(mAuth.getCurrentUser().getUid()).setValue(vacantParking_report);

                    }
                });


                // crowded
                Crowded_Wrong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final DatabaseReference crowded_wrong_ref = database.getReference("locations").child(lid).child("LiveReports").child("crowded").getRef();
                        Report crowded_wrong_report = new Report(mAuth.getCurrentUser().getUid(), (long) (new Date().getTime() / 1000), 0);
                        crowded_wrong_ref.child(mAuth.getCurrentUser().getUid()).setValue(crowded_wrong_report);

                    }
                });
                Crowded_Helpful.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final DatabaseReference crowded_helpful_ref = database.getReference("locations").child(lid).child("LiveReports").child("crowded").getRef();
                        Report crowded_helpful_report = new Report(mAuth.getCurrentUser().getUid(), (long) (new Date().getTime() / 1000), 2);
                        crowded_helpful_ref.child(mAuth.getCurrentUser().getUid()).setValue(crowded_helpful_report);

                    }
                });
                Crowded.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final DatabaseReference crowded_ref = database.getReference("locations").child(lid).child("LiveReports").child("crowded").getRef();
                        Report crowded_report = new Report(mAuth.getCurrentUser().getUid(), (long) (new Date().getTime() / 1000), 1);
                        crowded_ref.child(mAuth.getCurrentUser().getUid()).setValue(crowded_report);

                    }
                });

                // dirty
                Dirty_Wrong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final DatabaseReference dirty_wrong_ref = database.getReference("locations").child(lid).child("LiveReports").child("dirty").getRef();
                        Report dirty_wrong_report = new Report(mAuth.getCurrentUser().getUid(), (long) (new Date().getTime() / 1000), 0);
                        dirty_wrong_ref.child(mAuth.getCurrentUser().getUid()).setValue(dirty_wrong_report);

                    }
                });
                Dirty_Helpful.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final DatabaseReference dirty_helpful_ref = database.getReference("locations").child(lid).child("LiveReports").child("dirty").getRef();
                        Report dirty_helpful_report = new Report(mAuth.getCurrentUser().getUid(), (long) (new Date().getTime() / 1000), 2);
                        dirty_helpful_ref.child(mAuth.getCurrentUser().getUid()).setValue(dirty_helpful_report);

                    }
                });
                Dirty.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final DatabaseReference dirty_ref = database.getReference("locations").child(lid).child("LiveReports").child("dirty").getRef();
                        Report dirty_report = new Report(mAuth.getCurrentUser().getUid(), (long) (new Date().getTime() / 1000), 1);
                        dirty_ref.child(mAuth.getCurrentUser().getUid()).setValue(dirty_report);

                    }
                });


                // closed
                Closed_Wrong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final DatabaseReference closed_wrong_ref = database.getReference("locations").child(lid).child("LiveReports").child("closed").getRef();
                        Report closed_wrong_report = new Report(mAuth.getCurrentUser().getUid(), (long) (new Date().getTime() / 1000), 0);
                        closed_wrong_ref.child(mAuth.getCurrentUser().getUid()).setValue(closed_wrong_report);

                    }
                });
                Closed_Helpful.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final DatabaseReference closed_helpful_ref = database.getReference("locations").child(lid).child("LiveReports").child("closed").getRef();
                        Report closed_helpful_report = new Report(mAuth.getCurrentUser().getUid(), (long) (new Date().getTime() / 1000), 2);
                        closed_helpful_ref.child(mAuth.getCurrentUser().getUid()).setValue(closed_helpful_report);

                    }
                });
                Closed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final DatabaseReference closed_ref = database.getReference("locations").child(lid).child("LiveReports").child("closed").getRef();
                        Report closed_report = new Report(mAuth.getCurrentUser().getUid(), (long) (new Date().getTime() / 1000), 1);
                        closed_ref.child(mAuth.getCurrentUser().getUid()).setValue(closed_report);

                    }
                });


                // Noisy
                Noisy_Wrong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final DatabaseReference noisy_wrong_ref = database.getReference("locations").child(lid).child("LiveReports").child("noisy").getRef();
                        Report noisy_wrong_report = new Report(mAuth.getCurrentUser().getUid(), (long) (new Date().getTime() / 1000), 0);
                        noisy_wrong_ref.child(mAuth.getCurrentUser().getUid()).setValue(noisy_wrong_report);

                    }
                });
                Noisy_Helpful.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final DatabaseReference noisy_helpful_ref = database.getReference("locations").child(lid).child("LiveReports").child("noisy").getRef();
                        Report noisy_helpful_report = new Report(mAuth.getCurrentUser().getUid(), (long) (new Date().getTime() / 1000), 2);
                        noisy_helpful_ref.child(mAuth.getCurrentUser().getUid()).setValue(noisy_helpful_report);

                    }
                });
                Noisy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final DatabaseReference noisy_ref = database.getReference("locations").child(lid).child("LiveReports").child("noisy").getRef();
                        Report noisy_report = new Report(mAuth.getCurrentUser().getUid(), (long) (new Date().getTime() / 1000), 1);
                        noisy_ref.child(mAuth.getCurrentUser().getUid()).setValue(noisy_report);

                    }
                });
                // Favorites
                Favorites.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final DatabaseReference favorites_ref = database.getReference("locations").child(lid).child("Favorites").getRef();
                        favorites_ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                boolean isFav = false;
                                for (DataSnapshot DS : dataSnapshot.getChildren()) {
                                    System.out.println("the key is " + DS.getKey() + " equals    " + mAuth.getCurrentUser().getUid());
                                    if (DS.getKey().equals(mAuth.getCurrentUser().getUid())) {
                                        Favorites.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), android.R.drawable.btn_star_big_off));
                                        favorites_ref.child(mAuth.getCurrentUser().getUid()).removeValue();
                                        Toast.makeText(rootView.getContext(), " Removed from favorites ", Toast.LENGTH_LONG).show();
                                        isFav = true;
                                    }

                                }
                                if (!isFav) {
                                    Favorites.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), android.R.drawable.btn_star_big_on));
                                    favorites_ref.child(mAuth.getCurrentUser().getUid()).setValue("");
                                    Toast.makeText(rootView.getContext(), " Added to favorites ", Toast.LENGTH_LONG).show();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }


                        });
                    }

                });


                //Maintenance
                for (int i = 0; i < maintenance.length; i++) {
                    final int finalI = i;
                    maintenance[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final DatabaseReference temp_ref = database.getReference("locations").child(lid).child("LiveReports").child(REPORT_TYPE[3]).getRef();
                            Report temp_report = new Report(mAuth.getCurrentUser().getUid(), (long) (new Date().getTime() / 1000), finalI);
                            temp_ref.child(mAuth.getCurrentUser().getUid()).setValue(temp_report);
                        }
                    });
                }

                FevRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean isFav = false;
                        Favorites.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), android.R.drawable.btn_star_big_off));
                        for (DataSnapshot ds : dataSnapshot.getChildren())
                            if (ds.getKey().equals(lid))
                                for (DataSnapshot DS : ds.child("Favorites").getChildren()) {
                                    //System.out.println("the key is " + DS.getKey() + " equals    " + mAuth.getCurrentUser().getUid());
                                    if (DS.getKey().equals(mAuth.getCurrentUser().getUid())) {
                                        Favorites.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), android.R.drawable.btn_star_big_on));

                                        isFav = true;
                                    }
                                }
                        if (!isFav)
                            Favorites.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), android.R.drawable.btn_star_big_off));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    facilityName.setText(dataSnapshot.child("Name").getValue().toString());
                    int numOfPeople_vacantParking=0,numOfPeople_vacantParkingWrong=0,numOfPeople_vacantParkingHelpful=0;
                    int numOfPeople_dirty=0,numOfPeople_dirtyWrong=0,numOfPeople_dirtyHelpful=0;
                    int numOfPeople_Closed = 0,numOfPeople_ClosedWrong=0,numOfPeople_ClosedHelpful=0;
                    int numOfPeople_Noisy = 0,numOfPeople_NoisyWrong=0,numOfPeople_NoisyHelphul=0;
                    int numOfPeople_Maintenance = 0,numOfPeople_MaintenanceWrong=0,numOfPeople_MaintenanceHelpful=0;
                    int numOfPeople_Crowded = 0,numOfPeople_CrowdedWrong=0,numOfPeople_CrowdedHelpful=0;
                    // closed
                    for( DataSnapshot ds : dataSnapshot.child("LiveReports").child("closed").getChildren()){
                        if(Long.parseLong(ds.child("time").getValue().toString()) > (long)((new Date().getTime()/1000)-3600) ){
                            if(Integer.parseInt(ds.child("type").getValue().toString())==0)
                                numOfPeople_ClosedWrong++;
                            if(Integer.parseInt(ds.child("type").getValue().toString())==1)
                                numOfPeople_Closed ++;
                            if(Integer.parseInt(ds.child("type").getValue().toString())==2)
                                numOfPeople_ClosedHelpful++;
                    }}
                    // dirty
                    for( DataSnapshot ds : dataSnapshot.child("LiveReports").child("dirty").getChildren()){
                        if(Long.parseLong(ds.child("time").getValue().toString()) > (long)((new Date().getTime()/1000)-3600) ){
                            if(Integer.parseInt(ds.child("type").getValue().toString())==0)
                                numOfPeople_dirtyWrong++;
                            if(Integer.parseInt(ds.child("type").getValue().toString())==1)
                                numOfPeople_dirty++;
                            if(Integer.parseInt(ds.child("type").getValue().toString())==2)
                                numOfPeople_dirtyHelpful++;
                    }}
                    // noisy
                    for( DataSnapshot ds : dataSnapshot.child("LiveReports").child("noisy").getChildren()){
                        if(Long.parseLong(ds.child("time").getValue().toString()) > (long)((new Date().getTime()/1000)-3600) ){
                            if(Integer.parseInt(ds.child("type").getValue().toString())==0)
                                numOfPeople_NoisyWrong++;
                            if(Integer.parseInt(ds.child("type").getValue().toString())==1)
                                numOfPeople_Noisy++;
                            if(Integer.parseInt(ds.child("type").getValue().toString())==2)
                                numOfPeople_NoisyHelphul++;
                        }}
                    // crowded
                    for( DataSnapshot ds : dataSnapshot.child("LiveReports").child("crowded").getChildren()){
                        if(Long.parseLong(ds.child("time").getValue().toString()) > (long)((new Date().getTime()/1000)-3600) ){
                            if(Integer.parseInt(ds.child("type").getValue().toString())==0)
                                numOfPeople_CrowdedWrong++;
                            if(Integer.parseInt(ds.child("type").getValue().toString())==1)
                                numOfPeople_Crowded++;
                            if(Integer.parseInt(ds.child("type").getValue().toString())==2)
                                numOfPeople_CrowdedHelpful++;
                        }}
                    // vacantParking
                    for( DataSnapshot ds : dataSnapshot.child("LiveReports").child("vacantParking").getChildren()){
                        if(Long.parseLong(ds.child("time").getValue().toString()) > (long)((new Date().getTime()/1000)-3600) ){
                            if(Integer.parseInt(ds.child("type").getValue().toString())==0)
                                numOfPeople_vacantParkingWrong++;
                            if(Integer.parseInt(ds.child("type").getValue().toString())==1)
                                numOfPeople_vacantParking++;
                            if(Integer.parseInt(ds.child("type").getValue().toString())==2)
                                numOfPeople_vacantParkingHelpful++;
                        }}
                    //Maintenance
                    for( DataSnapshot ds : dataSnapshot.child("LiveReports").child(REPORT_TYPE[3]).getChildren()){
                        if(Long.parseLong(ds.child("time").getValue().toString()) > (long)((new Date().getTime()/1000)-3600) ){
                            if(Integer.parseInt(ds.child("type").getValue().toString())==0)
                                numOfPeople_MaintenanceWrong++;
                            if(Integer.parseInt(ds.child("type").getValue().toString())==1)
                                numOfPeople_Maintenance++;
                            if(Integer.parseInt(ds.child("type").getValue().toString())==2)
                                numOfPeople_MaintenanceHelpful++;
                        }}

                    VacantParking_Wrong.setText(numOfPeople_vacantParkingWrong+"");
                    VacantParking_Helpful.setText(numOfPeople_vacantParkingHelpful+"") ;
                    VacantParking.setText("vacant\nParking\n"+numOfPeople_vacantParking) ;

                    Dirty_Wrong.setText(numOfPeople_dirtyWrong+"");
                    Dirty_Helpful.setText(numOfPeople_dirtyHelpful+"") ;
                    Dirty.setText("Dirty\n"+numOfPeople_dirty) ;

                    Closed.setText("Closed\n"+numOfPeople_Closed);
                    Closed_Wrong.setText(numOfPeople_ClosedWrong+"");
                    Closed_Helpful.setText(numOfPeople_ClosedHelpful+"");

                    Noisy.setText("Noisy\n"+numOfPeople_Noisy);
                    Noisy_Wrong.setText(numOfPeople_NoisyWrong+"");
                    Noisy_Helpful.setText(numOfPeople_NoisyHelphul+"");

                    Crowded.setText("Crowded\n"+numOfPeople_Crowded);
                    Crowded_Wrong.setText(numOfPeople_CrowdedWrong+"");
                    Crowded_Helpful.setText(numOfPeople_CrowdedHelpful+"");

                    maintenance[2].setText(numOfPeople_MaintenanceHelpful+"");
                    maintenance[1].setText("Maintenance\n"+numOfPeople_Maintenance);
                    maintenance[0].setText(numOfPeople_MaintenanceWrong+"");

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            //Button[] valid_btns={valed_dirty_btn,valed_closed_btn,valed_noisy_btn};
            if(IsManager){
                /**
                 * to add a new action(live update)
                 * 1. add the new validate button to this array : Button[] valid_btns
                 * 2. add the new liveUpdate to the lists in Report.class
                 * 3. add a button to XML : activity_facility_live_updates.xml
                 */
                for (int S=0;S < REPORT_TYPE.length;S++) {

                    valid_btns[S].setVisibility(View.VISIBLE);
                    final int finalS=S;
                    valid_btns[finalS].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final DatabaseReference notifyMsg=database.getReference("message").getRef();
                            final DatabaseReference dirty_ref = database.getReference("locations").child(lid).child("LiveReports").child(REPORT_TYPE[finalS]).getRef();
                            dirty_ref.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    ArrayList<String> uidReported=new ArrayList<String>();//the list of users reported this park (dirty or helpful)
                                    int PointsUpdate;
                                    for( final DataSnapshot ds : dataSnapshot.getChildren())
                                        if(ds.child("time").getValue(long.class) >
                                                (long)((new Date().getTime()/1000)-3600) &&  (PointsUpdate = ds.child("type").getValue(int.class) )!= 0 ) {// noticed
                                            notifyMsg.child(ds.getKey()).child(finalS + "").
                                                    setValue(new Notification_validation("Your vote was taken into consideration",
                                                            "Thanks for notifying us.the park is no longer " + REPORT_TYPE[finalS], finalS));
                                            ds.child("time").getRef().setValue(0);
                                            final DatabaseReference userRef = database.getReference("users").child(ds.getKey());
                                            final int finalPointsUpdate = PointsUpdate;
                                            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    if(dataSnapshot.child("points").getValue(int.class) != null )
                                                        dataSnapshot.child("points").getRef().setValue(dataSnapshot.child("points").getValue(int.class)+ finalPointsUpdate);
                                                    else
                                                        dataSnapshot.child("points").getRef().setValue( finalPointsUpdate);
                                                    userRef.removeEventListener(this);
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });

                                        }
                                    dirty_ref.removeEventListener(this);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    System.out.println("The read failed: " + databaseError.getCode());
                                }
                            });
                        }
                    });
                }}
        } else {
            Toast.makeText(this,"ERROR LOADING PAGE",Toast.LENGTH_LONG).show();
            finish();
        }

    }
}
