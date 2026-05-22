package service;

import java.util.ArrayList;
import java.util.List;

import model.*;

public class GerenciadorFinancas {

    private List<Transacao> transacoes;

    public GerenciadorFinancas() {
        transacoes = new ArrayList<>();
    }

    //adicionar transição na lista
    public void adicionarTransicao(Transacao transacao) {
        transacoes.add(transacao);
    }


    //Calcular o saldo.
    public double calcularSaldo(){
        double saldo = 0;

        for (Transacao transacao : transacoes) {
            saldo += transacao.getValor();
        }

        return saldo;
    }

    //Setter lista de transição
    public List<Transacao> getTransacoes() {
        return transacoes;
    }  
}
