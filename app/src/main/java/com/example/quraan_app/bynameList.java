package com.example.quraan_app;

import android.content.Intent;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.io.IOException;

public class bynameList extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.byname_list);

        NavigationView navigationView;
        DrawerLayout drawerLayout;
        Toolbar toolbar;
        ActionBarDrawerToggle toggle;

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView=findViewById(R.id.nav_view);
        drawerLayout=findViewById(R.id.drawer);

        toggle=new ActionBarDrawerToggle(bynameList.this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.read_quran :
                        Intent intent1 = new Intent(bynameList.this, bynameList.class);
                        startActivity(intent1);
                        //drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.home :
                        Intent intent2 = new Intent(bynameList.this, MainActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.search:
                        Intent intent3 = new Intent(bynameList.this, SearchFunction.class);
                        startActivity(intent3);
                        break;

                }

                return true;
            }
        });

        TextView txt = (TextView) findViewById(R.id.txt);
        ListView listView = (ListView) findViewById(R.id.listViewSurah);

        DBMain db;
        db = new DBMain(getApplicationContext());

        try {

            db.createDB();
        } catch (IOException ioe) {

            throw new Error("Database not created....");
        }

        try {
            db.openDB();

        } catch (SQLException sqle) {

            throw sqle;
        }
        db.DBSurahNames();

        CustomAdapterSurahName customBaseAdapter = new CustomAdapterSurahName(getApplicationContext(),db.getSurahNameEng(),db.getSurahNameUrdu());
        listView.setAdapter(customBaseAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) { toAyahList(i); }
        });

    }
    private void toAyahList(int ii)
    {
//        Intent intent = new Intent(this, ayahActivity.class);
//        intent.putExtra("surahIndex",String.valueOf(ii));
//        startActivity(intent);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT,"OOP SS");
        intent.putExtra(Intent.EXTRA_EMAIL,"bsef19m049@pucit.edu.pk");
        if (intent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(intent);
        }

    }

    DrawerLayout drawerLayout;
    int counter =0;
    @Override
    public void onBackPressed(){
        counter++;
        if(counter == 2)
        {
            super.onBackPressed();
        }

    }

}
