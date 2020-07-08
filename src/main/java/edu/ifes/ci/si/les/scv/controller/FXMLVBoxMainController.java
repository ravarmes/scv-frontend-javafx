package edu.ifes.ci.si.les.scv.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

public class FXMLVBoxMainController implements Initializable {
    
    @FXML
    private MenuItem menuItemCadastrosUfs;
    @FXML
    private AnchorPane anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void handleMenuItemCadastrosUfs() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/edu/ifes/ci/si/les/scv/view/FXMLAnchorPaneCadastrosUFs.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    public void handleMenuItemProcessosEmprestimos() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/edu/ifes/ci/si/les/scv/view/FXMLAnchorPaneProcessosEmprestimos.fxml"));
        anchorPane.getChildren().setAll(a);
    }

}
