package com.example.quraan_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.io.IOException;

public class SearchFunction extends customListViewAyah {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_function);
        EditText SearchText = findViewById(R.id.editTextSearch);
        Button Search = findViewById(R.id.searchBtn);
        ListView SearchListView = findViewById(R.id.searchListView);
        Switch suraNameSearch = findViewById(R.id.toggleSurahName);
        Switch ayahNameSearch = findViewById(R.id.toggleAyah);
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