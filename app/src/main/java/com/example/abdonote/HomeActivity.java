/*
 * Created by AbdOo Saed from Egypt
 * all Copyright reserved
 */

package com.example.abdonote;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.abdonote.Model.ClassDate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;

public class HomeActivity extends AppCompatActivity {
    //    public static final int PICK_IMG_REQUEST = 6541;
    static String strUId;
    //    private StorageReference mainRef;
    //    new
    private ArrayList<ClassDate> list2 = new ArrayList<>();

    private RecyclerViewAdapter viewAdapter;
    private RecyclerView recyclerV;
    private Toolbar toolbarHome;
    private FloatingActionButton fab_add;
    private FirebaseAuth mAuth;
    private DatabaseReference RDatabase;
    private FirebaseDatabase fDatabase;
//    private FirebaseStorage firebaseStorage;


    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbarHome = findViewById(R.id.toolbarHome);
        recyclerV = findViewById(R.id.recyclerV);
        //hide fab when scrol
        recyclerV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    fab_add.hide();
                } else {
                    fab_add.show();
                }

                super.onScrolled(recyclerView, dx, dy);
            }
        });


        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            strUId = mAuth.getCurrentUser().getUid();
        } else {
            Toast.makeText(this, "There's no user ID", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
//        firebaseStorage = FirebaseStorage.getInstance();
//        mainRef = firebaseStorage.getReference();

//        RDatabase = FirebaseDatabase.getInstance().getReference().child("Task Note").child(strUId);

        // Read from the database
        fDatabase = FirebaseDatabase.getInstance();
        RDatabase = fDatabase.getReference().child("Task Note").child(strUId);
        RDatabase.keepSynced(true);

        fab_add = findViewById(R.id.fab_add);
        recyclerV.setHasFixedSize(true);
        setSupportActionBar(toolbarHome);
        getSupportActionBar().setTitle(mAuth.getCurrentUser().getDisplayName() + " Note");//getString(R.string.app_name)

//new
        viewAdapter = new RecyclerViewAdapter(HomeActivity.this, list2);
        recyclerV.setLayoutManager(new LinearLayoutManager(this));
        recyclerV.setAdapter(viewAdapter);


        RDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list2.clear();
                //    Encap encap3 = dataSnapshot.getValue(Encap.class);
                //   list.add(encap3);
                viewAdapter.notifyDataSetChanged();
                list2.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


                    ClassDate encap3 = snapshot.getValue(ClassDate.class);

                    list2.add(encap3);

                }

                Collections.reverse(list2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(HomeActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
//

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logOut) {
            mAuth.signOut();
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        } else if (id == R.id.About_Us) {
            AlertDialog.Builder builderr = new AlertDialog.Builder(HomeActivity.this);
            LayoutInflater inflater = LayoutInflater.from(HomeActivity.this);
            View view1 = inflater.inflate(R.layout.about_dev, null);
            builderr.setView(view1);
            final AlertDialog dialog = builderr.create();
            dialog.show();
        } else if (id == R.id.Exit) {
            finishAffinity();
        }

        return super.onOptionsItemSelected(item);
    }


    public void fab_add(View view) {
        startActivity(new Intent(getApplicationContext(), FapAddActivity.class));
    }


    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface arg0, int arg1) {
                        HomeActivity.super.onBackPressed();
                    }
                }).create().show();
    }

    public void EditUserName(MenuItem item) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
        alertDialog.setTitle("Enter Your Name");
        final EditText input = new EditText(HomeActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        alertDialog.setPositiveButton("Set Name",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(HomeActivity.this, ""+input.getText(), Toast.LENGTH_SHORT).show();
                        FirebaseUser user = mAuth.getCurrentUser();
                        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                                .setDisplayName(input.getText().toString())
                                .build();
                        user.updateProfile(userProfileChangeRequest);
//                        SharedPreferences.Editor editor = getSharedPreferences("save", Context.MODE_PRIVATE).edit();
//                        editor.putString("ed_user_display_name_rg", input.getText().toString());
//                        editor.apply();
                        getSupportActionBar().setTitle(input.getText().toString()+ " Note");
                        Toast.makeText(HomeActivity.this, "Old Is: " + mAuth.getCurrentUser().getDisplayName(), Toast.LENGTH_SHORT).show();
                    }
                });


        alertDialog.show();
    }

}
