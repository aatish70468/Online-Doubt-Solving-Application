package com.example.aatish.onlinedoubt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.*;
import android.support.v7.widget.Toolbar;
import com.google.firebase.database.*;

import java.util.ArrayList;

public class answer extends AppCompatActivity {

    ListView listView;
    int j;
    int count = 0;
    String sub;
    DatabaseReference mRef;
    ArrayList<String> acs = new ArrayList<>();
    ArrayList<String> acs1 = new ArrayList<>();
    ArrayList<String> acs2 = new ArrayList<>();
    String titlesText[] = {};
    String detailsArray[] = {};
    String keyArray[] = {};
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_answer );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        context = this;

        listView = (ListView) findViewById( R.id.list2 );
        sub = getIntent().getExtras().getString( "sub_name" );
        setTitle( sub );
        mRef = FirebaseDatabase.getInstance().getReference( "Subject" ).child( sub );
        mRef.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                count = (int) dataSnapshot.getChildrenCount();
                for (j = 0; j < count; j++) {
                    acs.add( " Que.:" + (j + 1) );
                }
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String p1 = ds.child( "Questions" ).getValue().toString();
                    acs1.add( p1 );
                    String p2 = ds.getKey();
                    acs2.add( p2 );
                }
                titlesText = acs.toArray( new String[acs.size()] );
                detailsArray = acs1.toArray( new String[acs1.size()] );
                keyArray = acs2.toArray( new String[acs2.size()] );
                ans_dis( titlesText, detailsArray, keyArray );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }

    public void ans_dis(final String[] z1, final String[] z2, final String[] z3) {
        MyAdapter adapter = new MyAdapter( this, z1, z2, z3 );
        listView.setAdapter( adapter );
        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String q1 = z1[i];
                String q2 = z2[i];
                String q3 = z3[i];
                Intent intent = new Intent( getApplicationContext(), add_answer.class );
                intent.putExtra( "subject_name", sub );
                intent.putExtra( "q_no", q1 );
                intent.putExtra( "q_dis", q2 );
                intent.putExtra( "q_key", q3 );
                startActivity( intent );
            }
        } );
    }

}

//My Adapter
class MyAdapter extends ArrayAdapter {
    String[] a1;
    String[] a2;
    String[] a3;

    public MyAdapter(Context context, String[] b1, String[] b2, String[] b3) {
        super( context, R.layout.ans_data, R.id.que_no, b1 );
        this.a1 = b1;
        this.a2 = b2;
        this.a3 = b3;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View row = inflater.inflate( R.layout.ans_data, parent, false );

        TextView mQue_no = (TextView) row.findViewById( R.id.que_no );
        TextView mQue_dis = (TextView) row.findViewById( R.id.que_dis );

        mQue_no.setText( a1[position] );
        mQue_dis.setText( a2[position] );
        return row;
    }
}