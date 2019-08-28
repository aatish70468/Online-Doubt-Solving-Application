package com.example.aatish.onlinedoubt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class civil extends AppCompatActivity {

    Button b1,b3,b4;
    String[] sem1 = {};
    FirebaseDatabase database;
    DatabaseReference mRef1;
    ArrayList<String> acs1 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_civil );
        setTitle("Civil");
        database = FirebaseDatabase.getInstance();

        b1 = (Button)findViewById( R.id.semester1 );
        b3 = (Button)findViewById( R.id.semester3 );
        b4 = (Button)findViewById( R.id.semester4 );

        b1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRef1 = database.getReference("Department/Civil/sem 1 & 2");
                mRef1.addListenerForSingleValueEvent( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String p1 = ds.child( "Subject" ).getValue().toString();
                            acs1.add( p1 );
                        }
                        sem1 = acs1.toArray( new String[acs1.size()] );
                        sub_put(sem1);
                        acs1.clear();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                } );
            }
        } );
        b3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRef1 = database.getReference("Department/Civil/sem 3");
                mRef1.addListenerForSingleValueEvent( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String p1 = ds.child( "Subject" ).getValue().toString();
                            acs1.add( p1 );
                        }
                        sem1 = acs1.toArray( new String[acs1.size()] );
                        sub_put(sem1);
                        acs1.clear();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                } );
            }
        } );
        b4.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRef1 = database.getReference("Department/Civil/sem 4");
                mRef1.addListenerForSingleValueEvent( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String p1 = ds.child( "Subject" ).getValue().toString();
                            acs1.add( p1 );
                        }
                        sem1 = acs1.toArray( new String[acs1.size()] );
                        sub_put(sem1);
                        acs1.clear();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                } );
            }
        } );
    }

    private void sub_put(String[] x1){
        Intent intent = new Intent( civil.this,subject_page.class );
        Bundle bundle1 = new Bundle(  );
        bundle1.putStringArray( "s1",x1 );
        intent.putExtras( bundle1 );
        intent.putExtra("dep", "Civil");
        startActivity( intent );
    }
}
