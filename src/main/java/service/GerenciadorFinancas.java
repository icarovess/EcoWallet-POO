package service;

import java.util.ArrayList;
import java.util.List;

import model.Transacao;

public class GerenciadorFinancas {

    private List<Transacao> transacoes;

    public GerenciadorFinancas() {
        transacoes = new ArrayList<>();
    }

    public void adicionarTransacao(Transacao transacao) {
        transacoes.add(transacao);
    }

    public void removerTransacao(Transacao transacao) {
        transacoes.remove(transacao);
    }

    public double calcularSaldo() {
        double saldo = 0;
        for (Transacao transacao : transacoes) {
            saldo += transacao.getValorparaSaldo();
        }
        return saldo;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }
}