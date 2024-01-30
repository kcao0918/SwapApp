package com.example.swapapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.UUID;

public class InfoActivity extends AppCompatActivity {

    Button button;
    ImageView imageView;
    FirebaseStorage storage;
    Uri imageUri;
    EditText name;
    EditText desc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        button = findViewById(R.id.button);
        imageView = findViewById(R.id.imageView);
        storage = FirebaseStorage.getInstance();
        name = findViewById(R.id.name);
        desc = findViewById(R.id.desc);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetContent.launch("image/*");

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
                StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                System.out.println(storageRef.child("images/1ba70124-d42a-4877-958b-c33b206e2a66.jpeg"));
                HashMap<String, Object> hs =
            }

        });

}

    private void uploadImage() {
        if (imageUri != null) {
            StorageReference reference = storage.getReference().child("images/" + UUID.randomUUID().toString());
            reference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(InfoActivity.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(InfoActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if (result!=null) {
                        imageView.setImageURI(result);
                        imageUri = result;
                    }
                }
            });
}


