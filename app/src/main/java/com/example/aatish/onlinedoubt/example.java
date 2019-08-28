package com.example.aatish.onlinedoubt;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.*;

public class example extends AppCompatActivity {

    EditText mTextName;
    Button mLogout;
    String subject;
    PrefManager session;
    FirebaseDatabase database;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        database = FirebaseDatabase.getInstance();
        mRef = database.getReference("Computer/Sem1");
        session = new PrefManager(getApplicationContext());

        mTextName = (EditText)findViewById(R.id.name3);
        mLogout = (Button)findViewById(R.id.logout);

        Toast.makeText(getApplicationContext(),"User status: "+ session.isLoggedIn(),Toast.LENGTH_LONG).show();
        mLogout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subject = mTextName.getText().toString();
                addnewsubject();
            }
        } );
    }

    public void addnewsubject(){
        mRef.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = new User(subject);
                mRef.child( subject ).setValue( user );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }
}
