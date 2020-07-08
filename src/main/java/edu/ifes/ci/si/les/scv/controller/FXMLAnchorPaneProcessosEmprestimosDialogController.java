package edu.ifes.ci.si.les.scv.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import edu.ifes.ci.si.les.scv.service.ClienteService;
import edu.ifes.ci.si.les.scv.service.FitaService;
import edu.ifes.ci.si.les.scv.model.Cliente;
import edu.ifes.ci.si.les.scv.model.ItemDeEmprestimo;
import edu.ifes.ci.si.les.scv.model.Fita;
import edu.ifes.ci.si.les.scv.model.Emprestimo;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

public class FXMLAnchorPaneProcessosEmprestimosDialogController implements Initializable {

    @FXML
    private ComboBox comboBoxEmprestimoCliente;
    @FXML
    private DatePicker datePickerEmprestimoData;
    @FXML
    private ComboBox comboBoxEmprestimoFita;
    @FXML
    private TableView<ItemDeEmprestimo> tableViewItensDeEmprestimo;
    @FXML
    private TableColumn<ItemDeEmprestimo, Fita> tableColumnItemDeEmprestimoFita;
    @FXML
    private TableColumn<ItemDeEmprestimo, Double> tableColumnItemDeEmprestimoValor;
    @FXML
    private TextField textFieldEmprestimoValor;
    @FXML
    private Button buttonConfirmar;
    @FXML
    private Button buttonCancelar;
    @FXML
    private Button buttonAdicionar;
    @FXML
    private Button buttonRemover;

    private List<Cliente> listClientes;
    private List<Fita> listFitas;
    private ObservableList<Cliente> observableListClientes;
    private ObservableList<Fita> observableListFitas;
    private ObservableList<ItemDeEmprestimo> observableListItensDeEmprestimo;

    private final ClienteService clienteService = new ClienteService();
    private final FitaService fitaService = new FitaService();

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Emprestimo emprestimo;

    ZoneId defaultZoneId = ZoneId.systemDefault();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarComboBoxClientes();
        carregarComboBoxFitas();

        tableColumnItemDeEmprestimoFita.setCellValueFactory(new PropertyValueFactory<>("fita"));
        tableColumnItemDeEmprestimoValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
    }

    public void carregarComboBoxClientes() {
        listClientes = clienteService.findAll();
        observableListClientes = FXCollections.observableArrayList(listClientes);
        comboBoxEmprestimoCliente.setItems(observableListClientes);
    }

    public void carregarComboBoxFitas() {
        listFitas = fitaService.findByDanificadaAndDisponivel(false, true); // Carregando somentes as fitas não danificadas e disponíveis (não associadas a empréstimo no momento)
        observableListFitas = FXCollections.observableArrayList(listFitas);
        comboBoxEmprestimoFita.setItems(observableListFitas);
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Emprestimo getEmprestimo() {
        return this.emprestimo;
    }

    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;

        if (emprestimo.getId() != null) {
            //[No caso de alteração] Deixando selecionado os dados da emprestimo escolhida
            comboBoxEmprestimoCliente.getSelectionModel().select(emprestimo.getCliente());
            datePickerEmprestimoData.setValue(emprestimo.getData().toInstant().atZone( ZoneId.systemDefault() ).toLocalDate());
            observableListItensDeEmprestimo = FXCollections.observableArrayList(emprestimo.getItens());
            tableViewItensDeEmprestimo.setItems(observableListItensDeEmprestimo);
            textFieldEmprestimoValor.setText(String.format("%.2f", emprestimo.getValor()));
        }else{
            this.emprestimo.setValor(0.0);
        }
    }

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    @FXML
    public void handleButtonAdicionar() {
        Fita fita;
        ItemDeEmprestimo itemDeEmprestimo = new ItemDeEmprestimo();

        if (comboBoxEmprestimoFita.getSelectionModel().getSelectedItem() != null) {
            fita = (Fita) comboBoxEmprestimoFita.getSelectionModel().getSelectedItem();

            if (fita.getDisponivel()) {
                itemDeEmprestimo.setFita((Fita) comboBoxEmprestimoFita.getSelectionModel().getSelectedItem());
                itemDeEmprestimo.setValor(itemDeEmprestimo.getFita().getFilme().getTipoDeFilme().getPreco());
                itemDeEmprestimo.setEntrega(new java.sql.Date(new java.util.Date().getTime()));//apenas para preencher

                emprestimo.getItens().add(itemDeEmprestimo);
                emprestimo.setValor(emprestimo.getValor() + itemDeEmprestimo.getValor());

                observableListItensDeEmprestimo = FXCollections.observableArrayList(emprestimo.getItens());
                tableViewItensDeEmprestimo.setItems(observableListItensDeEmprestimo);

                textFieldEmprestimoValor.setText(String.format("%.2f", emprestimo.getValor()));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Problemas na escolha do fita!");
                alert.setContentText("A fita não está disponível para empréstimos!");
                alert.show();
            }
        }
    }

    @FXML
    public void handleButtonRemover() {
        ItemDeEmprestimo itemDeEmprestimo;

        if (tableViewItensDeEmprestimo.getSelectionModel().getSelectedItem() != null) {
            itemDeEmprestimo = (ItemDeEmprestimo) tableViewItensDeEmprestimo.getSelectionModel().getSelectedItem();

            emprestimo.getItens().remove(itemDeEmprestimo);
            emprestimo.setValor(emprestimo.getValor() - itemDeEmprestimo.getValor());

            observableListItensDeEmprestimo = FXCollections.observableArrayList(emprestimo.getItens());
            tableViewItensDeEmprestimo.setItems(observableListItensDeEmprestimo);

            textFieldEmprestimoValor.setText(String.format("%.2f", emprestimo.getValor()));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Problemas na escolha do item de empréstimo!");
            alert.setContentText("Você não selecionou um item de empréstimos da tabela!");
            alert.show();
        }

    }

    @FXML
    public void handleButtonConfirmar() {
        if (validarEntradaDeDados()) {
            emprestimo.setCliente((Cliente) comboBoxEmprestimoCliente.getSelectionModel().getSelectedItem());
            emprestimo.setData(java.sql.Date.valueOf(datePickerEmprestimoData.getValue()));

            buttonConfirmarClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    public void handleButtonCancelar() {
        getDialogStage().close();
    }

    //Validar entrada de dados para o cadastro
    private boolean validarEntradaDeDados() {
        String errorMessage = "";

        if (comboBoxEmprestimoCliente.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Cliente inválido!\n";
        }
        if (datePickerEmprestimoData.getValue() == null) {
            errorMessage += "Data inválida!\n";
        }
        if (observableListItensDeEmprestimo == null) {
            errorMessage += "Itens de Empréstimo inválidos!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Mostrando a mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Campos inválidos, por favor, corrija...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }

}
