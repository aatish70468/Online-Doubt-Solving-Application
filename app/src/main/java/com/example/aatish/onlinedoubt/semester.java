package com.example.aatish.onlinedoubt;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.google.firebase.database.*;

import java.util.ArrayList;

public class semester extends AppCompatActivity {

    Button s1;
    EditText val;
    DatabaseReference mRef1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_semester );
        s1 = (Button)findViewById( R.id.semester1 );
        val = (EditText)findViewById( R.id.mon );
        val.setText(getIntent().getExtras().getString( "depart" ));
        final String a = val.getText().toString();
        //for sem1
        s1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String b = "sem1";
                Intent intent1 = new Intent( semester.this,subject_page.class );
                intent1.putExtra( "depart1",a );
                intent1.putExtra( "semester",b );
                startActivity( intent1 );
            }
        } );
    }
}
