package com.example.quraan_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
}