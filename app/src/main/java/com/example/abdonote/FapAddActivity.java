/*
 * Created by AbdOo Saed from Egypt
 * all Copyright reserved
 */

package com.example.abdonote;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.abdonote.Model.ClassDate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FapAddActivity extends AppCompatActivity {
    private DatabaseReference RDatabase1;
    private FirebaseDatabase fDatabase;
    private EditText ed_Title1;
    private EditText ed_Note1;
    private Button btn_save1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fap_add);
        fDatabase = FirebaseDatabase.getInstance();
        RDatabase1 = fDatabase.getReference().child("Task Note").child(HomeActivity.strUId);
        ed_Title1 = findViewById(R.id.ed_Title1);
        ed_Note1 = findViewById(R.id.ed_Note1);
        btn_save1 = findViewById(R.id.btn_save1);

    }

    public void btn_save_fap_onclick(View view) {

        String str_Title = ed_Title1.getText().toString().trim();
        String str_Note = ed_Note1.getText().toString().trim();
        if (TextUtils.isEmpty(str_Title)) {
            ed_Title1.setError("Enter ur Title Plzz");
        } else if (TextUtils.isEmpty(str_Note)) {
            ed_Note1.setError("Enter ur Note Plzz");
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("M/dd hh:mm a",new Locale("EN"));
            String str_id = RDatabase1.push().getKey();
            String str_Time = formatter.format(new Date());


//                    ClassDate cDate = new ClassDate(str_id, str_Time, str_Title, str_Note);
            ClassDate cDate = new ClassDate(str_id, str_Time, str_Title, str_Note);

            RDatabase1.push().setValue(cDate).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                        finish();
                    } else
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            });
        }
        finish();
    }
}