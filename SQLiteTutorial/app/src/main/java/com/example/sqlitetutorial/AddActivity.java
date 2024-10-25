package com.example.sqlitetutorial;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    EditText title_input, author_input, pages_input;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title_input = findViewById(R.id.title_input_update);
        author_input = findViewById(R.id.author_input_update);
        pages_input = findViewById(R.id.pages_input_update);
        add_button = findViewById(R.id.add_button);

        add_button.setOnClickListener(view -> {
            MyDatabaseHelper myDB = new MyDatabaseHelper(this);
            myDB.addBook(title_input.getText().toString().trim(),
                    author_input.getText().toString().trim(),
                    Integer.valueOf(pages_input.getText().toString().trim()));
            finish();
        });
    }
}