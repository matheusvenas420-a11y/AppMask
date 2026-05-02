package com.example.maskateapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maskateapp.adapter.ProdutoAdapter;
import com.example.maskateapp.model.Produto;
import com.example.maskateapp.repository.ProdutoRepository;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private ProdutoAdapter adapter;
    private List<Produto> lista = new ArrayList<>();
    private ProdutoRepository repo = new ProdutoRepository();
    private EditText inputBusca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);

        recycler = findViewById(R.id.recycler);
        inputBusca = findViewById(R.id.inputBusca);

        recycler.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ProdutoAdapter(lista, p -> {

            Intent i = new Intent(ListActivity.this, DetalheActivity.class);

            i.putExtra("nome", p.getNome());
            i.putExtra("codigo", p.getCodigo());
            i.putExtra("qtd", p.getQuantidade());
            i.putExtra("codigoBarras", p.getCodigoBarras());

            // 🔥 PROTEÇÃO contra null e lista vazia
            if (p.getImgurl() != null && !p.getImgurl().isEmpty()) {
                i.putStringArrayListExtra("imgs", new ArrayList<>(p.getImgurl()));
            }

            startActivity(i);
        });

        recycler.setAdapter(adapter);

        // 🔥 FIREBASE CORRIGIDO
        repo.listar(query -> {

            List<Produto> novaLista = new ArrayList<>();

            for (DocumentSnapshot doc : query.getDocuments()) {
                try {
                    Produto p = doc.toObject(Produto.class);

                    if (p != null) {
                        // 🔥 GARANTE que lista de imagens nunca seja null
                        if (p.getImgurl() == null) {
                            p.setImgurl(new ArrayList<>());
                        }

                        novaLista.add(p);
                    }

                } catch (Exception e) {
                    e.printStackTrace(); // mostra erro real
                }
            }

            // 🔥 SEMPRE atualizar na UI thread
            runOnUiThread(() -> adapter.atualizarLista(novaLista));
        });

        // 🔍 BUSCA
        inputBusca.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filtrar(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}