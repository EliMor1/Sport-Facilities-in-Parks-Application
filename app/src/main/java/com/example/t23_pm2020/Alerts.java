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
import java.util.Date;

public class Alerts extends Fragment {

    private AlertsViewModel mViewModel;
    private boolean IsManager = true;
    private boolean IsLogged = true;
    public ArrayList<location> alertsList = new ArrayList<>();
    public static Alerts newInstance() {
        return new Alerts();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.alerts_fragment, container, false);
        Button updatethresh = (Button)rootView.findViewById(R.id.update_report_thresh);
        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.alerts_list);
        final Context context = this.getContext();
        final EditText threshEditor = (EditText)rootView.findViewById(R.id.min_report_thresh);
        updatethresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(alertsList.size() > 0) {
                    alertsList.clear();
                    rootView.invalidate();
                }
                final int thresh = getThreshFromInput(threshEditor.getText().toString());
                threshEditor.setText(thresh + "");
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference("locations");
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for( DataSnapshot ds : dataSnapshot.getChildren()){
                            int dirty = 0;
                            int closed = 0;
                            int noisy = 0;
                            int maintenance = 0;
                            int crowded = 0;
                            int vacantParking = 0;
                            int lid = Integer.parseInt(ds.getKey());
                            String street = ds.child("street").getValue(String.class);
                            String neighborhood = ds.child("neighborho").getValue(String.class);
                            String name = ds.child("Name").getValue(String.class);
                            double lat = Double.parseDouble(ds.child("lat").getValue(String.class));
                            double lon = Double.parseDouble(ds.child("lon").getValue(String.class));
                            String type = ds.child("Type").getValue(String.class);
                            //closed
                            for(DataSnapshot d : ds.child("LiveReports").child("closed").getChildren()){
                                if(Integer.parseInt(d.child("type").getValue().toString())==1 && Long.parseLong(d.child("time").getValue().toString()) > (long)((new Date().getTime()/1000)-3600)){
                                    closed++;
                                }
                            }
                            addNewReport(thresh, closed, "closed", lid, street, neighborhood, name, lat, lon, type);
                            //crowded
                            for(DataSnapshot d : ds.child("LiveReports").child("crowded").getChildren()){
                                if(Integer.parseInt(d.child("type").getValue().toString())==1 && Long.parseLong(d.child("time").getValue().toString()) > (long)((new Date().getTime()/1000)-3600)){
                                    crowded++;
                                }
                            }
                            addNewReport(thresh, crowded, "crowded", lid, street, neighborhood, name, lat, lon, type);
                            //dirty
                            for(DataSnapshot d : ds.child("LiveReports").child("dirty").getChildren()){
                                if(Integer.parseInt(d.child("type").getValue().toString())==1 && Long.parseLong(d.child("time").getValue().toString()) > (long)((new Date().getTime()/1000)-3600)){
                                    dirty++;
                                }
                            }
                            addNewReport(thresh, dirty, "dirty", lid, street, neighborhood, name, lat, lon, type);
                            //maintenance
                            for(DataSnapshot d : ds.child("LiveReports").child("maintenance").getChildren()){
                                if(Integer.parseInt(d.child("type").getValue().toString())==1 && Long.parseLong(d.child("time").getValue().toString()) > (long)((new Date().getTime()/1000)-3600)){
                                    maintenance++;
                                }
                            }
                            addNewReport(thresh, maintenance, "maintenance", lid, street, neighborhood, name, lat, lon, type);
                            //noisy
                            for(DataSnapshot d : ds.child("LiveReports").child("noisy").getChildren()){
                                if(Integer.parseInt(d.child("type").getValue().toString())==1 && Long.parseLong(d.child("time").getValue().toString()) > (long)((new Date().getTime()/1000)-3600)){
                                    noisy++;
                                }
                            }
                            addNewReport(thresh, noisy, "noisy", lid, street, neighborhood, name, lat, lon, type);
                            //vacant parking
                            for(DataSnapshot d : ds.child("LiveReports").child("vacantParking").getChildren()){
                                if(Integer.parseInt(d.child("type").getValue().toString())==1 && Long.parseLong(d.child("time").getValue().toString()) > (long)((new Date().getTime()/1000)-3600)){
                                    vacantParking++;
                                }
                            }
                            addNewReport(thresh, noisy, "vacantParking", lid, street, neighborhood, name, lat, lon, type);
                        }
                        locationRecyclerViewAdapter adapter = new locationRecyclerViewAdapter(context, alertsList);
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
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AlertsViewModel.class);
        // TODO: Use the ViewModel
    }

    public int getThreshFromInput(String input){
        if(input.equals(""))
            return 0;
        else {
            double returnVal = Double.parseDouble(input);
            if (returnVal < 0)
                returnVal = returnVal*(-1);
            return (int)returnVal;
        }
    }

    public boolean addNewReport(int thresh, int amount, String reportType, int lid, String street, String neighborhood, String name, double lat, double lon, String type){
        if (amount >= thresh) {
            location newLocal = new location(lid, street, neighborhood, name, lat, lon, type);
            newLocal.setReportType(reportType);
            newLocal.setReportsNum(amount + "");
            alertsList.add(newLocal);
            return true;
        } else return false;
    }

}
