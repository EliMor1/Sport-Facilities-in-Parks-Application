package com.example.t23_pm2020;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class locationRecyclerViewAdapter extends RecyclerView.Adapter<locationRecyclerViewAdapter.ViewHolder> {
    public static final String TAG = "locationRecyclerViewAdapter";
    private ArrayList<location> locationsList;
    private boolean IsManager=false;
    private boolean IsLogged = false;
    private Context mContext;

    public locationRecyclerViewAdapter(Context context, ArrayList<location> locationsList) {
        mContext = context;
        this.locationsList = locationsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (locationsList != null) {
            holder.street.setText(locationsList.get(position).getStreet());
            if(locationsList.get(position).getReportType().equals("") && locationsList.get(position).getReportsNum().equals(""))
                holder.locationName.setText(locationsList.get(position).getName());
            else holder.locationName.setText(locationsList.get(position).getName() + " -- " + locationsList.get(position).getReportType() + ":" + locationsList.get(position).getReportsNum());
            holder.neighborhood.setText(locationsList.get(position).getNeighborhood());
        }

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, facilityDescription.class);
                intent.putExtra("lid", locationsList.get(position).getLid() + "");
                intent.putExtra("IsManager", IsManager);
                intent.putExtra("IsLogged", IsLogged);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return locationsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView street;
        TextView neighborhood;
        TextView locationName;
        RelativeLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            street = itemView.findViewById(R.id.street_name);
            neighborhood = itemView.findViewById(R.id.neighborhood_name);
            locationName = itemView.findViewById(R.id.location_name);
            parentLayout = itemView.findViewById(R.id.location_list_item);
        }
    }

    public void setManager(boolean manager) {
        IsManager = manager;
    }

    public void setLogged(boolean logged) {
        IsLogged = logged;
    }
}
