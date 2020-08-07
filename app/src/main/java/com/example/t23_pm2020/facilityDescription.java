package com.example.t23_pm2020;

import android.content.Intent;
import android.net.Uri;
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

import java.text.DecimalFormat;

public class facilityDescription extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facility_description);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final View rootView = findViewById(R.id.root_description_layout);
        final TextView facilityName = findViewById(R.id.facility_name);
        final TextView descriptionContent = findViewById(R.id.description_content);

        final TextView num_of_ratings_cardio = findViewById(R.id.num_of_ratings_cardio);
        final TextView num_of_ratings_strength = findViewById(R.id.num_of_ratings_strength);
        final TextView num_of_ratings_childSuitability = findViewById(R.id.num_of_ratings_childSuitability);
        final TextView num_of_ratings_UpKeep = findViewById(R.id.num_of_ratings_UpKeep);
        final TextView num_of_ratings_Handicap_Accessibility = findViewById(R.id.num_of_ratings_Handicap_Accessibility);

        final TextView cardio_rating_txt = findViewById(R.id.Cardio_update_txt);
        final TextView strength_rating_txt = findViewById(R.id.strength_update_txt);
        final TextView childSuitability_rating_txt = findViewById(R.id.childSuitability_update_txt);
        final TextView UpKeep_rating_txt = findViewById(R.id.UpKeep_update_txt);
        final TextView Handicap_Accessibility_rating_txt = findViewById(R.id.Handicap_Accessibility_update_txt);

        final TextView cardio_Total_Score_txt= findViewById(R.id.Cardio_Total_Score_txt);
        final TextView strength_Total_Score_txt = findViewById(R.id.Strength_Total_Score_txt);
        final TextView childSuitability_Total_Score_txt = findViewById(R.id.childSuitability_Total_Score_txt);
        final TextView UpKeep_Total_Score_txt= findViewById(R.id.UpKeep_Total_Score_txt);
        final TextView Handicap_Accessibility_Total_Score_txt= findViewById(R.id.Handicap_Accessibility_Total_Score_txt);

        Bundle extras = getIntent().getExtras();
        final ImageButton Favorites = findViewById(R.id.add_favorites2);
        Favorites.setVisibility(View.GONE);
        final String lid;
        final boolean IsManager;
        final boolean IsLogged;
        ImageButton back_btn = findViewById(R.id.back_btn);
        ImageButton google_maps_btn = findViewById(R.id.google_maps_btn);


        Button goto_LiveUpdates = findViewById(R.id.goto_live_conditions);
        if(extras != null) {
            IsManager = extras.getBoolean("IsManager");
            IsLogged = extras.getBoolean("IsLogged");
            lid = extras.getString("lid");
           if(( IsManager) || (IsLogged )) {
               Favorites.setVisibility(View.VISIBLE);
               final DatabaseReference FevRef = database.getReference("locations");
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
                       FevRef.removeEventListener(this);
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });

           }
           ImageButton editdesc = findViewById(R.id.edit_description_btn);
            //lid = extras.getString("lid");
            goto_LiveUpdates.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(facilityDescription.this, facilityLiveUpdates.class);
                    intent.putExtra("lid",lid);
                    intent.putExtra("IsManager", IsManager);
                    intent.putExtra("IsLogged", IsLogged);
                    startActivity(intent);
                }
            });

            if(IsManager){
                editdesc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(facilityDescription.this, edit_description.class);
                        intent.putExtra("lid",lid);
                        startActivity(intent);
                    }
                });
            }
            else{
                editdesc.setVisibility(View.INVISIBLE);
            }
            back_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(IsManager) {
                        Intent intent = new Intent(facilityDescription.this, mpd_home.class);
                        startActivity(intent);
                    } else if (IsLogged){
                        Intent intent = new Intent(facilityDescription.this, loggedHome.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(facilityDescription.this, GuestHome.class);
                        startActivity(intent);
                    }
                }
            });
            google_maps_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final DatabaseReference google_maps_ref = database.getReference("locations").child(lid).getRef();

                    google_maps_ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            String lat, lon;
                            // getting the lat and lon for the loacation
                            lat = dataSnapshot.child("lat").getValue().toString();
                            lon = dataSnapshot.child("lon").getValue().toString();

                            String url = "https://www.google.com/maps/search/?api=1&query="+lat+","+lon;
                            google_maps_ref.removeEventListener(this);
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }


                    });

                }
            });
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
            /*
            Favorites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final DatabaseReference favorites_ref = database.getReference("locations").child(lid).child("Favorites").getRef();
                    favorites_ref.child(mAuth.getCurrentUser().getUid()).setValue("");
                    Toast.makeText(rootView.getContext(), " Added to favorites ", Toast.LENGTH_LONG).show();

                }
            });
            */
            if(IsManager||IsLogged) {// add the restriction for guests to update the rating
                Button cardio_Update_btn = findViewById(R.id.Cardio_update_btn);
                Button strength_update_btn = findViewById(R.id.strength_update_btn);
                Button childSuitability_update_btn = findViewById(R.id.childSuitability_update_btn);
                Button UpKeep_Update_btn = findViewById(R.id.UpKeep_update_btn);
                Button Handicap_Accessibility_Update_btn = findViewById(R.id.Handicap_Accessibility_update_btn);

                cardio_Update_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    // this method is been updated by Eli.
                    public void onClick(View v) {
                        Rating newRating = new Rating(mAuth.getCurrentUser().getUid());
                        if (!cardio_rating_txt.getText().toString().equals("")) { // checking validation if the cardio text rating string may be empty : if not continues rate updating process.
                            boolean Good_Integer = newRating.setScore(Integer.parseInt(cardio_rating_txt.getText().toString())); // changed the boolean cond puts into a var
                             //System.out.println(cardio_rating_txt.getText().toString()); // checking validation of cardio rating text
                            if (Good_Integer) {
                                Toast.makeText(rootView.getContext(), "Updating the Rating ", Toast.LENGTH_LONG).show();
                                final DatabaseReference Cardio_ref = database.getReference("locations").child(lid).child("Ratings").child("Cardio").getRef();
                                Cardio_ref.child(mAuth.getCurrentUser().getUid()).setValue(newRating);
                                cardio_rating_txt.setText("");
                            } else
                                Toast.makeText(rootView.getContext(), "Rating must be [0-5] ", Toast.LENGTH_LONG).show(); // pop up msg for inner condition
                        }
                        else{
                            Toast.makeText(rootView.getContext(), "Rating field cannot be empty ", Toast.LENGTH_LONG).show(); // pop up msg for extern condition
                        }
                    }
                });
                strength_update_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Rating newRating = new Rating(mAuth.getCurrentUser().getUid());
                        if (!strength_rating_txt.getText().toString().equals("")) { // checking if the cardio text rating string is empty : if not continues rating update process.
                            boolean Good_Integer = newRating.setScore(Integer.parseInt(strength_rating_txt.getText().toString())); // changed the boolean cond puts into a var
                            if (Good_Integer) {
                                Toast.makeText(rootView.getContext(), "Updating the Rating ", Toast.LENGTH_LONG).show();
                                final DatabaseReference Strength_ref = database.getReference("locations").child(lid).child("Ratings").child("Strength").getRef();
                                Strength_ref.child(mAuth.getCurrentUser().getUid()).setValue(newRating);
                                strength_rating_txt.setText("");
                            } else
                                Toast.makeText(rootView.getContext(), "Rating must be [0-5] ", Toast.LENGTH_LONG).show(); // pop up msg for inner condition
                        }
                        else{
                            Toast.makeText(rootView.getContext(), "Rating field cannot be empty ", Toast.LENGTH_LONG).show(); // pop up msg for extern condition
                        }
                    }
                });
                childSuitability_update_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Rating newRating = new Rating(mAuth.getCurrentUser().getUid());
                        if (!childSuitability_rating_txt.getText().toString().equals("")) { // checking if the cardio text rating string is empty : if not continues rating update process.
                            boolean Good_Integer = newRating.setScore(Integer.parseInt(childSuitability_rating_txt.getText().toString())); // changed the boolean cond puts into a var
                            if (Good_Integer) {
                                Toast.makeText(rootView.getContext(), "Updating the Rating ", Toast.LENGTH_LONG).show();
                                final DatabaseReference ChildSuitability_ref = database.getReference("locations").child(lid).child("Ratings").child("Child Suitability").getRef();
                                ChildSuitability_ref.child(mAuth.getCurrentUser().getUid()).setValue(newRating);
                                childSuitability_rating_txt.setText("");
                            } else
                                Toast.makeText(rootView.getContext(), "Rating must be [0-5] ", Toast.LENGTH_LONG).show(); // pop up msg for inner condition
                        }
                        else{
                            Toast.makeText(rootView.getContext(), "Rating field cannot be empty ", Toast.LENGTH_LONG).show(); // pop up msg for extern condition
                        }
                    }
                });
                UpKeep_Update_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {//add action on click listener
                        Rating newRating = new Rating(mAuth.getCurrentUser().getUid());
                        if (!UpKeep_rating_txt.getText().toString().equals("")) { // make sure text is not empty .
                            boolean Good_Integer = newRating.setScore(Integer.parseInt(UpKeep_rating_txt.getText().toString())); //if the text is integer start the process
                            if (Good_Integer) {
                                Toast.makeText(rootView.getContext(), "Updating the Rating ", Toast.LENGTH_LONG).show();
                                final DatabaseReference UpKeep_ref = database.getReference("locations").child(lid).child("Ratings").child("UpKeep").getRef();
                                UpKeep_ref.child(mAuth.getCurrentUser().getUid()).setValue(newRating);//add the score to the database using the rules
                                UpKeep_rating_txt.setText("");
                            } else
                                Toast.makeText(rootView.getContext(), "Rating must be [0-5] ", Toast.LENGTH_LONG).show(); // pop up msg for wrong value
                        }
                        else{
                            Toast.makeText(rootView.getContext(), "Rating field cannot be empty ", Toast.LENGTH_LONG).show(); // pop up msg for empty value
                        }
                    }
                });
                Handicap_Accessibility_Update_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {//add action on click listener
                        Rating newRating = new Rating(mAuth.getCurrentUser().getUid());
                        if (!Handicap_Accessibility_rating_txt.getText().toString().equals("")) { // make sure text is not empty .
                            boolean Good_Integer = newRating.setScore(Integer.parseInt(Handicap_Accessibility_rating_txt.getText().toString())); //if the text is integer start the process
                            if (Good_Integer) {
                                Toast.makeText(rootView.getContext(), "Updating the Rating ", Toast.LENGTH_LONG).show();
                                final DatabaseReference Handicap_Accessibility_ref = database.getReference("locations").child(lid).child("Ratings").child("Handicap Acessibility").getRef();
                                Handicap_Accessibility_ref.child(mAuth.getCurrentUser().getUid()).setValue(newRating);//add the score to the database using the rules
                                Handicap_Accessibility_rating_txt.setText("");
                            } else
                                Toast.makeText(rootView.getContext(), "Rating must be [0-5] ", Toast.LENGTH_LONG).show(); // pop up msg for wrong value
                        }
                        else{
                            Toast.makeText(rootView.getContext(), "Rating field cannot be empty ", Toast.LENGTH_LONG).show(); // pop up msg for empty value
                        }
                    }
                });
            }else // guests can only see the rating
            {

                cardio_rating_txt.setVisibility(View.INVISIBLE);
                Button cardio_Update_btn = findViewById(R.id.Cardio_update_btn);
                cardio_Update_btn.setVisibility(View.INVISIBLE);

                strength_rating_txt.setVisibility(View.INVISIBLE);
                Button strength_Update_btn = findViewById(R.id.strength_update_btn);
                strength_Update_btn.setVisibility(View.INVISIBLE);

                childSuitability_rating_txt.setVisibility(View.INVISIBLE);
                Button childSuitability_Update_btn = findViewById(R.id.childSuitability_update_btn);
                childSuitability_Update_btn.setVisibility(View.INVISIBLE);
                //UpKeep
                UpKeep_rating_txt.setVisibility(View.INVISIBLE);
                Button UpKeep_Update_btn = findViewById(R.id.UpKeep_update_btn);
                UpKeep_Update_btn.setVisibility(View.INVISIBLE);

                Handicap_Accessibility_rating_txt.setVisibility(View.INVISIBLE);
                Button Handicap_btn = findViewById(R.id.Handicap_Accessibility_update_btn);
                Handicap_btn.setVisibility(View.INVISIBLE);
            }

            final DatabaseReference myRef = database.getReference("locations").child(lid);
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    facilityName.setText(dataSnapshot.child("Name").getValue().toString());
                    descriptionContent.setText(dataSnapshot.child("description").getValue().toString());
                    int cardioSum = 0, cardioLen = 0, strengthSum = 0, strengthLen = 0, childSuitabilitySum = 0, childSuitabilityLen = 0;
                    int handicapSum = 0, handicapLen = 0;

                    //cardio
                    for (DataSnapshot ds : dataSnapshot.child("Ratings").child("Cardio").getChildren()) {
                        cardioLen++;
                        cardioSum += Integer.parseInt(ds.child("score").getValue().toString());

                    }
                    if (cardioLen != 1)
                        cardio_Total_Score_txt.setText(oneDecimalAVG(cardioSum, cardioLen - 1));
                    else cardio_Total_Score_txt.setText("--");
                    num_of_ratings_cardio.setText("(" + (cardioLen - 1) + ")"); //cardio rating amount view

                    //Handicap Accessibility
                    for (DataSnapshot ds : dataSnapshot.child("Ratings").child("Handicap Acessibility").getChildren()) {
                        handicapLen++;
                        handicapSum += Integer.parseInt(ds.child("score").getValue().toString());

                    }
                    if (handicapLen != 1)
                        Handicap_Accessibility_Total_Score_txt.setText(oneDecimalAVG(handicapSum, handicapLen - 1));
                    else Handicap_Accessibility_Total_Score_txt.setText("--");
                    num_of_ratings_Handicap_Accessibility.setText("(" + (handicapLen - 1) + ")");

                    //strength
                    for (DataSnapshot ds : dataSnapshot.child("Ratings").child("Strength").getChildren()) {
                        strengthLen++;
                        strengthSum += Integer.parseInt(ds.child("score").getValue().toString());

                    }
                    if (strengthLen != 1)
                        strength_Total_Score_txt.setText(oneDecimalAVG(strengthSum, strengthLen - 1));
                    else strength_Total_Score_txt.setText("--");
                    num_of_ratings_strength.setText("(" + (strengthLen - 1) + ")"); //strength rating amount view

                    //child Suitability
                    for (DataSnapshot ds : dataSnapshot.child("Ratings").child("Child Suitability").getChildren()) {
                        childSuitabilityLen++;
                        childSuitabilitySum += Integer.parseInt(ds.child("score").getValue().toString());

                    }
                    if (childSuitabilityLen != 1)
                        childSuitability_Total_Score_txt.setText(oneDecimalAVG(childSuitabilitySum, childSuitabilityLen - 1));
                    else childSuitability_Total_Score_txt.setText("--");
                    num_of_ratings_childSuitability.setText("(" + (childSuitabilityLen - 1) + ")"); //child suitability rating amount view

                    //UpKeep
                    int UpKeepSum = 0, UpKeepLen = 0;
                    //UpKeep
                    for (DataSnapshot ds : dataSnapshot.child("Ratings").child("UpKeep").getChildren()) {
                        UpKeepLen++;
                        UpKeepSum += Integer.parseInt(ds.child("score").getValue().toString());

                    }
                    if (UpKeepLen != 1) //if there are some ratings
                        UpKeep_Total_Score_txt.setText(oneDecimalAVG(UpKeepSum, UpKeepLen - 1));// set the number to the average rating
                    else UpKeep_Total_Score_txt.setText("--"); // else keep it empty
                    num_of_ratings_UpKeep.setText("(" + (UpKeepLen - 1) + ")"); 
                    //UpKeep
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else {
            Toast.makeText(this,"ERROR LOADING PAGE",Toast.LENGTH_LONG).show();
            finish();
        }
    }
    String oneDecimalAVG(int sum, int len){
        DecimalFormat df = new DecimalFormat("#.#");
        return df.format((float)sum/(float)len);
    }
}
