package com.example.lab5_2;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FruitDescriptionFullView extends AppCompatActivity{

    MyApplication myApplication = (MyApplication) this.getApplication();
    ArrayList<Fruit> fruitList = myApplication.getFruitList();
    Fruit fruit;

    Dialog dialog;
    Button btnDialogClose, btnDialogConfirm;
    String title;
    String description;
    int image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fruit_full_description);

        int position = getIntent().getIntExtra("POSITION", -1);
        if (fruitList != null && position < fruitList.size()){
            fruit = fruitList.get(position);
        }

        title = getIntent().getStringExtra("TITLE");
        description = getIntent().getStringExtra("DESCRIPTION");
        image = getIntent().getIntExtra("IMAGE", 0);

        TextView titleTV = findViewById(R.id.fruitFullDescription_title);
        TextView descriptionTV = findViewById(R.id.fruitFullDescription_description);
        ImageView imageIV = findViewById(R.id.fruitFullDescription_image);
        Button edit = findViewById(R.id.editBtn);
        Button delete = findViewById(R.id.deleteBtn);

        titleTV.setText(title);
        descriptionTV.setText(description);
        imageIV.setImageResource(image);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_box);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_bg));
        dialog.setCancelable(false);

        Button cancel = dialog.findViewById(R.id.dialog_cancelBtn);
        Button confirm = dialog.findViewById(R.id.dialog_confirmBtn);

        // Edit description or title
        edit.setOnClickListener(view -> dialog.show());

        delete.setOnClickListener(view -> {
            if (position != -1) {
                fruitList.remove(fruit);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        confirm.setOnClickListener(view -> {
            if (position != -1) {
                EditText editTitle = dialog.findViewById(R.id.editTitleBox);
                EditText editDescription = dialog.findViewById(R.id.editDescriptionBox);

                String newTitle = editTitle.getText().toString().isEmpty() ? title : editTitle.getText().toString();
                String newDescription = editDescription.getText().toString().isEmpty() ? description : editDescription.getText().toString();

                fruit.setTitle(newTitle);
                fruit.setDescription(newDescription);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        cancel.setOnClickListener(view -> {
            dialog.dismiss();
        });
    }
}
