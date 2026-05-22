package model;

import java.time.LocalDate;

public class Receita extends Transacao{

    public Receita(String descricao, Double valor, LocalDate data, Categoria categoria){
        super(descricao, valor, data, categoria);
    }

    @Override
    public Double getValorparaSaldo(){
        return getValor();
    }
}
