package com.example.maskateapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetalheActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        TextView nome = findViewById(R.id.txtNome);
        TextView codigo = findViewById(R.id.txtCodigo);
        ImageView img = findViewById(R.id.imgProduto);

        nome.setText(getIntent().getStringExtra("nome"));
        codigo.setText(getIntent().getStringExtra("codigo"));

        String url = getIntent().getStringExtra("img");

        Log.d("IMG_URL", url);

        if (url != null && url.startsWith("http://")) {
            url = url.replace("http://", "https://");
        }

        String finalUrl = url;

        Glide.with(this)
                .load(finalUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_delete)
                .into(img);

        img.setOnClickListener(v -> {
            Intent i = new Intent(DetalheActivity.this, ImagemActivity.class);
            i.putExtra("img", finalUrl);
            startActivity(i);
        });
    }
}