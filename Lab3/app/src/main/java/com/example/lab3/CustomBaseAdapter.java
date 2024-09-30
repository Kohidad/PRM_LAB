package com.example.lab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

// A list view utilize what's called a Adapter, adapter is the bridge listView and data source.
// ListView data (View) is implemented by Adapter
public class CustomBaseAdapter extends BaseAdapter {

    Context context;
    String[] fruitList;
    int[] fruitImages;
    LayoutInflater inflater;

    // The constructor for each items
    public CustomBaseAdapter(Context context, String[] fruitList, int[] fruitImages){
        this.context = context;
        this.fruitList = fruitList;
        this.fruitImages = fruitImages;
        inflater = LayoutInflater.from(context);
    }

    // Basic calls.
    @Override
    public int getCount() {
        return fruitList.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // The inflater is used to instantiate the contents of the layout XML file into its corresponding View objects.
        // In this case, we're turning the XML layout 'activity_my_list_view' into a View object.
        // This allows us to access and manipulate the ImageView and TextView declared in the XML file within the Java class.

        // With the View object, we can set or get properties like images and text dynamically (important).
        // Dynamically means that the properties can change based on runtime, interaction from users.

        // For each item in the list, we create a new View object.
        // The MainActivity provides the data (e.g., fruit names and images) through the constructor of the adapter.
        // The adapter binds each data item (like text and image) to the respective View in the list.
        // Finally, each View created by the adapter is displayed within a ListView.
        view = inflater.inflate(R.layout.activity_my_list_view, null);
        TextView textView = (TextView) view.findViewById(R.id.listViewItemText);
        ImageView imageView = (ImageView) view.findViewById(R.id.listViewItemImage);
        textView.setText(fruitList[i]);
        imageView.setImageResource(fruitImages[i]);
        return view;
    }
}
