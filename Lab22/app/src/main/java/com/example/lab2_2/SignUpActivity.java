package com.example.lab2_2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText user_username;
    private EditText user_password;
    private EditText confirmPass;
    private TextView accountExist;
    private Button btnSignUp;

    private final String REQUIRE = "Required";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        user_username = findViewById(R.id.signUp_username);
        user_password = findViewById(R.id.signUp_password);
        confirmPass = findViewById(R.id.signUp_confirmPass);
        accountExist = findViewById(R.id.accountExist);
        btnSignUp = findViewById(R.id.btnSignUp);

        accountExist.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    private  boolean checkInput(){
        if (TextUtils.isEmpty(user_username.getText().toString())){
            user_username.setError(REQUIRE);
            return false;
        }

        if (TextUtils.isEmpty(user_password.getText().toString())){
            user_username.setError(REQUIRE);
            return false;
        }

        if (TextUtils.isEmpty(user_password.getText().toString())){
            user_username.setError(REQUIRE);
            return false;
        }

        if (!TextUtils.equals(user_password.getText().toString(), confirmPass.getText().toString())) {
            Toast.makeText(this, "Password aren't matched", Toast.LENGTH_LONG).show();
            return false;
        }

        Log.i("UserSignUp","A user has successfully created an account.");
        return true;
    }

    private void signUp(){
        if (!checkInput()){
            return;
        }
    }

    private void signInForm(){
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btnSignUp){
            signUp();
        } else if (id == R.id.accountExist){
            signInForm();
        }
    }
}