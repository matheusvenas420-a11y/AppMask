package com.example.maskateapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maskateapp.R;
import com.example.maskateapp.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ViewHolder> {

    private List<Produto> lista;
    private List<Produto> listaOriginal;
    private OnItemClick listener;

    public interface OnItemClick {
        void onClick(Produto p);
    }

    public ProdutoAdapter(List<Produto> lista, OnItemClick listener) {
        this.lista = lista;
        this.listaOriginal = new ArrayList<>(lista);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_produto, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Produto p = lista.get(position);

        holder.nome.setText(p.getNome());
        holder.codigo.setText(p.getCodigo());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClick(p);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void filtrar(String texto) {
        lista.clear();

        if (texto.isEmpty()) {
            lista.addAll(listaOriginal);
        } else {
            for (Produto p : listaOriginal) {
                if (p.getNome().toLowerCase().contains(texto.toLowerCase())) {
                    lista.add(p);
                }
            }
        }

        notifyDataSetChanged();
    }

    public void atualizarLista(List<Produto> novaLista) {
        lista.clear();
        lista.addAll(novaLista);

        listaOriginal.clear();
        listaOriginal.addAll(novaLista);

        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nome, codigo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.txtNome);
            codigo = itemView.findViewById(R.id.txtCodigo);
        }
    }
}