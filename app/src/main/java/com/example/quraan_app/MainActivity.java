package com.example.quraan_app;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button toSurahList = findViewById(R.id.button);
        Button Search = findViewById(R.id.buttonSearch);

        toSurahList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toByNameList();
            }
        });

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { toSearchActivity(); }
        });

    }
    private void toByNameList()
    {
        Intent intent = new Intent(this, bynameList.class);
        startActivity(intent);
    }

    private void toSearchActivity()
    {
        Intent intent = new Intent(this,SearchFunction.class);
        startActivity(intent);
    }


}