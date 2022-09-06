package com.example.quraan_app;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class bynameList extends AppCompatActivity{

    String DB_PATH;
    final Context context=this;
    private SQLiteDatabase mDataBase;
    private static String DB_NAME ="QuranDB.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.byname_list);

        TextView txt = (TextView) findViewById(R.id.txt);
        ListView listView = (ListView) findViewById(R.id.listViewAyah);

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
        Intent intent = new Intent(this, ayahActivity.class);
        intent.putExtra("surahIndex",String.valueOf(ii));
        startActivity(intent);

    }

}
