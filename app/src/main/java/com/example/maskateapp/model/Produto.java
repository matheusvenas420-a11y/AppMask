package com.example.maskateapp.model;

public class Produto {
    private String nome;
    private String codigo;
    private int quantidade;
    private String imgurl;
    public Produto() {
    }
    public Produto(String nome, String codigo, int quantidade, String imgurl) {
        this.nome = nome;
        this.codigo = codigo;
        this.quantidade = quantidade;
        this.imgurl = imgurl;
    }
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

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
