package com.example.lab2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //declare values
    EditText minInput, maxInput;
    TextView result;
    Button btnRandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //activity_main is just an integer value (represent Res folder) can be found in R.java file.
        //it just help the android system to find the xml file in Res folder.

        minInput = findViewById(R.id.minInput);
        maxInput = findViewById(R.id.maxInput);
        result = findViewById(R.id.yourRandom);
        btnRandom = findViewById(R.id.btnRandom);

        Random random = new Random();

        //doesn't need to create new object because View.OnClickListener itself only have 1 method.
        //hence use lambda expression.
        btnRandom.setOnClickListener(view -> {
            try {
                int min = Integer.parseInt(minInput.getText().toString());
                int max = Integer.parseInt(maxInput.getText().toString());
                if (min > max){
                    minInput.setError("Minimum number cannot be lower!");
                } else if (min == max) {
                    minInput.setError("Min & max are equal!");
                    maxInput.setError("Min & max are equal!");
                } else {
                    int ans = random.nextInt((max - min) + 1) + min; //max-min + 1 to create the range.
                    result.setText(Integer.toString(ans));
                }
            } catch(NumberFormatException e){
                result.setText("");
                result.setError(null);
            }
        });

    }
}