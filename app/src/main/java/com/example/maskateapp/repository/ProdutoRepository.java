package com.example.maskateapp.repository;

import android.util.Log;

import com.example.maskateapp.model.Produto;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

// ProdutoRepository
public class ProdutoRepository {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void salvar(Produto p) {
        db.collection("produtos")
                .add(p)
                .addOnSuccessListener(doc -> {
                    Log.d("FIREBASE", "Salvo com sucesso");
                })
                .addOnFailureListener(e -> {
                    Log.e("FIREBASE", "Erro ao salvar", e);
                });
    }
    public void listar(OnSuccessListener<QuerySnapshot> success) {
        db.collection("produtos")
                .get()
                .addOnSuccessListener(success);
    }
}