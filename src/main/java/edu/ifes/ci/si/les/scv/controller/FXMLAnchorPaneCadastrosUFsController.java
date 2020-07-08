package edu.ifes.ci.si.les.scv.controller;

import edu.ifes.ci.si.les.scv.model.UF;
import edu.ifes.ci.si.les.scv.service.UFService;
import java.io.IOException;
import java.net.URL;
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

public class FXMLAnchorPaneCadastrosUFsController implements Initializable {

    @FXML
    private TableView<UF> tableViewUFs;
    @FXML
    private TableColumn<UF, Integer> tableColumnUFId;
    @FXML
    private TableColumn<UF, String> tableColumnUFSigla;
    @FXML
    private TableColumn<UF, String> tableColumnUFNome;
    @FXML
    private Label labelUFId;
    @FXML
    private Label labelUFSigla;
    @FXML
    private Label labelUFNome;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonAlterar;
    @FXML
    private Button buttonRemover;

    private List<UF> listUFs;
    private ObservableList<UF> observableListUFs;

    private final UFService ufService = new UFService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarTableViewUFs();

        // Limpando a exibição dos detalhes do cliente
        selecionarItemTableViewUFs(null);

        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableViewUFs.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewUFs(newValue));
    }

    public void carregarTableViewUFs() {
        tableColumnUFId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnUFSigla.setCellValueFactory(new PropertyValueFactory<>("sigla"));
        tableColumnUFNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        listUFs = ufService.findAll();

        observableListUFs = FXCollections.observableArrayList(listUFs);
        tableViewUFs.setItems(observableListUFs);
        tableViewUFs.refresh();
    }

    public void selecionarItemTableViewUFs(UF uf) {
        if (uf != null) {
            labelUFId.setText(uf.getId().toString());
            labelUFSigla.setText(uf.getSigla());
            labelUFNome.setText(uf.getNome());
        } else {
            labelUFId.setText("");
            labelUFSigla.setText("");
            labelUFNome.setText("");
        }
    }

    @FXML
    public void handleButtonInserir() throws IOException {
        UF uf = new UF();
        boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosUFsDialog(uf);
        if (buttonConfirmarClicked) {
            String resultado = ufService.insert(uf);
            exibirMensagemErro(resultado);
            carregarTableViewUFs();
        }
    }

    @FXML
    public void handleButtonAlterar() throws IOException {
        UF uf = tableViewUFs.getSelectionModel().getSelectedItem();
        if (uf != null) {
            boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosUFsDialog(uf);
            if (buttonConfirmarClicked) {
                String resultado = ufService.update(uf);
                exibirMensagemErro(resultado);
                carregarTableViewUFs();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha uma UF na Tabela!");
            alert.show();
        }
    }

    @FXML
    public void handleButtonRemover() throws IOException {
        UF uf = tableViewUFs.getSelectionModel().getSelectedItem();
        if (uf != null) {
            String resultado = ufService.delete(uf.getId());
            exibirMensagemErro(resultado);
            carregarTableViewUFs();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha uma UF na Tabela!");
            alert.show();
        }
    }

    public boolean showFXMLAnchorPaneCadastrosUFsDialog(UF uf) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneCadastrosUFsDialogController.class.getResource("/edu/ifes/ci/si/les/scv/view/FXMLAnchorPaneCadastrosUFsDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de UFs");
        //Especifica a modalidade para esta fase . Isso deve ser feito antes de fazer o estágio visível. A modalidade pode ser: Modality.NONE , Modality.WINDOW_MODAL , ou Modality.APPLICATION_MODAL 
        //dialogStage.initModality(Modality.WINDOW_MODAL);//WINDOW_MODAL (possibilita minimizar)

        //Especifica a janela do proprietário para esta página, ou null para um nível superior.
        //dialogStage.initOwner(null); //null deixa a Tela Principal livre para ser movida
        //dialogStage.initOwner(this.tableViewClientes.getScene().getWindow()); //deixa a tela de Preenchimento dos dados como prioritária
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Setando o cliente no Controller.
        FXMLAnchorPaneCadastrosUFsDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setUF(uf);

        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();

    }

    public void exibirMensagemErro(String resultado) {
        if (!resultado.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(resultado);
            alert.show();
        }
    }

}
