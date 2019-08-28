package com.example.aatish.onlinedoubt;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class student_login extends AppCompatActivity {

    EditText mTextEnrollment;
    EditText mTextPassword;
    Button mButtonLogin;
    Button mTextClickRegister;
    TextView mTextStudentForgot;

    String enroll;
    String pass;

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Enrollment = "enrollmentKey";
    public static final String Password = "passwordKey";
    SharedPreferences sharedPreferences;

    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        mTextEnrollment = (EditText)findViewById(R.id.enrollment);
        mTextPassword = (EditText)findViewById(R.id.password2);
        mButtonLogin = (Button)findViewById(R.id.student_login);
        mTextClickRegister = (Button) findViewById(R.id.student_register);
        mTextStudentForgot = (TextView)findViewById(R.id.stu_forgot);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        mTextClickRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sturegisterintent = new Intent(student_login.this,student_registration.class);
                startActivity(sturegisterintent);
            }
        });

        mTextStudentForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stuforgotpass = new Intent(student_login.this,student_forgot.class);
                startActivity(stuforgotpass);
            }
        });

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enroll = mTextEnrollment.getText().toString();
                pass = mTextPassword.getText().toString();
                if (enroll.isEmpty())
                {
                    if (pass.isEmpty()){
                        Toast.makeText(student_login.this, "Enter your Enrollment and Password..", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(student_login.this,"Enter your Enrollment no..",Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    if (pass.isEmpty()){
                        Toast.makeText(student_login.this, "Enter your Password..", Toast.LENGTH_LONG).show();
                    }
                    else {
                        mRef = FirebaseDatabase.getInstance().getReference("Student").child(enroll);
                        mRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                final String e1 = dataSnapshot.child("enrollment").getValue().toString();
                                final String p1 = dataSnapshot.child("pass").getValue().toString();

                                if(enroll.equals(e1)){
                                    if (pass.equals(p1)){

                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(Enrollment, enroll);
                                        editor.putString(Password, pass);
                                        editor.commit();

                                        //User user = new User(enroll);
                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(student_login.this);
                                        alertDialogBuilder.setTitle("Alert").setMessage("LogIn Successful...")
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Intent studentregister = new Intent(student_login.this,home_1.class);
                                                        studentregister.putExtra("enrollment",e1.toString());
                                                        startActivity(studentregister);
                                                        finish();
                                                    }
                                                }).show();
                                    }
                                    else {
                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(student_login.this);
                                        alertDialogBuilder.setTitle("Alert").setMessage("Enrollment and Password  is Incorrect...")
                                                .setPositiveButton("RETRY", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        mTextPassword.setText("");
                                                    }
                                                }).show();
                                    }
                                }
                                else {
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(student_login.this);
                                    alertDialogBuilder.setTitle("Alert").setMessage("Enrollment is Incorrect...")
                                            .setPositiveButton("RETRY", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        mTextEnrollment.setText("");
                                                    }
                                            }).show();
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(student_login.this,"DataBase Error...",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }
        });
    }
}
