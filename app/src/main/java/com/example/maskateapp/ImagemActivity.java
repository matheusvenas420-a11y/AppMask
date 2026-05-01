package com.example.maskateapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.chrisbanes.photoview.PhotoView;
import com.bumptech.glide.Glide;

public class ImagemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagem);

        PhotoView img = findViewById(R.id.imgFull);

        String url = getIntent().getStringExtra("img");

        Glide.with(this)
                .load(url)
                .into(img);
    }
}