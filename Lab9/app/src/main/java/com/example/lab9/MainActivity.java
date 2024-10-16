package com.example.lab9;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.add_congviec, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuAdd){
            DialogThem();
        }
        return super.onOptionsItemSelected(item);
    }

    private void DialogThem(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_them_cong_viec);

        EditText editTen = dialog.findViewById(R.id.editTextTenCV);
        Button btnThem = dialog.findViewById(R.id.buttonThem);
        Button btnHuy = dialog.findViewById(R.id.buttonHuy);

        btnThem.setOnClickListener(view -> {
            String tenCV = editTen.getText().toString();
            Toast.makeText(this, "tenCV: " + tenCV, Toast.LENGTH_SHORT).show();
            if (tenCV.isEmpty()){
                Toast.makeText(this, "Vui lòng nhập tên công việc", Toast.LENGTH_SHORT).show();
            } else {
                database.queryData("Insert into CongViec values(null, '" + tenCV + "')");
                Toast.makeText(this, "Đã thêm " + tenCV, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                GetDataCongViec();
            }
        });

        btnHuy.setOnClickListener(view -> {
            dialog.dismiss();
        });

        dialog.show();
    }

    private void GetDataCongViec() {
        Cursor dataCongViec = database.getData("Select * from CongViec");
        arrayCongViec.clear();

        while (dataCongViec.moveToNext()){
            String ten = dataCongViec.getString(1);
//            Toast.makeText(this, ten, Toast.LENGTH_SHORT).show();
            int id = dataCongViec.getInt(0);
            arrayCongViec.add(new CongViec(id,ten));
        }
        adapter.notifyDataSetChanged();
    }

    public void DialogSuaCongViec(String ten, int id){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_sua_cong_viec);

        EditText editTenCV = dialog.findViewById(R.id.editTextTenCV);
        Button btnXacNhan = dialog.findViewById(R.id.buttonXacNhan);
        Button btnHuy = dialog.findViewById(R.id.buttonHuy);

        editTenCV.setText(ten);
        btnXacNhan.setOnClickListener(view -> {
            String tenMoi = editTenCV.getText().toString().trim();
            database.queryData("UPDATE CongViec SET TenCV ='" + tenMoi +
                    "' WHERE id = '" + id + "'");
            Toast.makeText(this, "Đã cập nhât: " + ten + " thành " + tenMoi, Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            GetDataCongViec();
        });

        btnHuy.setOnClickListener(view -> {
            dialog.dismiss();
        });

        dialog.show();
    }

    public void DialogXoaCongViec(String tenCV,int Id){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có muốn xóa công việc " + tenCV + " không ? ");
        dialogXoa.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                database.queryData("DELETE FROM CongViec WHERE Id = '" + Id + "' ");
                Toast.makeText(MainActivity.this, "Đã xóa", Toast.LENGTH_SHORT).show();
                GetDataCongViec();
            }
        });
        dialogXoa.setNegativeButton("No", (dialogInterface, i) -> {

        });
        dialogXoa.show();
    }

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

        GetDataCongViec();
    }
}