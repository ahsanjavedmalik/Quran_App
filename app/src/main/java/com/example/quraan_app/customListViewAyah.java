package com.example.quraan_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class customListViewAyah extends AppCompatActivity {
    public String FTVS;
    public String STVS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list_view_ayah);
        TextView firstTVSearch = findViewById(R.id.ArabicText);
        TextView secondTVSearch = findViewById(R.id.UrduText);
        FTVS = String.valueOf(firstTVSearch.getText());
        STVS = String.valueOf(secondTVSearch.getText());
    }

    public String getFirstTVSearch()
    {
        return FTVS;
    }
    public String getSecondTVSearch()
    {
        return STVS;
    }

}