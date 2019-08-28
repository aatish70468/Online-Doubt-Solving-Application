package com.example.aatish.onlinedoubt;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.*;

public class faculty_login extends AppCompatActivity {

    EditText mTextFacltyid;
    EditText mTextPassword;
    Button mButtonLogin;
    Button mTextClickRegister;
    TextView mTextfacultyforgot;

    String facid;
    String pass;

    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_login);

        mTextFacltyid = (EditText)findViewById(R.id.faculty_id);
        mTextPassword = (EditText)findViewById(R.id.password2);
        mButtonLogin = (Button)findViewById(R.id.faculty_login);
        mTextClickRegister = (Button) findViewById(R.id.faculty_register);
        mTextfacultyforgot = (TextView)findViewById(R.id.fac_forgot);

        mTextClickRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facregisterintent = new Intent(faculty_login.this,faculty_registration.class);
                startActivity(facregisterintent);
            }
        });

        mTextfacultyforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facforgotpass = new Intent(faculty_login.this,faculty_forgot.class);
                startActivity(facforgotpass);
            }
        });

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facid = mTextFacltyid.getText().toString();
                pass = mTextPassword.getText().toString();
                if (facid.isEmpty())
                {
                    if (pass.isEmpty()){
                        Toast.makeText(faculty_login.this, "Enter your Enrollment and Password..", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(faculty_login.this,"Enter your Enrollment no..",Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    if (pass.isEmpty()){
                        Toast.makeText(faculty_login.this, "Enter your Password..", Toast.LENGTH_LONG).show();
                    }
                    else {
                        mRef = FirebaseDatabase.getInstance().getReference("Faculty").child(facid);
                        mRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                final String e2 = dataSnapshot.child("enrollment").getValue().toString();
                                final String p2 = dataSnapshot.child("pass").getValue().toString();

                                if(facid.equals(e2)){
                                    if (pass.equals(p2)){
                                        //User user = new User(enroll);
                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(faculty_login.this);
                                        alertDialogBuilder.setTitle("Alert").setMessage("LogIn Successful...")
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Intent studentregister = new Intent(faculty_login.this,home_1.class);
                                                        studentregister.putExtra("fac_id",e2.toString());
                                                        startActivity(studentregister);
                                                        finish();
                                                    }
                                                }).show();
                                    }
                                    else {
                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(faculty_login.this);
                                        alertDialogBuilder.setTitle("Alert").setMessage("Enrollment and Password  is Incorrect...")
                                                .setPositiveButton("RETRY", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        mTextPassword.setText("");
                                                        /*Intent studentregister = new Intent(student_login.this,example.class);
                                                        startActivity(studentregister);*/
                                                    }
                                                }).show();
                                    }
                                }
                                else {
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(faculty_login.this);
                                    alertDialogBuilder.setTitle("Alert").setMessage("Enrollment is Incorrect...")
                                            .setPositiveButton("RETRY", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    mTextFacltyid.setText("");
                                                        /*Intent studentregister = new Intent(student_login.this,example.class);
                                                        startActivity(studentregister);*/
                                                }
                                            }).show();
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(faculty_login.this,"DataBase Error...",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }
        });
    }
}
