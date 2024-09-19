package com.example.lab2_2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText user_username;
    private EditText user_password;
    private TextView noAccountYet;
    private Button btnSignIn;

    private final String REQUIRE = "Required";
    private final String USERBASE = "userbase.txt";

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

    public boolean loginAccount(String fileName, String username, String password) {
        File path = this.getFilesDir();
        File readFile = new File(path, fileName);
        byte[] content = new byte[(int) readFile.length()];

        try {
            FileInputStream stream = new FileInputStream(readFile);
            stream.read(content);
            stream.close();

            String fileContent = new String(content);

            String userInfo = username + ":" + password;

            if (!fileContent.contains(userInfo)) {
                return false;
            }
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "File not found!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    private boolean checkInput() {
        if (TextUtils.isEmpty(user_username.getText().toString())) {
            user_username.setError(REQUIRE);
            return false;
        }

        if (TextUtils.isEmpty(user_password.getText().toString())) {
            user_password.setError(REQUIRE);
            return false;
        }

        if (!loginAccount(USERBASE,user_username.getText().toString(),user_password.getText().toString())){
            Toast.makeText(this, "Either username/password is wrong, else create account.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void signIn() {
        if (!checkInput()) {
            return;
        }

        //Intent assign the path of current class (SignInActivity) to MainActivity class if account exist.
        Intent intent = new Intent(this, MainActivity.class);
        //Passing the username value to MainActivity
        intent.putExtra("USERNAME",user_username.getText().toString());
        startActivity(intent);
        finish();
    }

    private void signUpForm() {
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
