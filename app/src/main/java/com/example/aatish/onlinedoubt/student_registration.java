package com.example.aatish.onlinedoubt;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class student_registration extends AppCompatActivity {

    EditText mTextname;
    EditText mTextenrollment;
    EditText mTextemail;
    EditText mTextpassword;
    EditText mTextcnfpassword;
    Button mButtonSignup;

    String name;
    String enrollment;
    String email;
    String pass;
    String cnfpass;
    String enrollment_valid = "[0-9]{2}[124]{3}[01|31]{2}[06|07|09|16|19]{2}[0-9]{3}";

    // [START declare_database_ref]
    FirebaseDatabase database;
    DatabaseReference mRef;
    // [END declare_database_ref]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        database = FirebaseDatabase.getInstance();
        mRef = database.getReference("Student");

        //Fetch all details
        mTextname = (EditText) findViewById(R.id.name2);
        mTextenrollment = (EditText) findViewById(R.id.stu_enrollment);
        mTextemail = (EditText) findViewById(R.id.email2);
        mTextpassword = (EditText) findViewById(R.id.pass2);
        mTextcnfpassword = (EditText) findViewById(R.id.cnfpass2);
        mButtonSignup = (Button)findViewById(R.id.stu_signup);

        mButtonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = mTextname.getText().toString();
                enrollment = mTextenrollment.getText().toString();
                email = mTextemail.getText().toString();
                pass = mTextpassword.getText().toString();
                cnfpass = mTextcnfpassword.getText().toString();

                //Validation
                if (name.isEmpty()){
                    mTextname.setError("Enter Your Name");
                }
                else if (enrollment.isEmpty() || enrollment.length()<12 || enrollment.length()>12 || !enrollment.matches(enrollment_valid)){
                    mTextenrollment.setError("Enter valid Enrollment Number");
                }
                else if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    mTextemail.setError("Enter valid Email");
                }
                else if (pass.isEmpty() || pass.length()<6){
                    mTextpassword.setError("Enter password above 6 characters");
                }
                else if (cnfpass.isEmpty() || !cnfpass.equals(pass)) {
                    mTextcnfpassword.setError("Password and Confirm Password are not same.");
                }
                else {
                    addNewUser();
                }
            }
        });

    }

    private  void addNewUser(){
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(enrollment)){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(student_registration.this);
                    alertDialogBuilder.setTitle("Alert").setMessage("Enrollment Number Alerady Exists...")
                            .setPositiveButton("LogIn", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(student_registration.this,student_login.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }).show();
                }
                else {
                    User user = new User(name,enrollment,email,pass,cnfpass);
                    mRef.child(enrollment).setValue(user);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(student_registration.this);
                    alertDialogBuilder.setTitle("Alert").setMessage("Registration Successful...")
                            .setPositiveButton("LogIn", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(student_registration.this,student_login.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
