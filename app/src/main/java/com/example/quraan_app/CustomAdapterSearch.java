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

public class CustomAdapterSearch extends BaseAdapter {

    Context context;
    ArrayList<String> First;
    ArrayList<String> Second;
    LayoutInflater inflater;

    CustomAdapterSearch(Context ctxt, ArrayList<String> First, ArrayList<String> Second)
    {
        this.context=ctxt;
        this.First = First;
        this.Second = Second;
        inflater = LayoutInflater.from(ctxt);
    }

    @Override
    public int getCount() {
        return Second.size();
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
        view = inflater.inflate(R.layout.activity_custom_list_view_search,null);
        TextView First1 = view.findViewById(R.id.firstTextViewSearch);
        TextView Second1 = view.findViewById(R.id.secondTextViewSearch);
        Log.i("get returns: ",First.get(i));
        First1.setText(First.get(i));
        Second1.setText(Second.get(i));
        return view;
    }
}
