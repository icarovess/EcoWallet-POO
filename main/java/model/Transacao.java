package main.java.model;

import java.time.LocalDate;
import java.util.Locale.Category;

public abstract class Transacao {
    private String descricao;
    private Double valor;
    private LocalDate data;
    private Category categoria;


    //cosntrutor.
    public Transacao(String descricao, Double valor, LocalDate data, Category categoria){
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.categoria = categoria;
    }


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

    public Category getCategoria(){
        return categoria;
    }

}

