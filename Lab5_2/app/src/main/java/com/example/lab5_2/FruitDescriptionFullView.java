package com.example.lab5_2;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FruitDescriptionFullView extends AppCompatActivity {

    ActivityResultLauncher<Intent> resultLauncher;

    MyApplication myApplication = (MyApplication) this.getApplication();
    ArrayList<Fruit> fruitList = myApplication.getFruitList();
    Fruit fruit;
    private Uri dialogImageUri;

    Dialog dialog;

    // Intent info
    String title;
    String description;
    String imageUri;

    // Smol dialog box
    Button cancel;
    Button confirm;
    Button editImage;
    ImageView fruitImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fruit_full_description);

        int position = getIntent().getIntExtra("POSITION", -1);
        if (fruitList != null && position < fruitList.size()) {
            fruit = fruitList.get(position);
        }

        title = getIntent().getStringExtra("TITLE");
        description = getIntent().getStringExtra("DESCRIPTION");
        imageUri = getIntent().getStringExtra("IMAGEURI");

        // Beeg screen view/info
        TextView titleTV = findViewById(R.id.fruitFullDescription_title);
        TextView descriptionTV = findViewById(R.id.fruitFullDescription_description);
        ImageView imageIV = findViewById(R.id.fruitFullDescription_image);
        Button edit = findViewById(R.id.editBtn);
        Button delete = findViewById(R.id.deleteBtn);

        titleTV.setText(title);
        descriptionTV.setText(description);
        imageIV.setImageURI(Uri.parse(imageUri));

        // Custom dialog
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_box);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_bg));
        dialog.setCancelable(false);

        cancel = dialog.findViewById(R.id.dialog_cancelBtn);
        confirm = dialog.findViewById(R.id.dialog_confirmBtn);
        editImage = dialog.findViewById(R.id.dialog_addImageBtn);
        fruitImage = dialog.findViewById(R.id.dialog_fruitImage);

        // Getting data from action go brrrr
        registerResult();

        // Edit description or title
        edit.setOnClickListener(view -> dialog.show());

        editImage.setOnClickListener(view -> {
            openImage();
        });

        delete.setOnClickListener(view -> {
            if (position != -1) {
                fruitList.remove(fruit);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        confirm.setOnClickListener(view -> {
            if (position != -1) {
                EditText editTitle = dialog.findViewById(R.id.editTitleBox);
                EditText editDescription = dialog.findViewById(R.id.editDescriptionBox);

                String newTitle = editTitle.getText().toString().isEmpty() ? title : editTitle.getText().toString();
                String newDescription = editDescription.getText().toString().isEmpty() ? description : editDescription.getText().toString();
                String newImageUri = dialogImageUri.toString().isEmpty() ? imageUri : dialogImageUri.toString();

                fruit.setTitle(newTitle);
                fruit.setDescription(newDescription);
                fruit.setImage(newImageUri);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        cancel.setOnClickListener(view -> {
            dialog.dismiss();
        });

    }

    private void openImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        resultLauncher.launch(intent);
    }

    private void registerResult() {
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    Uri uri = data.getData();
                    if (uri != null) {
                        fruitImage.setImageURI(uri);
                        dialogImageUri = uri;
                    }
                }
            }  else {
                Toast.makeText(this, "Nothing was selected", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
