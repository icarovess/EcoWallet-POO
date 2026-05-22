package factory;

import java.time.LocalDate;

import model.*;

public class TransacaoFactory {

    public static Transacao criarTransacao(String tipo, String descricao, Double valor, LocalDate data, Categoria categoria){
        if(tipo.equalsIgnoreCase("receita")){
            return new Receita(descricao, valor, data, categoria);
        }
        else if(tipo.equalsIgnoreCase("despesa")){
            return new Despesa(descricao, valor, data, categoria);
        }
        return null;
        }
    }