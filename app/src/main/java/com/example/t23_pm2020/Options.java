package com.example.t23_pm2020;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Options extends Fragment {

    private static final String TAG = "Option.Class";
    private OptionsViewModel mViewModel;
    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    ProgressBar progressBar;
    private boolean IsLogged;
    private boolean IsManager;

    public static Options newInstance() {
        return new Options();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        Bundle extras = getActivity().getIntent().getExtras();
        final View rootView = inflater.inflate(R.layout.options_fragment, container, false);
        progressBar = rootView.findViewById(R.id.progressBar);
        final Context context = this.getContext();
        final EditText newPass = (EditText) rootView.findViewById(R.id.new_pass_id);
        final EditText confPass = (EditText) rootView.findViewById(R.id.conf_pass_id);
        progressBar.setVisibility(View.GONE);
        Button changePass = (Button) rootView.findViewById(R.id.change_pass_btn);
        if (extras != null) {
            IsManager = extras.getBoolean("IsManager");
            IsLogged = extras.getBoolean("IsLogged");
        }
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassStr = newPass.getText().toString();
                String confPassStr = confPass.getText().toString();
                int isValid = validatePass(newPassStr, confPassStr);
                if (isValid == 1) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    user.updatePassword(newPassStr)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(context, "Password changed successfully!", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(context, "ERROR! Password wasn't changed!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                } else if (isValid == -1) {
                    Toast.makeText(context, "Passwords are too short!", Toast.LENGTH_LONG).show();
                } else Toast.makeText(context, "Passwords don't match!", Toast.LENGTH_LONG).show();
            }
        });
        Button signOutBtn = (Button) rootView.findViewById(R.id.sign_out_btn);
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getActivity().getIntent().removeExtra("user_id");
                mAuth.signOut();
                Intent intent = new Intent(getActivity().getApplicationContext(),
                        MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

        Button DeleteAccBtn = (Button) rootView.findViewById(R.id.delete_btn);
        final EditText emailTxt=((EditText) rootView.findViewById(R.id.delete_email_txt));
        final EditText passTxt=((EditText) rootView.findViewById(R.id.delete_pass_txt));
        if (IsManager==false){// if this user is not the manger
            deleteDialog(DeleteAccBtn,emailTxt,passTxt);
        }
        else{// if this user is the manger hide the delete from his page
            DeleteAccBtn.setVisibility(View.GONE);
            emailTxt.setVisibility(View.GONE);
            passTxt.setVisibility(View.GONE);
            ((TextView) rootView.findViewById(R.id.delete_pass_txt)).setVisibility(View.GONE);
        }




        return rootView;
    }

    private void deleteDialog(Button deleteAccBtn, final EditText emailTxt, final EditText passTxt) {
        deleteAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder dial = new AlertDialog.Builder(getActivity());
                dial.setTitle("Are You Sure?");
                dial.setMessage("Deleting this account will result in completely removing your "+
                        "account from the system and you won't be able to access the app.");
                dial.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteValidator(emailTxt,passTxt);
                    }
                });
                dial.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = dial.create();
                alertDialog.show();

            }
        });
    }

    private void deleteValidator(final EditText emailTxt, final EditText passTxt) {
        if(mAuth.getCurrentUser().getEmail().equals(emailTxt.getText().toString()))
            if (emailTxt.getText().toString().isEmpty()==false &&
                    passTxt.getText().toString().isEmpty()==false) {
                progressBar.setVisibility(View.VISIBLE);
                final FirebaseUser user = mAuth.getCurrentUser();
                // Get auth credentials from the user for re-authentication. The example below shows email and password credentials.
                AuthCredential credential = EmailAuthProvider.getCredential(emailTxt.getText().toString(), passTxt.getText().toString());
                // Prompt the user to re-provide their sign-in credentials
                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {//if the user was verified
                                    Log.d(TAG+" auth ", "User re-authenticated.");
                                    deleteAccount();
                                }
                                else//if the user couldn't be verified
                                    toastUser("Error:"+task.getException().getMessage());
                            }
                        });
            }else toastUser(  "Wrong Password");//the password is empty || not correct
        else toastUser( "Write your email and password correctly");//the email does not match the current user
    }

    private void deleteAccount() {
        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {//delete
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    toastUser("Deleted");
                    Intent intent = new Intent(getActivity().getApplicationContext(),MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);//redirect to the log in page
                } else //if the email couldn't deleted print the exception
                    toastUser("Error:"+task.getException().getMessage());
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(OptionsViewModel.class);
        // TODO: Use the ViewModel
    }

    int validatePass(String pass1, String pass2){
        if(pass1.equals(pass2)) {
            if(pass1.length() >= 4 && pass2.length() >= 4)
                return 1;
            else
                return -1;
        }
        else return 0;
    }
    private void toastUser(String str){//print a message
        Toast.makeText(getActivity().getApplicationContext(), str, Toast.LENGTH_LONG).show();
    }
}
