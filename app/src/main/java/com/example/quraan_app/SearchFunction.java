package com.example.quraan_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.io.IOException;

public class SearchFunction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_function);
        EditText SearchText = findViewById(R.id.editTextSearch);
        Button Search = findViewById(R.id.searchBtn);
        ListView SearchListView = findViewById(R.id.searchListView);
        Switch paraNameSearch = findViewById(R.id.toggleAyah);
        Switch suraNameSearch = findViewById(R.id.toggleSurahName);
        //set the current state of a Switch
        paraNameSearch.setChecked(true);
        suraNameSearch.setChecked(true);

        // check current state of a Switch (true or false).
        Boolean paraSwitchState = paraNameSearch.isChecked();
        Boolean suraSwitchState = suraNameSearch.isChecked();

                Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchTxt = SearchText.getText().toString();
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

                SQLiteDatabase simpleDB = getApplicationContext().openOrCreateDatabase("QuranDb.db", Context.MODE_PRIVATE, null);
                Cursor c = simpleDB.rawQuery("select * from tsurah where SurahNameE='"+searchTxt+"'", null);
                if(c.getCount()==0)
                {
                    Toast.makeText(getApplicationContext(),"No Record Found",Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(c.moveToNext()){
                    buffer.append("SurahNameE "+c.getString(2)+"\n");
                }
                int index = Integer.parseInt(c.getString(0));
                Toast.makeText(getApplicationContext(),"Result = "+buffer,Toast.LENGTH_SHORT).show();
                ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,db.getSearchResult(index));
                SearchListView.setAdapter(arrayAdapter);
            }
        });
    }
}