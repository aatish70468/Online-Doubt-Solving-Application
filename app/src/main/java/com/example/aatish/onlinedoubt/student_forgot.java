package com.example.aatish.onlinedoubt;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.*;

public class student_forgot extends AppCompatActivity {

    EditText menroll;
    TextView mdisplay;
    Button mbutton;
    DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_forgot);
        menroll = (EditText)findViewById(R.id.stu_forgot);
        mdisplay = (TextView) findViewById(R.id.for_dis);
        mbutton = (Button)findViewById( R.id.stu_submit );

        mbutton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id = menroll.getText().toString();
                if (id.isEmpty())
                {
                    Toast.makeText(student_forgot.this, "Enter your Enrollment and Password..", Toast.LENGTH_LONG).show();
                }
                else {
                    mRef = FirebaseDatabase.getInstance().getReference("Student").child( id );
                    mRef.addValueEventListener( new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final String pass = dataSnapshot.child( "pass" ).getValue().toString();
                            mdisplay.setText("Your Password:"+pass);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    } );
                }
            }
        } );
    }
}
