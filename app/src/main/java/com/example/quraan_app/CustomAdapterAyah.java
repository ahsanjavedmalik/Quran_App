package com.example.quraan_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapterAyah extends BaseAdapter {

    Context context;
    ArrayList<String> AyahArabic;
    ArrayList<String> AyahUrdu;
    LayoutInflater inflater;

    CustomAdapterAyah(Context ctxt, ArrayList<String> AyahArabic, ArrayList<String> AyahUrdu)
    {
        this.context=ctxt;
        this.AyahArabic = AyahArabic;
        this.AyahUrdu = AyahUrdu;
        inflater = LayoutInflater.from(ctxt);
    }

    @Override
    public int getCount() {
        return AyahArabic.size();
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
        view = inflater.inflate(R.layout.activity_custom_list_view_ayah,null);
        TextView AyahArabic1 = view.findViewById(R.id.ArabicText);
        TextView AyahUrdu1 = view.findViewById(R.id.UrduText);
        Log.i("get returns: ",AyahArabic.get(i));
        AyahArabic1.setText(AyahArabic.get(i));
        AyahUrdu1.setText(AyahUrdu.get(i));
        return view;
    }
}
