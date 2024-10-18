package com.example.lab10;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class EditPersonActivity extends AppCompatActivity {

    private EditText editFirstName;
    private EditText editLastName;

    private Button btnSave;
    private int mPersonId;

    private Intent intent;
    private AppDatabase mDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_person_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
        mDb = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app-database").build();

        intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.UPDATE_Person_Id)) {
            btnSave.setText("Update");

            mPersonId = intent.getIntExtra(Constants.UPDATE_Person_Id, -1);

            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    Person person = mDb.personDAO().loadPersonById(mPersonId);
                    populateUI(person);
                }
            });
        }
    }

    private void populateUI(Person person) {
        editFirstName.setText(person.getFirstName());
        editLastName.setText(person.getLastName());
    }

    private void initViews() {
        editFirstName = findViewById(R.id.editFirstName);
        editLastName = findViewById(R.id.editLastName);

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(view -> {
            onSaveButtonClicked();
        });
    }

    private void onSaveButtonClicked() {
        final Person person = new Person(
                editFirstName.getText().toString(),
                editLastName.getText().toString());

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (!intent.hasExtra(Constants.UPDATE_Person_Id)){
                    mDb.personDAO().insert(person);
                } else {
                    person.setUid(mPersonId);
                    mDb.personDAO().update(person);
                }
                finish();
            }
        });
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
