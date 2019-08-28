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
import com.google.firebase.database.*;

public class faculty_registration extends AppCompatActivity {

    EditText mTextname;
    EditText mTextfacultyid;
    EditText mTextemail;
    EditText mTextpassword;
    EditText mTextcnfpassword;
    Button mButtonSignup;

    String name;
    String facultyid;
    String email;
    String pass;
    String cnfpass;
    String enrollment_valid = "[0-9]{6}";

    // [START declare_database_ref]
    FirebaseDatabase database;
    DatabaseReference mRef;
    // [END declare_database_ref]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_registration);

        database = FirebaseDatabase.getInstance();
        mRef = database.getReference("Faculty");

        //Fetch all details
        mTextname = (EditText) findViewById(R.id.name1);
        mTextfacultyid = (EditText) findViewById(R.id.faculty_id);
        mTextemail = (EditText) findViewById(R.id.email1);
        mTextpassword = (EditText) findViewById(R.id.pass1);
        mTextcnfpassword = (EditText) findViewById(R.id.cnfpass1);
        mButtonSignup = (Button)findViewById(R.id.fac_signup);

        mButtonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = mTextname.getText().toString();
                facultyid = mTextfacultyid.getText().toString();
                email = mTextemail.getText().toString();
                pass = mTextpassword.getText().toString();
                cnfpass = mTextcnfpassword.getText().toString();

                //Validation
                if (name.isEmpty()){
                    mTextname.setError("Enter Your Name");
                }
                else if (facultyid.isEmpty() || facultyid.length()<6 || facultyid.length()>6 || !facultyid.matches(enrollment_valid)){
                    mTextfacultyid.setError("Enter valid Enrollment Number");
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
                    addnewuser();
                }
            }
        });
    }

    private void addnewuser(){
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(facultyid)){
                    AlertDialog.Builder alertdialogbox = new AlertDialog.Builder(faculty_registration.this);
                    alertdialogbox.setTitle("Alert").setMessage("Faculty Id Alread Exists...").setPositiveButton("LogIn", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(faculty_registration.this,faculty_login.class);
                            startActivity(intent);
                            finish();
                        }
                    }).show();
                }
                else {
                    User user = new User(name,facultyid,email,pass,cnfpass);
                    mRef.child(facultyid).setValue(user);

                    AlertDialog.Builder alertdialogbox = new AlertDialog.Builder(faculty_registration.this);
                    alertdialogbox.setTitle("Alert").setMessage("Registration Successful...").setPositiveButton("LogIn", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(faculty_registration.this,faculty_login.class);
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
