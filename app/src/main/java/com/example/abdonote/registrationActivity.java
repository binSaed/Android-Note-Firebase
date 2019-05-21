/*
 * Created by AbdOo Saed from Egypt
 * all Copyright reserved
 */

package com.example.abdonote;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class registrationActivity extends AppCompatActivity {
    private ScrollView scrlRegister;
    private EditText ed_user_name_rg, ed_password_rg, ed_user_display_name_rg;
    private Button btn_rg;
    private FirebaseAuth fbAuth;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        frameLayout = findViewById(R.id.progress_view);
        scrlRegister = findViewById(R.id.scrlRegister);
        fbAuth = FirebaseAuth.getInstance();
        ed_user_name_rg = findViewById(R.id.ed_user_name_rg);
        ed_password_rg = findViewById(R.id.ed_password_rg);
        ed_user_display_name_rg = findViewById(R.id.ed_user_display_name_rg);
        btn_rg = findViewById(R.id.btn_rg);
        ed_user_name_rg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                scrlRegister.scrollTo(0, scrlRegister.getBottom());
                if (hasFocus) {
                    scrlRegister.scrollTo(0, scrlRegister.getBottom());
                }
            }
        });

        ed_password_rg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                scrlRegister.scrollTo(0, scrlRegister.getBottom());
                if (hasFocus) {
                    scrlRegister.scrollTo(0, scrlRegister.getBottom());
                }
            }
        });
    }

    public void btnRegister(View view) {
        String strEmailRg = ed_user_name_rg.getText().toString().trim();
        String strPasswordRg = ed_password_rg.getText().toString().trim();
        if (TextUtils.isEmpty(strEmailRg)) {
            ed_user_name_rg.setError("Required Email..");
        }
        if (TextUtils.isEmpty(strPasswordRg)) {
            ed_password_rg.setError("Required Password..");
        } else {
            btn_rg.setText("");
            frameLayout.setVisibility(View.VISIBLE);
            fbAuth.createUserWithEmailAndPassword(strEmailRg, strPasswordRg).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getBaseContext(), "Successful", Toast.LENGTH_SHORT).show();

                        FirebaseUser user = task.getResult().getUser();
                        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                                .setDisplayName(ed_user_display_name_rg.getText().toString())
                                .build();
                        user.updateProfile(userProfileChangeRequest);
//                        SharedPreferences.Editor editor = getSharedPreferences("save", Context.MODE_PRIVATE).edit();
//                        editor.putString("ed_user_display_name_rg", ed_user_display_name_rg.getText().toString());
//                        editor.apply();
                        finish();
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    } else {
                        try {
                            if (task.getException().getMessage().equals("The email address is already in use by another account.")) {
                                Toast.makeText(getApplicationContext(), "This email ID already used by someone else", Toast.LENGTH_SHORT).show();
                            }else if (task.getException().getMessage().equals("The email address is badly formatted.")){
                                Toast.makeText(registrationActivity.this, "Please enter valid mail", Toast.LENGTH_LONG).show();
                                ed_user_name_rg.setError("Required Email..");
                            }
                            else if (task.getException().getMessage().equals("The given password is invalid. [ Password should be at least 6 characters ]")){
                                Toast.makeText(registrationActivity.this, "Password should be at least 6 characters", Toast.LENGTH_LONG).show();
                                ed_password_rg.setError("Required Password..");
                            }
                            else {
                                Log.i("k",task.getException().getMessage());
                                Toast.makeText(registrationActivity.this, task.getException().getMessage()+"", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(registrationActivity.this, e.getMessage()+"", Toast.LENGTH_SHORT).show();
                        }
                        frameLayout.setVisibility(View.GONE);
                        btn_rg.setText(getString(R.string.register));

                    }


                }
            });

        }
    }

    public void tvLoginHere(View view) {
        finish();
        startActivity(new Intent(getBaseContext(), LoginActivity.class));
    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface arg0, int arg1) {
                        registrationActivity.super.onBackPressed();
                    }
                }).create().show();
    }
}
