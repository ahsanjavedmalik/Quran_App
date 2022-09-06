package com.example.quraan_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class custom_list_view_search extends AppCompatActivity {
    public String FTVS;
    public String STVS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list_view_search);
        TextView firstTVSearch = findViewById(R.id.firstTextViewSearch);
        TextView secondTVSearch = findViewById(R.id.secondTextViewSearch);
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