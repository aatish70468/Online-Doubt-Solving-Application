package com.example.aatish.onlinedoubt;

import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import com.google.firebase.database.*;

import java.util.ArrayList;

import static com.example.aatish.onlinedoubt.notification_1.CHANNEL_ID;

public class add_question extends AppCompatActivity {

    Spinner mTextName;
    Spinner mBranch_name,mSemester;
    EditText mTextQues;
    Button mSubmit;
    String subject,q_add,id_1;
    FirebaseDatabase database;
    DatabaseReference mRef,mRef1;
    float maxid = 0;
    ArrayList<String> acs1 = new ArrayList<>();
    User user,u1;
    String m2,branch,sem;
    String[] branch_name = {"Civil","Computer","Electrical","IT","Mechanical"};
    String[] sem_name = {"sem 1 & 2","sem 3","sem 4"};
    String[] sub_name = {};
    NotificationManagerCompat notificationManagerCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_question );
        setTitle("Add Questions");

        mTextName = (Spinner)findViewById(R.id.sub_name);
        mBranch_name = (Spinner)findViewById(R.id.branch_name);
        mSemester = (Spinner)findViewById(R.id.sem_name);
        mTextQues = (EditText)findViewById(R.id.editText);
        mSubmit = (Button)findViewById(R.id.que_submit);
        user = new User();
        u1 = new User(  );
        notificationManagerCompat = NotificationManagerCompat.from( this );
        database = FirebaseDatabase.getInstance();

        ArrayAdapter<String> adapter_1 = new ArrayAdapter<>( this,android.R.layout.simple_spinner_dropdown_item,branch_name );
        mBranch_name.setAdapter( adapter_1 );
        mBranch_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                branch = parent.getItemAtPosition(position).toString();

                //Second Spinner
                ArrayAdapter<String> adapter_2 = new ArrayAdapter<String>( add_question.this,android.R.layout.simple_spinner_dropdown_item,sem_name );
                mSemester.setAdapter( adapter_2 );

                //Second Spinner Selected
                mSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        sem = parent.getItemAtPosition(position).toString();

                        //Third Spinner
                        mRef1 = database.getReference("Department/"+branch+"/"+sem);
                        mRef1.addListenerForSingleValueEvent( new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    String p1 = ds.child( "Subject" ).getValue().toString();
                                    acs1.add( p1 );
                                }
                                sub_name = acs1.toArray( new String[acs1.size()] );
                                sub_add(sub_name);
                                acs1.clear();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        } );
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mTextName.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subject = parent.getItemAtPosition(position).toString();
                mRef = database.getReference("Subject/"+subject);
                mRef.addValueEventListener( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists())
                        {
                            maxid = (dataSnapshot.getChildrenCount());
                        }
                        else
                        {
                            maxid = 0;
                        }
                        maxid = maxid+1;
                        id_1 = String.valueOf( maxid );
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                } );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );


        mSubmit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q_add = mTextQues.getText().toString();
                m2 = mRef.push().getKey();
                mRef.push().child( "Questions" ).setValue( q_add );

                //Create Notification Start
                Notification notification = new NotificationCompat.Builder( add_question.this, CHANNEL_ID )
                        .setSmallIcon( R.drawable.icon1 )
                        .setContentTitle( "Online Doubt Solving Application" )
                        .setContentText( "Question Recieved of "+subject )
                        .setPriority( NotificationCompat.PRIORITY_HIGH )
                        .setCategory( NotificationCompat.CATEGORY_MESSAGE )
                        .build();
                notificationManagerCompat.notify( R.drawable.icon1, notification );
                //Create Notification End

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(add_question.this);
                alertDialogBuilder.setTitle("Alert").setMessage("Question Added")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent studentregister = new Intent(add_question.this,home_1.class);
                                startActivity(studentregister);
                                finish();
                            }
                        }).show();


                /*Map< String, String > map1 = new HashMap<>();
                map1.put( id, q_add );*//*
                mRef.child( id ).setValue( q_add );*/
            }
        } );
    }

    public void sub_add(String[] x1){
        ArrayAdapter<String> adapter = new ArrayAdapter<>( this,android.R.layout.simple_spinner_dropdown_item,x1 );
        mTextName.setAdapter( adapter );
    }
}
