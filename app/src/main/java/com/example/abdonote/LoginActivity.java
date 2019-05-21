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
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private TextView tv_task_app;
    private EditText ed_user_name_login, ed_password_login;
    private Button btn_Login;
    private FirebaseAuth mAuth;
    private ScrollView scrlLogin;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            Log.i("1234", mAuth.getCurrentUser().toString());
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
        }
        tv_task_app = findViewById(R.id.tv_task_app);
        frameLayout = findViewById(R.id.progress_view);
        scrlLogin = findViewById(R.id.scrlLogin);

        ed_user_name_login = findViewById(R.id.ed_user_name_login);
        ed_password_login = findViewById(R.id.ed_password_login);
        btn_Login = findViewById(R.id.btn_Login);
        ed_user_name_login.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                scrlLogin.scrollTo(0, scrlLogin.getBottom());
                if (hasFocus) {
                    scrlLogin.scrollTo(0, scrlLogin.getBottom());
                }
            }
        });

        ed_password_login.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                scrlLogin.scrollTo(0, scrlLogin.getBottom());
                if (hasFocus) {
                    scrlLogin.scrollTo(0, scrlLogin.getBottom());
                }
            }
        });

    }

    public void btnLogin(View view) {
        if (isOnline()) {
            String strPasswordLogin = ed_password_login.getText().toString().trim();
            String strEmailLogin = ed_user_name_login.getText().toString().trim();
            if (TextUtils.isEmpty(strEmailLogin)) {
                ed_user_name_login.setError("Required Email..");
            }
            if (TextUtils.isEmpty(strPasswordLogin)) {
                ed_password_login.setError("Required Password..");
            } else {
                btn_Login.setText("");
                frameLayout.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(strEmailLogin, strPasswordLogin).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            finish();
                            Toast.makeText(getApplicationContext(), "تم تسجيلك بنجاح ^_^ ", Toast.LENGTH_SHORT).show();
//                            FirebaseUser user = task.getResult().getUser();
//
//                            UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
//                                    .setDisplayName("AbdOo Saed")
//                                    .setPhotoUri(Uri.parse("https://cdn130.picsart.com/245065642022202.jpg"))
//                                    .build();
//                            user.updateProfile(userProfileChangeRequest);

                        } else {

//                            // if email already registerd
//                            if (task.getException().getMessage().equals("The email address is already in use by another account.")) {
//                                Toast.makeText(getApplicationContext(), "هذا الايميل مستخدم من قبل", Toast.LENGTH_SHORT).show();
//
//                            }

                            Toast.makeText(getApplicationContext(), "فشل التسجيل", Toast.LENGTH_SHORT).show();

                        }
                        frameLayout.setVisibility(View.GONE);
                        btn_Login.setText("Login");

                    }
                });
                view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        } else
            Toast.makeText(getApplicationContext(), "No Internet Conction", Toast.LENGTH_SHORT).show();
    }

    public void tvRegister(View view) {
        finish();
        startActivity(new Intent(getApplicationContext(), registrationActivity.class));
    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface arg0, int arg1) {
                        LoginActivity.super.onBackPressed();
                    }
                }).create().show();
    }

    public void tvForgetPassword(View view) {

        Intent intent=new Intent(getApplicationContext(), forgotPassowrdActivity.class);
        intent.putExtra("ForgetPassword",ed_user_name_login.getText().toString());
        finish();
        startActivity(intent);
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
