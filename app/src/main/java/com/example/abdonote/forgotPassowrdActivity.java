/*
 * Created by AbdOo Saed from Egypt
 * all Copyright reserved
 */

package com.example.abdonote;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

public class forgotPassowrdActivity extends AppCompatActivity {
    private EditText ed_user_name_forget;
    private FirebaseAuth mAuth;
    private Button btn_Update_Pass;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_passowrd);
        frameLayout = findViewById(R.id.progress_view);
        mAuth = FirebaseAuth.getInstance();
        ed_user_name_forget = findViewById(R.id.ed_user_name_forget);
        btn_Update_Pass = findViewById(R.id.btn_Update_Pass);
        Intent intent=getIntent();
        ed_user_name_forget.setText(intent.getStringExtra("ForgetPassword"));
    }

    public void btnForgePassword(View view) {
        if (isOnline()) {
            final String StrEmailForget = ed_user_name_forget.getText().toString();
            String strEmailLogin = ed_user_name_forget.getText().toString().trim();
            if (TextUtils.isEmpty(strEmailLogin)) {
                ed_user_name_forget.setError("Required Email..");
            } else {
                frameLayout.setVisibility(View.VISIBLE);
                btn_Update_Pass.setText("");
                mAuth.fetchSignInMethodsForEmail(StrEmailForget).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        try {
                            if (!task.getResult().getSignInMethods().isEmpty()) {
                                frameLayout.setVisibility(View.INVISIBLE);
                                btn_Update_Pass.setText("Update Password");
                                new AlertDialog.Builder(forgotPassowrdActivity.this)
                                        .setTitle("Really Send password Reset?")
                                        .setMessage("Are you sure you want to Reset Your Password?")
                                        .setNegativeButton(android.R.string.no, null)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface arg0, int arg1) {
                                                mAuth.sendPasswordResetEmail(StrEmailForget);
                                                Toast.makeText(getApplicationContext(), "Reset Your Password Sent to Your Email", Toast.LENGTH_LONG).show();
                                                finish();
                                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                            }
                                        }).create().show();
                            } else {
                                new AlertDialog.Builder(forgotPassowrdActivity.this)
                                        .setTitle("Not Registered ")
                                        .setMessage("Email address not Registered Do You Went To Register?")
                                        .setNegativeButton(android.R.string.no, null)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface arg0, int arg1) {
                                                finish();
                                                startActivity(new Intent(getApplicationContext(), registrationActivity.class));
                                            }
                                        }).create().show();
                            }
                        } catch (Exception ee) {
                            Toast.makeText(getApplicationContext(), ee.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } else
            Toast.makeText(getApplicationContext(), "No Internet Conction", Toast.LENGTH_SHORT).show();
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

}