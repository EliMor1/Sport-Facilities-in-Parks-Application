package com.example.t23_pm2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class edit_description extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_description);
        ImageButton edit_desc = findViewById(R.id.save_description_btn);
        final EditText desc_field = findViewById(R.id.edit_description_txt);
        Bundle extras = getIntent().getExtras();
        String lid = extras.getString("lid");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("locations").child(lid);
        Context context = this;
        edit_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.child("description").setValue(desc_field.getText().toString());
                toast();
                finish();

            }
        });
    }
    private void toast(){
        Toast.makeText(this,"Changes Saved",Toast.LENGTH_LONG).show();
    }
}
