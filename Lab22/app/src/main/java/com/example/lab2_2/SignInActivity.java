package com.example.lab2_2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText user_username;
    private EditText user_password;
    private TextView noAccountYet;
    private Button btnSignIn;

    private final String REQUIRE = "Required";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        user_username = findViewById(R.id.signIn_username);
        user_password = findViewById(R.id.signIn_password);
        noAccountYet = findViewById(R.id.accountNonExist);
        btnSignIn = findViewById(R.id.btnSignIn);

        noAccountYet.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
    }

    private boolean checkInput(){
        if (TextUtils.isEmpty(user_username.getText().toString())){
            user_username.setError(REQUIRE);
            return false;
        }

        if (TextUtils.isEmpty(user_password.getText().toString())){
            user_password.setError(REQUIRE);
            return false;
        }

        return true;
    }

    private void signIn(){
        if(!checkInput()){
            return;
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void signUpForm(){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btnSignIn) {
            signIn();
        } else if (id == R.id.accountNonExist) {
            signUpForm();
        }
    }

}
