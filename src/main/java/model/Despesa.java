package model;

import java.time.LocalDate;

public class Despesa extends Transacao{

    public Despesa(String descricao, Double valor, LocalDate data, Categoria categoria){
        super(descricao, valor, data, categoria);
    }

    @Override
    public Double getValorparaSaldo(){
        return getValor() * -1;
    }
    
}
