package com.example.aatish.onlinedoubt;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import com.google.firebase.database.*;

import java.util.ArrayList;

public class add_answer extends AppCompatActivity {

    TextView mQue_no,mQue_dis;
    ListView mans_dis;
    EditText mwrite_ans;
    ImageButton ans_submit;
    String name;
    DatabaseReference mRef,mRef1;
    String sub_n,m1,q_key;
    String question;
    String[] titlesText = {};
    ArrayList<String> acs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_answer );

        mQue_no = (TextView) findViewById( R.id.titleTextView );
        mQue_dis = (TextView) findViewById( R.id.contentTextView );
        mans_dis = (ListView) findViewById( R.id.contentTextViewAns );
        mwrite_ans = (EditText) findViewById( R.id.que_ans );
        ans_submit = (ImageButton) findViewById( R.id.add_ans );

        sub_n = getIntent().getExtras().getString( "subject_name" );
        q_key = getIntent().getExtras().getString( "q_key" );
        setTitle( sub_n );
        mQue_no.setText( getIntent().getExtras().getString( "q_no" ) );
        mQue_dis.setText( getIntent().getExtras().getString( "q_dis" ) );

        question = mQue_dis.toString();
        mRef = FirebaseDatabase.getInstance().getReference("Subject/"+sub_n+"/"+q_key);
        ans_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = mwrite_ans.getText().toString();

                if(name.isEmpty()){
                    AlertDialog.Builder alertdialogbox = new AlertDialog.Builder(add_answer.this);
                    alertdialogbox.setTitle("Alert").setMessage("Please Write your answer...")
                            .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mwrite_ans.setText("");
                                }
                            }).show();
                }
                else {
                    mRef.child( "answer" ).push().setValue( name );
                    AlertDialog.Builder alertdialogbox = new AlertDialog.Builder(add_answer.this);
                    alertdialogbox.setTitle("Alert").setMessage("Answer Added Successfully...").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(add_answer.this,add_answer.class);
                            startActivity(intent);
                        }
                    }).show();
                }
            }
        });

        mRef1 = FirebaseDatabase.getInstance().getReference("Subject/"+sub_n+"/"+q_key).child( "answer" );
        mRef1.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String p1 = ds.getValue().toString();
                    acs.add( p1 );
                }
                titlesText = acs.toArray( new String[acs.size()] );
                ans_add( titlesText );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

        mans_dis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String sub1 = titlesText[position];
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(add_answer.this);
                alertDialogBuilder.setTitle("Answer").setMessage(sub1)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent studentregister = new Intent(add_answer.this,add_answer.class);
                                startActivity(studentregister);
                            }
                        }).show();
            }
        });
    }

    public void ans_add(String[] x1){
        ArrayAdapter<String> adapter = new ArrayAdapter<>( this,R.layout.sub_display,R.id.display,x1 );
        mans_dis.setAdapter( adapter );
    }
}
