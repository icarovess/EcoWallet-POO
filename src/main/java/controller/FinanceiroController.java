package controller;

import factory.TransacaoFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Categoria;
import model.Transacao;
import service.GerenciadorFinancas;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class FinanceiroController implements Initializable {

    @FXML private TextField txtDescricao;
    @FXML private TextField txtValor;
    @FXML private ComboBox<String> cmbTipo;
    @FXML private ComboBox<Categoria> cmbCategoria;
    @FXML private DatePicker datePicker;

    @FXML private TableView<Transacao> tabelaTransacoes;
    @FXML private TableColumn<Transacao, String> colDescricao;
    @FXML private TableColumn<Transacao, Double> colValor;
    @FXML private TableColumn<Transacao, String> colTipo;
    @FXML private TableColumn<Transacao, Categoria> colCategoria;
    @FXML private TableColumn<Transacao, LocalDate> colData;

    @FXML private Label lblSaldo;

    private final GerenciadorFinancas gerenciador = new GerenciadorFinancas();
    private final ObservableList<Transacao> listaObservavel = FXCollections.observableArrayList();
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarCombos();
        configurarTabela();
        carregarTransacoesSalvas();
        atualizarSaldo();
    }

    private void configurarCombos() {
        cmbTipo.setItems(FXCollections.observableArrayList("Receita", "Despesa"));
        cmbTipo.getSelectionModel().selectFirst();
        cmbCategoria.setItems(FXCollections.observableArrayList(Categoria.values()));
        cmbCategoria.getSelectionModel().selectFirst();
        datePicker.setValue(LocalDate.now());
    }

    private void configurarTabela() {
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colValor.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Double val, boolean empty) {
                super.updateItem(val, empty);
                if (empty || val == null) { setText(null); setStyle(""); return; }
                Transacao t = getTableView().getItems().get(getIndex());
                boolean isDespesa = t.getValorparaSaldo() < 0;
                setText(String.format("R$ %.2f", val));
                setStyle(isDespesa
                        ? "-fx-text-fill: #e53935; -fx-font-weight: bold;"
                        : "-fx-text-fill: #43a047; -fx-font-weight: bold;");
            }
        });

        colTipo.setCellValueFactory(cell -> {
            Transacao t = cell.getValue();
            String tipo = t.getValorparaSaldo() < 0 ? "DESPESA" : "RECEITA";
            return new javafx.beans.property.SimpleStringProperty(tipo);
        });
        colTipo.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String tipo, boolean empty) {
                super.updateItem(tipo, empty);
                if (empty || tipo == null) { setText(null); setStyle(""); return; }
                setText(tipo);
                setStyle(tipo.equals("RECEITA")
                        ? "-fx-text-fill: #43a047; -fx-font-weight: bold;"
                        : "-fx-text-fill: #e53935; -fx-font-weight: bold;");
            }
        });

        colData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colData.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate d, boolean empty) {
                super.updateItem(d, empty);
                setText(empty || d == null ? null : d.format(FMT));
            }
        });

        tabelaTransacoes.setItems(listaObservavel);
        tabelaTransacoes.setPlaceholder(new Label("Nenhuma transacao cadastrada."));
    }

    private void carregarTransacoesSalvas() {
        listaObservavel.addAll(gerenciador.getTransacoes());
    }

    @FXML
    private void handleAdicionar() {
        if (!validarCampos()) return;

        String tipo = cmbTipo.getValue();
        String descricao = txtDescricao.getText().trim();
        Double valor = Double.parseDouble(txtValor.getText().trim().replace(",", "."));
        LocalDate data = datePicker.getValue();
        Categoria categoria = cmbCategoria.getValue();

        Transacao t = TransacaoFactory.criarTransacao(tipo, descricao, valor, data, categoria);

        if (t != null) {
            gerenciador.adicionarTransacao(t);
            listaObservavel.add(t);
            atualizarSaldo();
            limparCampos();
        }
    }

    @FXML
    private void handleRemover() {
        Transacao selecionada = tabelaTransacoes.getSelectionModel().getSelectedItem();
        if (selecionada == null) {
            alerta(Alert.AlertType.WARNING, "Atencao", "Selecione uma transacao para remover.");
            return;
        }
        gerenciador.removerTransacao(selecionada);
        listaObservavel.remove(selecionada);
        atualizarSaldo();
    }

    @FXML
    private void handleLimpar() {
        limparCampos();
    }

    private void atualizarSaldo() {
        double saldo = gerenciador.calcularSaldo();
        lblSaldo.setText(String.format("Saldo Total: R$ %.2f", saldo));
        if (saldo < 0) {
            lblSaldo.setStyle("-fx-text-fill: #e53935;");
        } else if (saldo > 0) {
            lblSaldo.setStyle("-fx-text-fill: #2e7d32;");
        } else {
            lblSaldo.setStyle("-fx-text-fill: #424242;");
        }
    }

    private boolean validarCampos() {
        if (txtDescricao.getText().trim().isEmpty()) {
            alerta(Alert.AlertType.ERROR, "Erro", "Informe a descricao.");
            txtDescricao.requestFocus();
            return false;
        }
        try {
            double v = Double.parseDouble(txtValor.getText().trim().replace(",", "."));
            if (v <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            alerta(Alert.AlertType.ERROR, "Erro", "Valor invalido. Use numeros positivos (ex: 20.50).");
            txtValor.requestFocus();
            return false;
        }
        if (datePicker.getValue() == null) {
            alerta(Alert.AlertType.ERROR, "Erro", "Selecione uma data.");
            return false;
        }
        return true;
    }

    private void limparCampos() {
        txtDescricao.clear();
        txtValor.clear();
        cmbTipo.getSelectionModel().selectFirst();
        cmbCategoria.getSelectionModel().selectFirst();
        datePicker.setValue(LocalDate.now());
        txtDescricao.requestFocus();
    }

    private void alerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}