package com.example.maskateapp;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.maskateapp.model.Produto;
import com.example.maskateapp.repository.ImagemRepository;
import com.example.maskateapp.repository.ProdutoRepository;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    EditText nome, codigo, codigoBarras, quantidade;
    ImageView img1, img2;
    Uri imageUri;

    ImagemRepository imgRepo;
    ProdutoRepository prodRepo;

    List<Bitmap> fotos = new ArrayList<>();
    List<String> listaImagens = new ArrayList<>();

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.add_activity);

        nome = findViewById(R.id.nome);
        codigo = findViewById(R.id.codigo);
        codigoBarras = findViewById(R.id.codigoBarras);
        quantidade = findViewById(R.id.quantidade);

        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);

        imgRepo = new ImagemRepository();
        prodRepo = new ProdutoRepository();

        // 🔽 REMOVER IMAGENS AO CLICAR
        img1.setOnClickListener(v -> {
            if (fotos.size() >= 1) {
                fotos.remove(0);
                img1.setImageResource(0);

                if (fotos.size() >= 1) img1.setImageBitmap(fotos.get(0));
                if (fotos.size() >= 2) img2.setImageBitmap(fotos.get(1));
                else img2.setImageResource(0);
            }
        });

        img2.setOnClickListener(v -> {
            if (fotos.size() >= 2) {
                fotos.remove(1);
                img2.setImageResource(0);
            }
        });
        // 🔼 FIM

        // 🔽 ABRIR SCANNER
        findViewById(R.id.btnScanner).setOnClickListener(v -> {
            Intent i = new Intent(AddActivity.this, ScannerActivity.class);
            startActivityForResult(i, 200);
        });
        // 🔼 FIM

        findViewById(R.id.btnCamera).setOnClickListener(v -> {

            if (checkSelfPermission(android.Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {

                if (fotos.size() >= 2) {
                    Toast.makeText(this, "Máximo 2 imagens", Toast.LENGTH_SHORT).show();
                    return;
                }

                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "foto");
                imageUri = getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(i, 1);

            } else {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA}, 100);
            }
        });

        findViewById(R.id.btnSalvar).setOnClickListener(v -> {

            if (nome.getText().toString().isEmpty() ||
                    codigo.getText().toString().isEmpty() ||
                    codigoBarras.getText().toString().isEmpty() ||
                    quantidade.getText().toString().isEmpty() ||
                    fotos.isEmpty()) {

                Toast.makeText(this, "Preencha tudo!", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {

                listaImagens.clear();

                for (Bitmap btm : fotos) {
                    String url = imgRepo.upload(btm);
                    if (url != null) listaImagens.add(url);
                }

                Produto p = new Produto(
                        nome.getText().toString(),
                        codigo.getText().toString(),
                        codigoBarras.getText().toString(),
                        Integer.parseInt(quantidade.getText().toString()),
                        new ArrayList<>(listaImagens)
                );

                runOnUiThread(() -> {
                    prodRepo.salvar(p);

                    nome.setText("");
                    codigo.setText("");
                    codigoBarras.setText("");
                    quantidade.setText("");
                    img1.setImageResource(0);
                    img2.setImageResource(0);

                    fotos.clear();
                    listaImagens.clear();

                    Toast.makeText(this, "Salvo!", Toast.LENGTH_SHORT).show();
                });

            }).start();
        });
    }

    @Override
    protected void onActivityResult(int r, int c, Intent data) {
        super.onActivityResult(r, c, data);

        // 📸 CAMERA
        if (r == 1 && c == RESULT_OK) {
            try {
                Bitmap b = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                fotos.add(b);

                if (fotos.size() == 1) img1.setImageBitmap(fotos.get(0));
                else if (fotos.size() == 2) img2.setImageBitmap(fotos.get(1));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 🔍 SCANNER
        if (r == 200 && c == RESULT_OK && data != null) {
            String cod = data.getStringExtra("codigoBarras");
            codigoBarras.setText(cod);
        }
    }
}