package com.example.aatish.onlinedoubt;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class main_login extends AppCompatActivity {

    Button mButtonStudent;
    Button mButtonFaculty;
    PrefManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        session = new PrefManager(getApplicationContext());
        mButtonStudent = (Button)findViewById(R.id.button1);
        mButtonFaculty = (Button)findViewById(R.id.button4);
        mButtonStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent studentintent = new Intent(main_login.this,student_login.class);
                startActivity(studentintent);
            }
        });

        mButtonFaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facultyintent = new Intent(main_login.this,faculty_login.class);
                startActivity(facultyintent);
            }
        });
    }
}
