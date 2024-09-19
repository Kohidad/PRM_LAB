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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText user_username;
    private EditText user_password;
    private EditText confirmPass;
    private TextView accountExist;
    private Button btnSignUp;

    private final String REQUIRE = "Required";
    private final String USERBASE = "userbase.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        user_username = findViewById(R.id.signUp_username);
        user_password = findViewById(R.id.signUp_password);
        confirmPass = findViewById(R.id.signUp_confirmPass);
        accountExist = findViewById(R.id.accountExist);
        btnSignUp = findViewById(R.id.btnSignUp);

        //This means pointing to the current class (SignUpActivity)
        accountExist.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    public void writeToFile(String fileName, String content) {
        //Return the context of the entire application. Not current activity (diff from getContext())
        //This means pointing to the current class (SignUpActivity)
        File path = this.getFilesDir();
        try {
            //append to avoid overwrite
            FileOutputStream writer = new FileOutputStream(new File(path, fileName), true);
            writer.write((content + "\n").getBytes());
            writer.close();
            Toast.makeText(this, "Wrote to file: " + fileName, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean findAccount(String fileName, String username) {
        File path = this.getFilesDir();
        File readFile = new File(path, fileName);
        //Allocate memory based on file size.
        byte[] content = new byte[(int) readFile.length()];

        //Check if file exist, else write regardless.
        if (!readFile.exists()){
            return true;
        }

        try {
            FileInputStream stream = new FileInputStream(readFile);
            stream.read(content);
            stream.close();

            //Turn the byte[] into a string
            String fileContent = new String(content);

            String userInfo = username + ":";

            if (fileContent.contains(userInfo)){
                Log.i("UserDuplicate","User creation duplicated found, denied entry user: " + username);
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
            user_username.setError(REQUIRE);
            return false;
        }

        if (TextUtils.isEmpty(user_password.getText().toString())) {
            user_username.setError(REQUIRE);
            return false;
        }

        if (!TextUtils.equals(user_password.getText().toString(), confirmPass.getText().toString())) {
            Toast.makeText(this, "Password aren't matched", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!findAccount(USERBASE,user_username.getText().toString())){
            Toast.makeText(this, "Username already exists!", Toast.LENGTH_SHORT).show();
            return false;
        }

        Log.i("UserSignUp", "User " + user_username.getText().toString() + " has successfully created an account.");
        return true;
    }

    private void signUp() {
        if (!checkInput()) {
            return;
        }

        String userAccountInfo = user_username.getText().toString() + ":" + user_password.getText().toString();
        writeToFile(USERBASE, userAccountInfo);
    }

    private void signInForm() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btnSignUp) {
            signUp();
        } else if (id == R.id.accountExist) {
            signInForm();
        }
    }
}