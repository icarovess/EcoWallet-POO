package service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import model.Despesa;
import model.Receita;
import model.Transacao;

public class GerenciadorFinancas {

    private static final String ARQUIVO = "dados/ecowallet_dados.json";

    private List<Transacao> transacoes;
    private final Gson gson;

    public GerenciadorFinancas() {
        new File("dados").mkdirs();
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class,
                        (JsonSerializer<LocalDate>) (src, type, ctx) -> new JsonPrimitive(src.toString()))
                .registerTypeAdapter(LocalDate.class,
                        (JsonDeserializer<LocalDate>) (json, type, ctx) -> LocalDate.parse(json.getAsString()))
                .setPrettyPrinting()
                .create();

        transacoes = carregarDoArquivo();
    }

    public void adicionarTransacao(Transacao transacao) {
        transacoes.add(transacao);
        salvarNoArquivo();
    }

    public void removerTransacao(Transacao transacao) {
        transacoes.remove(transacao);
        salvarNoArquivo();
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

    private void salvarNoArquivo() {
        try {
            List<JsonObject> lista = new ArrayList<>();
            for (Transacao t : transacoes) {
                JsonObject obj = gson.toJsonTree(t).getAsJsonObject();
                obj.addProperty("tipo", t instanceof Receita ? "Receita" : "Despesa");
                lista.add(obj);
            }
            Files.writeString(Path.of(ARQUIVO), gson.toJson(lista));
        } catch (IOException e) {
            System.err.println("Erro ao salvar: " + e.getMessage());
        }
    }

    private List<Transacao> carregarDoArquivo() {
        List<Transacao> lista = new ArrayList<>();
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) return lista;
        try {
            String conteudo = Files.readString(Path.of(ARQUIVO));
            Type tipo = new TypeToken<List<JsonObject>>() {}.getType();
            List<JsonObject> objetos = gson.fromJson(conteudo, tipo);
            if (objetos == null) return lista;
            for (JsonObject obj : objetos) {
                String tipoClasse = obj.get("tipo").getAsString();
                if (tipoClasse.equals("Receita")) {
                    lista.add(gson.fromJson(obj, Receita.class));
                } else {
                    lista.add(gson.fromJson(obj, Despesa.class));
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar: " + e.getMessage());
        }
        return lista;
    }
}