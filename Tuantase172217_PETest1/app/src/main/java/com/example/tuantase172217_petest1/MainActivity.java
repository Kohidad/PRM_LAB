package com.example.tuantase172217_petest1;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView tvDate1, tvDate2, tvResult;
    private Button btnCalculateDays;
    private String date1, date2;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDate1 = findViewById(R.id.tvDate1);
        tvDate2 = findViewById(R.id.tvDate2);
        tvResult = findViewById(R.id.tvResult);
        btnCalculateDays = findViewById(R.id.btnCalculateDays);

        tvDate1.setOnClickListener(v -> showDatePickerDialog(1));
        tvDate2.setOnClickListener(v -> showDatePickerDialog(2));

        btnCalculateDays.setOnClickListener(v -> calculateDaysBetweenDates());
    }

    private void showDatePickerDialog(int dateType) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
            if (dateType == 1) {
                date1 = selectedDate;
                tvDate1.setText("Date 1: " + selectedDate);
            } else {
                date2 = selectedDate;
                tvDate2.setText("Date 2: " + selectedDate);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void calculateDaysBetweenDates() {
        if (date1 == null || date2 == null) {
            tvResult.setText("Please select both dates.");
            return;
        }

        try {
            Date startDate = dateFormat.parse(date1);
            Date endDate = dateFormat.parse(date2);

            if (startDate != null && endDate != null) {
                long differenceInMillis = Math.abs(endDate.getTime() - startDate.getTime());
                long differenceInDays = differenceInMillis / (1000 * 60 * 60 * 24);
                tvResult.setText("Days between: " + differenceInDays);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            tvResult.setText("Error parsing dates.");
        }
    }
}