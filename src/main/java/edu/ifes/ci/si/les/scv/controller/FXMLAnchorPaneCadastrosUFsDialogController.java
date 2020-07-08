package edu.ifes.ci.si.les.scv.controller;

import java.net.URL;
import java.util.ResourceBundle;

import edu.ifes.ci.si.les.scv.model.UF;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLAnchorPaneCadastrosUFsDialogController implements Initializable {

    @FXML
    private Button buttonConfirmar;
    @FXML
    private Button buttonCancelar;
    @FXML
    private Label labelUFId;
    @FXML
    private Label labelUFSigla;
    @FXML
    private Label labelUFNome;
    @FXML
    private TextField textFieldUFSigla;
    @FXML
    private TextField textFieldUFNome;
    
    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private UF uf;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buttonCancelar.setCancelButton(true);
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    
    public UF getUF() {
        return this.uf;
    }

    public void setUF(UF uf) {
        this.uf = uf;
        if(uf.getId() != null)
        	this.labelUFId.setText(uf.getId().toString());
        else
        	this.labelUFId.setText("");
        this.textFieldUFSigla.setText(uf.getSigla());
        this.textFieldUFNome.setText(uf.getNome());
    }
    

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    @FXML
    public void handleButtonConfirmar() {
        if (validarEntradaDeDados()) {
        	if(labelUFId.getText() != "")
        		uf.setId(Integer.parseInt(labelUFId.getText()));
        	else
        		uf.setId(0);
            uf.setSigla(textFieldUFSigla.getText());
            uf.setNome(textFieldUFNome.getText());
            buttonConfirmarClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    public void handleButtonCancelar() {
        dialogStage.close();
    }

    //Validar entrada de dados para o cadastro
    private boolean validarEntradaDeDados() {
        String errorMessage = "";

        if (textFieldUFSigla.getText() == null || textFieldUFSigla.getText().length() == 0) {
            errorMessage += "Sigla inválida!\n";
        }
        if (textFieldUFNome.getText() == null || textFieldUFNome.getText().length() == 0) {
            errorMessage += "Nome inválido!\n";
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
