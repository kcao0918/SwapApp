package com.example.swapapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class SwapActivity extends AppCompatActivity {

    ImageView imageView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swap);
        imageView3 = findViewById(R.id.imageView3);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://swapapp-6341b.appspot.com/images/406c789d-ffc5-4523-a9f2-2e09068d0191");

        GlideApp.with(this /* context */)
                .load(storageRef)
                .into(imageView3);
    }

}