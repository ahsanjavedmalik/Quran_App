package com.example.quraan_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class SearchFunction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_function);
        EditText SearchText = findViewById(R.id.editTextSearch);
        Button Search = findViewById(R.id.searchBtn);
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
                Toast.makeText(getApplicationContext(),"Result = "+buffer,Toast.LENGTH_SHORT).show();
            }
        });
    }
}