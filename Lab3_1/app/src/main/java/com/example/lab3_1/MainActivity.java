package com.example.lab3_1;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> myItemsList;
    LinearLayout mainLayout;
    ListView listView;
    MyCustomAdapter customerAdapter;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout = findViewById(R.id.main);
        listView = (ListView) findViewById(R.id.myListView);
        myItemsList = new ArrayList<>(Arrays.asList("Android", "ASP.NET", "Unity", "Java", "C#"));
        // Changed the getApplicationContext() to this
        customerAdapter = new MyCustomAdapter(this,myItemsList);
        listView.setAdapter(customerAdapter);

        // Add button
        addButton = findViewById(R.id.addItem);

        // Anonymous inner class
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myItemsList.add("Something");
                // Next time implement the RecycleView (this one automatically update)
                // We have this because adapter itself doesn't automatically update.
                customerAdapter.notifyDataSetChanged();
            }
        });

        // Set a Listener for listView
        // Create a anonymous object that listen on Adapter (within list) clicking action.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("ACTION", "A user clicked " + myItemsList.get(i));
                createPopUpWindow(i);
            }
        });

    }


    private void createPopUpWindow(int position){
        // getSystemService(LAYOUT_INFLATER_SERVICE) is a method that retrieves a system-level service.
        // LAYOUT_INFLATER_SERVICE is a constant that represents the system's LayoutInflater service.
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popUp = layoutInflater.inflate(R.layout.popwindow, null);
        EditText updateTitleInput;
        Button closeBtn;
        Button applyBtn;
        Button deleteBtn;

        updateTitleInput = popUp.findViewById(R.id.updateIBoxPopUp);
        closeBtn = popUp.findViewById(R.id.closePopUpBtn);
        applyBtn = popUp.findViewById(R.id.applyChangesBtn);
        deleteBtn = popUp.findViewById(R.id.deleteThisBtn);

        // Pre-fill the EditText with the current item title
        updateTitleInput.setText(myItemsList.get(position));

        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        boolean focusable = true;
        // This is a widget from android and will implement an animation, as well as adjusting pop in/out animation.
        // We add the parameter we got from inflater, assign popUp class.
        PopupWindow popupWindow = new PopupWindow(popUp,width,height,focusable);

        // When the main layout is ready, this will run.
        mainLayout.post(new Runnable() {
            @Override
            public void run() {
                popupWindow.showAtLocation(mainLayout, Gravity.CENTER,0,0);
            }
        });

        // Close popUp
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        // ApplyChanges popUp
        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newTitle = updateTitleInput.getText().toString();
                if (!newTitle.isEmpty()){
                    myItemsList.set(position, newTitle);
                    customerAdapter.notifyDataSetChanged();
                    popupWindow.dismiss();
                }
            }
        });

        // DeleteThis element
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!myItemsList.get(position).isEmpty()){
                    myItemsList.remove(position);
                    customerAdapter.notifyDataSetChanged();
                    popupWindow.dismiss();
                } else {
                    Log.i("ERROR - MISSING ELEMENT", "Missing element at: " + myItemsList.get(position));
                    Toast.makeText(MainActivity.this, "Something not right", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}