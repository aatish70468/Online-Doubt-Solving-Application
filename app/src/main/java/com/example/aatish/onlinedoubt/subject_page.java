package com.example.aatish.onlinedoubt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class subject_page extends AppCompatActivity {

    ListView listView;
    dis_subject d1 = new dis_subject(  );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_subject_page );
        String s1 = getIntent().getStringExtra("dep");
        setTitle(s1);

        listView = (ListView)findViewById( R.id.list1 );
        Bundle b1 = getIntent().getExtras();
        final String[] sem1 = b1.getStringArray( "s1" );
        ArrayAdapter<String> adapter = new ArrayAdapter<String>( this, R.layout.sub_display,R.id.display,sem1 );
        listView.setAdapter( adapter );

        listView.setOnItemClickListener( new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView,View view,int i,long l){
                dis_subject selItem = (dis_subject) listView.getSelectedItem();
                //assert sem1 != null;
                String sub1 = sem1[i];
                Intent intent = new Intent( getApplicationContext(),answer.class );
                intent.putExtra( "sub_name",sub1 );
                startActivity( intent );
            }
        } );
    }
}
