package main.java.model;

import java.time.LocalDate;
import java.util.Locale.Category;

public class Despesa extends Transacao{

    public Despesa(String descricao, Double valor, LocalDate data, Category categoria){
        super(descricao, valor, data, categoria);
    }
    
}
