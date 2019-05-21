/*
 * Created by AbdOo Saed from Egypt
 * all Copyright reserved
 */

package com.example.abdonote;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.abdonote.Model.ClassDate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Edit_ItemRecylerV extends AppCompatActivity {
    private Button btn_delete_upd, btn_update_upd;
    private EditText edt_title_upd, edt_note_upd;
    private ArrayList<ClassDate> values1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__item_recyler_v);

        final String id = getIntent().getStringExtra("id");
        final String time = getIntent().getStringExtra("time");
        final String note = getIntent().getStringExtra("note");
        final String title = getIntent().getStringExtra("title");
        edt_title_upd = findViewById(R.id.edt_title_upd);
        btn_delete_upd = findViewById(R.id.btn_delete_upd);
        edt_note_upd = findViewById(R.id.edt_note_upd);
        btn_update_upd = findViewById(R.id.btn_update_upd);
        edt_note_upd.setText(note);
        edt_title_upd.setText(title);

        final FirebaseDatabase firebaseDatabasem = FirebaseDatabase.getInstance();
        final DatabaseReference tDatabaseReference = firebaseDatabasem.getReference();
        btn_update_upd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final ClassDate vEncap = new ClassDate();
                vEncap.setTitle(edt_title_upd.getText().toString());
                vEncap.setNote(edt_note_upd.getText().toString());
                vEncap.setTime(time);
                vEncap.setId(id);

                final Query query1 = tDatabaseReference.child("Task Note").child(HomeActivity.strUId).orderByChild("id").equalTo(id);

                query1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            snapshot.getRef().setValue(vEncap);

                        }
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        btn_delete_upd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Query query2 = tDatabaseReference.child("Task Note").child(HomeActivity.strUId).orderByChild("id").equalTo(id);
                query2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            snapshot.getRef().removeValue();
                            finish();
//                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


    }
}
