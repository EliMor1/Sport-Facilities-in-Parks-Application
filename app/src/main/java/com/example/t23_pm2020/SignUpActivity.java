package com.example.t23_pm2020;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
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

public class SignUpActivity extends AppCompatActivity {
    //FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        Button signUpBtn = (Button)findViewById(R.id.signupBtn);
        signUpBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                final EditText userName = (EditText) findViewById(R.id.signupnametxt);
                final EditText userPass = (EditText) findViewById(R.id.signuppasswordtxt);
                final EditText userEmail = (EditText) findViewById(R.id.signupEmailtxt);
                userPass.onEditorAction(EditorInfo.IME_ACTION_DONE);                //hide keyboard :D
                signUpFunc(userName.getText().toString(), userEmail.getText().toString(), userPass.getText().toString());
            }
        });

    }

    public boolean updateUI(FirebaseUser account){
        if(account != null){
            Toast.makeText(this,"Signed up successfully",Toast.LENGTH_LONG).show();
            return true;
        }else {
            Toast.makeText(this,"Sign Up Failed",Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public boolean signUpFunc(final String name, final String email, final String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            User user = new User(name, email, 0);
                            //myRef.child("users").child(mAuth.getCurrentUser().getUid()).setValue(user);
                            myRef.child(mAuth.getCurrentUser().getUid()).setValue(user);
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            updateUI(currentUser);
                            NextSignScreen(); // route to the next screen.
                        }else updateUI(null);
                    }
                });
      return  true;
    }
    public void NextSignScreen(){ // this method calling the home page screen activity after login/sign up successfully
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("users").child(mAuth.getCurrentUser().getUid()).getRef();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Intent intent = new Intent(SignUpActivity.this, loggedHome.class);
                intent.putExtra("user_name", dataSnapshot.child("name").getValue().toString());
                intent.putExtra("points", Integer.parseInt(dataSnapshot.child("points").getValue().toString()));
                System.out.println(dataSnapshot.child("name").getValue().toString());
                startActivity(intent);
                myRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}

