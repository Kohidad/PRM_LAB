package com.example.lab5_2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FruitDescriptionFullView extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fruit_full_description);

        String title = getIntent().getStringExtra("TITLE");
        String description = getIntent().getStringExtra("DESCRIPTION");
        int image = getIntent().getIntExtra("IMAGE",0);

        TextView titleTV = findViewById(R.id.fruitFullDescription_title);
        TextView descriptionTV = findViewById(R.id.fruitFullDescription_description);
        ImageView imageIV = findViewById(R.id.fruitFullDescription_image);
        Button edit = findViewById(R.id.editBtn);
        Button delete = findViewById(R.id.deleteBtn);

        titleTV.setText(title);
        descriptionTV.setText(description);
        imageIV.setImageResource(image);
        
        // Edit description or Name
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


}
