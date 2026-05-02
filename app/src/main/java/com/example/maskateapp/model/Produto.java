package com.example.maskateapp.model;

import java.util.ArrayList;
import java.util.List;

public class Produto {

    private String nome;
    private String codigo;
    private String codigoBarras;
    private int quantidade;
    private List<String> imgurl;

    // 🔹 CONSTRUTOR VAZIO (OBRIGATÓRIO pro Firebase)
    public Produto() {
        imgurl = new ArrayList<>();
    }

    // 🔹 CONSTRUTOR COMPLETO
    public Produto(String nome, String codigo, String codigoBarras, int quantidade, List<String> imgurl) {
        this.nome = nome;
        this.codigo = codigo;
        this.codigoBarras = codigoBarras;
        this.quantidade = quantidade;
        this.imgurl = imgurl != null ? imgurl : new ArrayList<>();
    }

    // GETTERS E SETTERS

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public List<String> getImgurl() {
        return imgurl;
    }

    public void setImgurl(List<String> imgurl) {
        this.imgurl = imgurl != null ? imgurl : new ArrayList<>();
    }
}