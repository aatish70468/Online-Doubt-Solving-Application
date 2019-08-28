package com.example.aatish.onlinedoubt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class home_1 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView mTextEnrollment;
    TextView mTextName;
    Button mCivil;
    Button mComputer;
    Button mElectrical;
    Button mIT;
    Button mMechanical;
    View hview;
    String id,u_name;
    Integer num;
    DatabaseReference mRef;
    String fac_id = null;
    String enrollment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home_1 );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        setTitle("Home");

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();

        //navigation start
        NavigationView navigationView = (NavigationView) findViewById( R.id.nav_view );
        hview = navigationView.getHeaderView( 0 );

        mTextEnrollment = (TextView) hview.findViewById( R.id.enrollment );
        mTextName = (TextView) hview.findViewById( R.id.name1 );
        Intent intent = getIntent();
        Intent intent1 = getIntent();
        fac_id = intent.getStringExtra("fac_id");
        enrollment = intent1.getStringExtra("enrollment");
        if (enrollment != null)
        {
            u_name = "Student";
            id = enrollment;
            num = 0;
        }
        else
        {
            u_name = "Faculty";
            id = fac_id;
            num = 1;
        }
        mRef = FirebaseDatabase.getInstance().getReference().child(u_name).child( id );
        mRef.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String e1 = dataSnapshot.child( "enrollment" ).getValue().toString();
                String e2 = dataSnapshot.child( "name" ).getValue().toString();
                mTextEnrollment.setText( e1 );
                mTextName.setText( e2 );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
        navigationView.setNavigationItemSelectedListener( this );
        //navigation ends

        //content start
        mCivil = (Button) findViewById( R.id.civil );
        mComputer = (Button) findViewById( R.id.computer );
        mElectrical = (Button) findViewById( R.id.electrical );
        mIT = (Button) findViewById( R.id.it );
        mMechanical = (Button) findViewById( R.id.mechanical );

        mCivil.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String abc1 = "Civil";
                Intent intent1 = new Intent( home_1.this, civil.class );
                startActivity( intent1 );
            }
        } );
        mComputer.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String abc2 = "Computer";
                Intent intent2 = new Intent( home_1.this, computer.class );
                startActivity( intent2 );
            }
        } );
        mElectrical.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String abc3 = "Electrical";
                Intent intent3 = new Intent( home_1.this, electrical.class );
                startActivity( intent3 );
            }
        } );
        mIT.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String abc4 = "IT";
                Intent intent4 = new Intent( home_1.this, it_1.class );
                startActivity( intent4 );
            }
        } );
        mMechanical.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String abc5 = "Mechanical";
                Intent intent5 = new Intent( home_1.this, mechanical.class );
                startActivity( intent5 );
            }
        } );
        //content ends
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.home_1, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected( item );
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent i1 = new Intent( home_1.this, home_1.class );
            startActivity( i1 );
        } else if (id == R.id.nav_gallery) {
            Intent i2 = new Intent( home_1.this, add_question.class );
            startActivity( i2 );
        } else if (id == R.id.nav_logout) {

            SharedPreferences sharedPreferences = getSharedPreferences(student_login.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();

            Intent i3 = new Intent( home_1.this, main_login.class );
            startActivity( i3 );
            finish();
        } else if (id == R.id.nav_add_subject) {
            if (num == 1) {
                Intent i3 = new Intent(home_1.this, sample.class);
                startActivity(i3);
            }
            else{

            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }
}
