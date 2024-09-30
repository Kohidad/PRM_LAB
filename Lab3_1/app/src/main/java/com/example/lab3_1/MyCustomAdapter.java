package com.example.lab3_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyCustomAdapter extends BaseAdapter {

    ArrayList<String> myItems;
    Context context;
    LayoutInflater inflater;

    public MyCustomAdapter(Context context, ArrayList<String> myItems) {
        this.context = context;
        this.myItems = myItems;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return myItems.size();
    }

    @Override
    public Object getItem(int i) {
        return myItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.my_custom_item_list_view, null);
        TextView textView = (TextView) view.findViewById(R.id.itemTitle);
        textView.setText(myItems.get(i));
        return view;
    }
}
