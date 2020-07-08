package edu.ifes.ci.si.les.scv.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import edu.ifes.ci.si.les.scv.service.FitaService;
import edu.ifes.ci.si.les.scv.service.EmprestimoService;
import edu.ifes.ci.si.les.scv.model.ItemDeEmprestimo;
import edu.ifes.ci.si.les.scv.model.Emprestimo;
import java.text.Format;
import java.text.SimpleDateFormat;

public class FXMLAnchorPaneProcessosEmprestimosController implements Initializable {

    @FXML
    private TableView<Emprestimo> tableViewEmprestimos;
    @FXML
    private TableColumn<Emprestimo, Integer> tableColumnEmprestimoCodigo;
    @FXML
    private TableColumn<Emprestimo, String> tableColumnEmprestimoData;
    @FXML
    private TableColumn<Emprestimo, Emprestimo> tableColumnEmprestimoCliente;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonAlterar;
    @FXML
    private Button buttonRemover;
    @FXML
    private Label labelEmprestimoCodigo;
    @FXML
    private Label labelEmprestimoData;
    @FXML
    private Label labelEmprestimoValor;
    @FXML
    private Label labelEmprestimoCliente;

    private List<Emprestimo> listEmprestimos;
    private ObservableList<Emprestimo> observableListEmprestimos;

    private final EmprestimoService emprestimoService = new EmprestimoService();
    private final FitaService fitaService = new FitaService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        carregarTableViewEmprestimos();

        // Limpando a exibição dos detalhes da emprestimo
        selecionarItemTableViewEmprestimos(null);

        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableViewEmprestimos.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewEmprestimos(newValue));
    }

    public void carregarTableViewEmprestimos() {
        tableColumnEmprestimoCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
        //tableColumnEmprestimoData.setCellValueFactory(new PropertyValueFactory<>("data"));
        tableColumnEmprestimoData.setCellValueFactory(new PropertyValueFactory<>("dataString"));
        tableColumnEmprestimoCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));

        listEmprestimos = emprestimoService.findAll();

        observableListEmprestimos = FXCollections.observableArrayList(listEmprestimos);
        tableViewEmprestimos.setItems(observableListEmprestimos);
    }

    public void selecionarItemTableViewEmprestimos(Emprestimo emprestimo) {
        if (emprestimo != null) {
            Format formatter = new SimpleDateFormat("dd/MM/yyyy");
            labelEmprestimoCodigo.setText(String.valueOf(emprestimo.getId()));
            labelEmprestimoData.setText(formatter.format(emprestimo.getData()));
            labelEmprestimoValor.setText(String.format("%.2f", emprestimo.getValor()));
            labelEmprestimoCliente.setText(emprestimo.getCliente().toString());
        } else {
            labelEmprestimoCodigo.setText("");
            labelEmprestimoData.setText("");
            labelEmprestimoValor.setText("");
            labelEmprestimoCliente.setText("");
        }
    }

    @FXML
    public void handleButtonInserir() throws IOException {
        Emprestimo emprestimo = new Emprestimo();
        List<ItemDeEmprestimo> listItensDeEmprestimo = new ArrayList<>();
        emprestimo.setItens(listItensDeEmprestimo);
        boolean buttonConfirmarClicked = showFXMLAnchorPaneProcessosEmprestimosDialog(emprestimo);
        if (buttonConfirmarClicked) {
            String resultado = emprestimoService.insert(emprestimo);
            exibirMensagemErro(resultado);
            carregarTableViewEmprestimos();
        }

    }

    @FXML
    public void handleButtonAlterar() throws IOException {
        Emprestimo emprestimo = this.tableViewEmprestimos.getSelectionModel().getSelectedItem();
        if (emprestimo != null) {
            boolean buttonConfirmarClicked = showFXMLAnchorPaneProcessosEmprestimosDialog(emprestimo);
            if (buttonConfirmarClicked) {
            	String resultado = emprestimoService.update(emprestimo);
                exibirMensagemErro(resultado);
                carregarTableViewEmprestimos();
            }
        }
    }

    @FXML
    public void handleButtonRemover() throws IOException, SQLException {
        Emprestimo emprestimo = tableViewEmprestimos.getSelectionModel().getSelectedItem();
        if (emprestimo != null) {
        	String resultado = emprestimoService.delete(emprestimo.getId());
            exibirMensagemErro(resultado);
            carregarTableViewEmprestimos();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha uma emprestimo na Tabela!");
            alert.show();
        }
    }

    public boolean showFXMLAnchorPaneProcessosEmprestimosDialog(Emprestimo emprestimo) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneProcessosEmprestimosDialogController.class.getResource("/edu/ifes/ci/si/les/scv/view/FXMLAnchorPaneProcessosEmprestimosDialog.fxml"));

        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Registro de Emprestimos");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Setando o Empréstimo no Controller.
        FXMLAnchorPaneProcessosEmprestimosDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setEmprestimo(emprestimo);

        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();

    }
    
    public void exibirMensagemErro(String resultado){
        if(!resultado.equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(resultado);
            alert.show();
        }
    }

}
