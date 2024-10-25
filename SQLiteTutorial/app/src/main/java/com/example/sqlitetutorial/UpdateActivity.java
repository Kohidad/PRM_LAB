package com.example.sqlitetutorial;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {

    MyDatabaseHelper myDB = new MyDatabaseHelper(this);

    EditText title_input, author_input, pages_input;
    Button update_btn, delete_btn;

    String id, title, author, pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title_input = findViewById(R.id.title_input_update);
        author_input = findViewById(R.id.author_input_update);
        pages_input = findViewById(R.id.pages_input_update);
        update_btn = findViewById(R.id.update_button);
        delete_btn = findViewById(R.id.delete_button);

        getAndSetIntentData();

        update_btn.setOnClickListener(view -> {
            title = title_input.getText().toString().trim();
            author = author_input.getText().toString().trim();
            pages = pages_input.getText().toString().trim();

            myDB.updateData(id, title, author, Integer.parseInt(pages));
            finish();
        });

        delete_btn.setOnClickListener(view -> {
            confirmDialog();
        });
    }

    private void getAndSetIntentData() {
        if (getIntent().hasExtra("ID") && getIntent().hasExtra("TITLE")
                && getIntent().hasExtra("AUTHOR") && getIntent().hasExtra("PAGES")) {
            // Get
            id = getIntent().getStringExtra("ID");
            title = getIntent().getStringExtra("TITLE");
            author = getIntent().getStringExtra("AUTHOR");
            pages = getIntent().getStringExtra("PAGES");

            // Set
            title_input.setText(title);
            author_input.setText(author);
            pages_input.setText(pages);
        } else {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + " ?");
        builder.setMessage("Are you sure you wanted to delete " + title + " ?");
        builder.setPositiveButton("Yes", (dialog, i) -> {
            myDB.deleteData(id);
            finish();
        });
        builder.setNegativeButton("No", (dialog, which) -> {

        });
        builder.create().show();
    }
}