package main.java.model;

import java.time.LocalDate;
import java.util.Locale.Category;

public class Receita extends Transacao{

    public Receita(String descricao, Double valor, LocalDate data, Category categoria){
        super(descricao, valor, data, categoria);
    }

    
}
