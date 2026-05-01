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

import com.example.maskateapp.repository.*;
import com.example.maskateapp.model.Produto;

public class AddActivity extends AppCompatActivity {

    EditText nome, codigo, quantidade;
    ImageView imagem;
    Bitmap foto;
    Uri imageUri;
    ImagemRepository imgRepo;
    ProdutoRepository prodRepo;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.add_activity);

        nome = findViewById(R.id.nome);
        codigo = findViewById(R.id.codigo);
        quantidade = findViewById(R.id.quantidade);
        imagem = findViewById(R.id.img);

        imgRepo = new ImagemRepository();
        prodRepo = new ProdutoRepository();

        findViewById(R.id.btnCamera).setOnClickListener(v -> {

            if (checkSelfPermission(android.Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {

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

            if(nome.getText().toString().isEmpty() ||
                    codigo.getText().toString().isEmpty() ||
                    quantidade.getText().toString().isEmpty() ||
                    foto == null){

                Toast.makeText(this,"Preencha tudo!",Toast.LENGTH_SHORT).show();
                return;
            }

            // dentro do btnSalvar (substitua só a parte do salvar)

            new Thread(() -> {
                String url = imgRepo.upload(foto);

                if(url != null){
                    Produto p = new Produto(
                            nome.getText().toString(),
                            codigo.getText().toString(),
                            Integer.parseInt(quantidade.getText().toString()),
                            url
                    );

                    runOnUiThread(() -> {
                        prodRepo.salvar(p);

                        nome.setText("");
                        codigo.setText("");
                        quantidade.setText("");
                        imagem.setImageResource(0);
                        foto = null;

                        Toast.makeText(this,"Salvo!",Toast.LENGTH_SHORT).show();
                    });
                }
            }).start();
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("foto", foto);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        foto = savedInstanceState.getParcelable("foto");

        if(foto != null){
            imagem.setImageBitmap(foto);
        }
    }

    @Override
    protected void onActivityResult(int r, int c, Intent data) {
        super.onActivityResult(r, c, data);

        if(r == 1 && c == RESULT_OK){
            imagem.setImageURI(imageUri);

            try {
                foto = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}