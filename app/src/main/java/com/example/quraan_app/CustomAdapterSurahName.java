package com.example.quraan_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomAdapterSurahName extends BaseAdapter {

    Context context;
    ArrayList<String> surahNameEng;
    ArrayList<String> surahNameUrdu;
    LayoutInflater inflater;


    CustomAdapterSurahName(Context ctxt, ArrayList<String> surahNameEng, ArrayList<String> surahNameUrdu)
    {
        this.context=ctxt;
        this.surahNameEng = surahNameEng;
        this.surahNameUrdu = surahNameUrdu;
        inflater = LayoutInflater.from(ctxt);
    }


    @Override
    public int getCount() {
        return surahNameEng.size();

    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @SuppressLint("LongLogTag")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.activity_custom_list_view_surah_name,null);
        TextView surahNameEngg = view.findViewById(R.id.surahNameEng);
        TextView surahNameUrduu = view.findViewById(R.id.surahNameUrdu);
        Log.i("get returns: ",surahNameEng.get(i));
        surahNameEngg.setText(surahNameEng.get(i));
        surahNameUrduu.setText(surahNameUrdu.get(i));
        return view;
    }
}
