package com.example.lab9;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Database database;
    ListView lvCongViec;
    ArrayList<CongViec> arrayCongViec;
    CongViecAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvCongViec = findViewById(R.id.listviewCongViec);
        arrayCongViec = new ArrayList<>();
        adapter = new CongViecAdapter(this, R.layout.dong_cong_viec, arrayCongViec);
        lvCongViec.setAdapter(adapter);

        database = new Database(this, "GhiChu.sqlite", null, 1);

        database.queryData("Create table if not exists CongViec(id Integer Primary Key Autoincrement," +
                "TenCV nv nvarchar(200))");

        // Insert Data
//        database.queryData("Insert into CongViec values(null, 'Project Android')");
//        database.queryData("Insert into CongViec values(null, 'Design app')");

        Cursor dataCongViec = database.getData("Select * from CongViec");
        while (dataCongViec.moveToNext()){
            String ten = dataCongViec.getString(1);
//            Toast.makeText(this, ten, Toast.LENGTH_SHORT).show();
            int id = dataCongViec.getInt(0);
            arrayCongViec.add(new CongViec(id,ten));
        }
        adapter.notifyDataSetChanged();
    }
}