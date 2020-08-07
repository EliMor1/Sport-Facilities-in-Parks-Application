package com.example.t23_pm2020;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    //FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private static final String TAG = "MainActivity";
    private ProgressBar pbar;
     EditText userPass;
     EditText userEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pbar=findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
        Button logInBtn = (Button)findViewById(R.id.LogInBtn);
        logInBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                pbar.setVisibility(View.VISIBLE);
                 userPass = (EditText) findViewById(R.id.passwordtxt);
                 userEmail = (EditText) findViewById(R.id.emailtxt);
                logInFunc(userEmail.getText().toString(), userPass.getText().toString());

            }
        });

        Button CreateNewAccountBtn = (Button)findViewById(R.id.signupBtn);
        CreateNewAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        Button Guestbtn = (Button)findViewById(R.id.Guestbtn);
        Guestbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GuestHome.class);
                startActivity(intent);
            }
        });


    }

    public boolean logInFunc(String email , String password ){
        Log.d(TAG, "signIn");

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signIn:onComplete:" + task.isSuccessful());
                            if (task.isSuccessful()) {
                                onAuthSuccess(task.getResult().getUser());
                            } else {
                                pbar.setVisibility(View.GONE);
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Sign In Failed", Toast.LENGTH_SHORT).show();
                            // If sign in fails, display a message to the user.
                            updateUI(null);
                            pbar.setVisibility(View.GONE);
                        }

                    }
                });
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            return true;
        } else {
            updateUI(null);
            return false;
        }
    }

    public boolean updateUI(FirebaseUser account){
        if(account != null){
            Toast.makeText(this,"You Logged In Successfully",Toast.LENGTH_LONG).show();
            return true;
        } else
            return false;
    }
    private void onAuthSuccess(FirebaseUser user) {
        String username;
        //String isManager = myRef.child(mAuth.getCurrentUser().getUid()).child("isManager").toString();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("users").child(mAuth.getCurrentUser().getUid()).getRef();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("manager").getValue().toString().equals("true")){
                    Intent intent = new Intent(MainActivity.this, mpd_home.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, loggedHome.class);
                    System.out.println(dataSnapshot.child("name").getValue().toString());
                    startActivity(intent);
                }
                pbar.setVisibility(View.GONE);
                myRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        // Go to MainActivity
        //startActivity(new Intent(MainActivity.this, LogedHome.class));
        //finish();
    }
}




