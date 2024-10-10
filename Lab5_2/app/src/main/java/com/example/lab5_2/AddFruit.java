package com.example.lab5_2;

import static android.app.ProgressDialog.show;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddFruit extends AppCompatActivity {

    ActivityResultLauncher<Intent> resultLauncher;
    Fruit newFruit;

    EditText titleEditBox, descriptionEditBox;
    ImageView fruitImage;
    Button addImageBtn, addConfirmBtn;

    private boolean isImageSelected = false;
    private Uri imageUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fruit_add);

        titleEditBox = findViewById(R.id.add_title_box);
        descriptionEditBox = findViewById(R.id.add_description_box);
        fruitImage = findViewById(R.id.fruitImage);
        addImageBtn = findViewById(R.id.add_imageBtn);
        addConfirmBtn = findViewById(R.id.add_confirm);

        registerResult();
        addImageBtn.setOnClickListener(view -> openImage());

        addConfirmBtn.setOnClickListener(view -> {

            Log.i("STATUS", "Title: " + titleEditBox.getText().toString().isEmpty());
            Log.i("STATUS", "Desc: " + descriptionEditBox.getText().toString().isEmpty());
            Log.i("STATUS", "Image: " + isImageSelected);

                titleEditBox.setError(null);
                descriptionEditBox.setError(null);
                boolean isError = false;

                if (titleEditBox.getText().toString().isEmpty()) {
                    titleEditBox.setError("This field cannot be blank");
                    isError = true;
                }

                if (descriptionEditBox.getText().toString().isEmpty()) {
                    descriptionEditBox.setError("This field cannot be blank");
                    isError = true;
                }

                if (!isImageSelected) {
                    Toast.makeText(this, "Image cannot be blank", Toast.LENGTH_SHORT).show();
                    isError = true;
                }

                if (!isError) {
                    newFruit = new Fruit(titleEditBox.getText().toString(),
                            descriptionEditBox.getText().toString(),
                            imageUri.toString());
                    ArrayList<Fruit> myFruitList = MyApplication.getFruitList();
                    myFruitList.add(newFruit);
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("NEW_FRUIT_ADDED", true);
                    startActivity(intent);
                    finish();
                }

//            } catch (Exception e) {
//                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
//            }

        });
    }

    private void openImage() {
        // Basically we're picking the place to "launch" the action.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        resultLauncher.launch(intent);
    }

    private void registerResult() {
        // Takes resultContract and ResultCall back (basically, I&O) then get resultLauncher which is something you need
        // Which is something you need for launching activities.
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            // If user selected something => OK
            if (result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    Uri uri = data.getData();
                    if (uri != null) {
                        fruitImage.setImageURI(uri);
                        imageUri = uri;
                        isImageSelected = true;
                    }
                }
            } else {
                Toast.makeText(this, "Nothing was selected", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
