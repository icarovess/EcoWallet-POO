package main.java.model;

import java.time.LocalDate;

public abstract class Transacao {
    private String descricao;
    private Double valor;
    private LocalDate data;
    private Categoria categoria;


    //cosntrutor.
    public Transacao(String descricao, Double valor, LocalDate data, Categoria categoria){
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.categoria = categoria;
    }

    //abstração
    public abstract Double getValorparaSaldo();

    //getters 
    public String getDescricao(){
        return descricao;
    }

    public Double getValor(){
        return valor;
    }
    
    public LocalDate getData(){
        return data;
    }

    public Categoria getCategoria(){
        return categoria;
    }

    //setters
    public void setDescricao(String descricao){
        this.descricao = descricao;
    }

    public void setValor(Double valor){
        this.valor = valor;
    }

    public void setData(LocalDate data){
        this.data = data;
    }

    public void setCategoria(Categoria categoria){
        this.categoria = categoria;
    }

}

