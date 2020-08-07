package com.example.t23_pm2020;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class searchLocations extends Fragment {

    private SearchLocationsViewModel mViewModel;
    private boolean IsManager=false;
    private  boolean IsLogged = false;
    public ArrayList<location> locationsList = new ArrayList<>();

    public static searchLocations newInstance() {
        return new searchLocations();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.search_locations_fragment, container, false);
        final Context context = this.getContext();

        Button search_button = (Button)rootView.findViewById(R.id.search_button);
        search_button.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(locationsList.size() > 0) {
                    locationsList.clear();
                    rootView.invalidate();
                }
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference("locations");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String search_term = ((EditText) rootView.findViewById(R.id.search_locations)).getText().toString();
                        for( DataSnapshot ds : dataSnapshot.getChildren()){
                            if((ds.child("street").getValue(String.class).contains(search_term) || ds.child("neighborho").getValue(String.class).contains(search_term) || ds.child("Name").getValue(String.class).contains(search_term)) && !search_term.equals("")) {
                                location newlocal = new location(Integer.parseInt(ds.getKey()), ds.child("street").getValue(String.class), ds.child("neighborho").getValue(String.class), ds.child("Name").getValue(String.class), Double.parseDouble(ds.child("lat").getValue(String.class)), Double.parseDouble(ds.child("lon").getValue(String.class)), ds.child("Type").getValue(String.class));
                                locationsList.add(newlocal);
                                System.out.println(ds.child("street").getValue(String.class));
                            }
                        }
                        System.out.println("got here");
                        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.search_results);
                        locationRecyclerViewAdapter adapter = new locationRecyclerViewAdapter(context, locationsList);
                        if(IsManager)
                            adapter.setManager(true);
                        if(IsLogged)
                            adapter.setLogged(true);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        myRef.removeEventListener(this);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        /*System.out.println("len of array is " + locationsList.size());
        locationRecyclerViewAdapter adapter = new locationRecyclerViewAdapter(this.getContext(), locationsList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));*/
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SearchLocationsViewModel.class);
        // TODO: Use the ViewModel
    }

    public void setManager(boolean manager) {
        IsManager = manager;
    }

    public void setLogged(boolean logged) {
        IsLogged = logged;
    }
}
