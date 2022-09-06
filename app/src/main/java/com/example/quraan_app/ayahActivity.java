package com.example.quraan_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.ArrayList;

public class ayahActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayah);
        ListView listViewAyah = findViewById(R.id.listViewAyah);
        Intent intent = getIntent();
        int index=Integer.parseInt(intent.getStringExtra("surahIndex"));
        Toast.makeText(this, "Index: " + index, Toast.LENGTH_SHORT).show();

        NavigationView navigationView;
        DrawerLayout drawerLayout;
        Toolbar toolbar;
        ActionBarDrawerToggle toggle;

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView=findViewById(R.id.nav_view);
        drawerLayout=findViewById(R.id.drawer);

        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.read_quran :
                        Intent intent1 = new Intent(ayahActivity.this, bynameList.class);
                        startActivity(intent1);
                        //drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.home :
                        Intent intent2 = new Intent(ayahActivity.this, MainActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.search:
                        Intent intent3 = new Intent(ayahActivity.this, SearchFunction.class);
                        startActivity(intent3);
                        break;

                }

                return true;
            }
        });

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
        db.DBAyahs(index);
        CustomAdapterAyah customBaseAdapter = new CustomAdapterAyah(getApplicationContext(),db.getAyahsArabic(),db.getAyahsUrdu());
        listViewAyah.setAdapter(customBaseAdapter);

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