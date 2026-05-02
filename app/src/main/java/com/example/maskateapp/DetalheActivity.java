package com.example.maskateapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.maskateapp.adapter.ImagemAdapter;

import java.util.ArrayList;

public class DetalheActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        TextView nome = findViewById(R.id.txtNome);
        TextView codigo = findViewById(R.id.txtCodigo);
        TextView codigoBarras = findViewById(R.id.txtCodigoBarras);

        ViewPager2 viewPager = findViewById(R.id.viewPager); // 🔥 AQUI

        nome.setText("Nome: " + getIntent().getStringExtra("nome"));
        codigo.setText("Código: " + getIntent().getStringExtra("codigo"));
        codigoBarras.setText("Código de Barras: " +
                getIntent().getStringExtra("codigoBarras"));

        ArrayList<String> urls = getIntent().getStringArrayListExtra("imgs");

        if (urls != null && !urls.isEmpty()) {
            viewPager.setAdapter(new ImagemAdapter(urls));
        }
    }
}