package com.example.aatish.onlinedoubt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class sample extends AppCompatActivity {

    Spinner mBranch_name,mSemester;
    EditText mTextsubname;
    Button mSubmit;
    String sub_name,branch,sem;
    FirebaseDatabase database;
    DatabaseReference mRef,mRef1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sample );
        setTitle("Add Subjects");

        mBranch_name = (Spinner)findViewById(R.id.branch_name);
        mSemester = (Spinner)findViewById(R.id.sem_name);
        mTextsubname = (EditText)findViewById(R.id.editText);
        mSubmit = (Button)findViewById(R.id.que_submit);

        database = FirebaseDatabase.getInstance();
        final String[] branch_name = {"Civil","Computer","Electrical","IT","Mechanical"};
        String[] sem_name = {"sem 1 & 2","sem 3","sem 4"};

        ArrayAdapter<String> adapter_1 = new ArrayAdapter<>( this,android.R.layout.simple_spinner_dropdown_item,branch_name );
        mBranch_name.setAdapter( adapter_1 );
        ArrayAdapter<String> adapter_2 = new ArrayAdapter<>( this,android.R.layout.simple_spinner_dropdown_item,sem_name );
        mSemester.setAdapter( adapter_2 );

        mBranch_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                branch = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sem = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sub_name = mTextsubname.getText().toString();
                mRef = database.getReference("Department/"+branch+"/"+sem);
                mRef.push().child("Subject").setValue(sub_name);
            }
        });
    }
}
