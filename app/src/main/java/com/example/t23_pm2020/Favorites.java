package com.example.t23_pm2020;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Favorites extends Fragment {

    private FavoritesViewModel mViewModel;
    private boolean IsManager=false;
    private boolean IsLogged = false;
    public ArrayList<location> locationsList = new ArrayList<>();
    public static Favorites newInstance() {
        return new Favorites();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //return inflater.inflate(R.layout.favorites_fragment, container, false);

        View rootView = inflater.inflate(R.layout.favorites_fragment, container, false);
        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.all_favorites_list);
        final Context context = this.getContext();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final DatabaseReference myRef = database.getReference("locations");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                locationsList.clear();
                for( DataSnapshot ds : dataSnapshot.getChildren()){
                    for(DataSnapshot DS : ds.child("Favorites").getChildren())
                        if(DS.getKey().equals(mAuth.getCurrentUser().getUid())) {
                            location newlocal = new location(Integer.parseInt(
                                    ds.getKey()),
                                    ds.child("street").getValue(String.class),
                                    ds.child("neighborho").getValue(String.class),
                                    ds.child("Name").getValue(String.class),
                                    Double.parseDouble(ds.child("lat").getValue(String.class)),
                                    Double.parseDouble(ds.child("lon").getValue(String.class)),
                                    ds.child("Type").getValue(String.class)
                            );
                            locationsList.add(newlocal);
                        }
                    //System.out.println(ds.child("street").getValue(String.class));
                }
                //System.out.println("len of array is " + locationsList.size());
                locationRecyclerViewAdapter adapter = new locationRecyclerViewAdapter(context, locationsList);
                if(IsManager)
                    adapter.setManager(true);
                if(IsLogged)
                    adapter.setLogged(true);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);
        // TODO: Use the ViewModel
    }

    public void setManager(boolean manager) {
        IsManager = manager;
    }

    public void setLogged(boolean logged) {
        IsLogged = logged;
    }
}
