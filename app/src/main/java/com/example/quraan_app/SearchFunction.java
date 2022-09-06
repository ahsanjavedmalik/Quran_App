package com.example.quraan_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.IOException;

public class SearchFunction extends customListViewAyah {

    DrawerLayout drawerLayout;

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
            Toast.makeText(getApplicationContext(),"Start",Toast.LENGTH_LONG).show();

        }
        else
        {
            Toast.makeText(getApplicationContext(),"End",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_function);
        EditText SearchText = findViewById(R.id.editTextSearch);
        Button Search = findViewById(R.id.searchBtn);
        ListView SearchListView = findViewById(R.id.searchListView);
        Switch suraNameSearch = findViewById(R.id.toggleSurahName);
        Switch ayahNameSearch = findViewById(R.id.toggleAyah);

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
                        Intent intent1 = new Intent(SearchFunction.this, bynameList.class);
                        startActivity(intent1);
                        //drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.home :
                        Intent intent2 = new Intent(SearchFunction.this, MainActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.search:
                        Intent intent3 = new Intent(SearchFunction.this, SearchFunction.class);
                        startActivity(intent3);
                        break;

                }

                return true;
            }
        });

        suraNameSearch.setChecked(true);
                Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                if((suraNameSearch.isChecked()==true) && (ayahNameSearch.isChecked()==true))
                {
                    ayahNameSearch.setChecked(false);
                    Toast.makeText(SearchFunction.this, "Searching by Surah Name (Search Either at a time)", Toast.LENGTH_SHORT).show();
                }
                String searchTxt = SearchText.getText().toString();
                String searchTxtAyah = SearchText.getText().toString();


                if(ayahNameSearch.isChecked()==true)
                {
                    db.DBSearch(searchTxt,true);
                    int one = 1;
                    if (one == 1) {
                        CustomAdapterSearch customBaseAdapter = new CustomAdapterSearch(getApplicationContext(), db.getFirst(), db.getSecond());
                        SearchListView.setAdapter(customBaseAdapter);
                        one++;
                    }
                }
                else if(suraNameSearch.isChecked()==true){
                    db.DBSearch(searchTxt,false);
                    int one = 1;
                    if (one == 1) {
                        CustomAdapterSearch customBaseAdapter = new CustomAdapterSearch(getApplicationContext(), db.getFirst(), db.getSecond());
                        SearchListView.setAdapter(customBaseAdapter);
                        one++;
                    }
                    SearchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Cursor c = db.DBSearch(searchTxt,false);
                            if (c.moveToFirst()) {
                                do {
                                    String surahItemNameEng = db.getItemNameEng(i);
                                    if (c.getString(0) != null) {
                                        Toast.makeText(SearchFunction.this, "surahItemName: " + surahItemNameEng, Toast.LENGTH_SHORT).show();
                                        if (c.getString(2).equals(surahItemNameEng)) {
                                            toAyahList(c.getInt(0) - 1);
                                            SearchListView.setAdapter(null);

                                        }
                                    }
                                } while (c.moveToNext());
                            }
                        }
                    });
                }
            }
        });
    }

    private void toAyahList(int ii)
    {
        Intent intent = new Intent(this, ayahActivity.class);
        Toast.makeText(SearchFunction.this, "ii" + ii, Toast.LENGTH_SHORT).show();
        intent.putExtra("surahIndex",String.valueOf(ii));
        startActivity(intent);
    }
}