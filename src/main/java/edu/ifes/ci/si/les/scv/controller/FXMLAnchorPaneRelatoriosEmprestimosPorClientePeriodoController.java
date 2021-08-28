package edu.ifes.ci.si.les.scv.controller;

import edu.ifes.ci.si.les.scv.model.Cliente;
import edu.ifes.ci.si.les.scv.model.Emprestimo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import edu.ifes.ci.si.les.scv.model.reports.RelatorioDeEmprestimoPorClienteTotalQuantidade;
import edu.ifes.ci.si.les.scv.service.ClienteService;
import edu.ifes.ci.si.les.scv.service.EmprestimoService;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Rafael Vargas Mesquita	
 */
public class FXMLAnchorPaneRelatoriosEmprestimosPorClientePeriodoController implements Initializable {

    
	@FXML
    private TableView<Emprestimo> tableViewRelatorio;
    @FXML
    private TableColumn<Emprestimo, Integer> tableColumnEmprestimoCodigo;
    @FXML
    private TableColumn<Emprestimo, String> tableColumnEmprestimoData;
    @FXML
    private TableColumn<Emprestimo, Double> tableColumnEmprestimoValor;
    @FXML
    private ComboBox<Cliente> comboBoxClientes;
    @FXML
    private DatePicker datePickerInicio;
    @FXML
    private DatePicker datePickerTermino;
    
    private final EmprestimoService emprestimoService = new EmprestimoService();
    private final ClienteService clienteService = new ClienteService();
    
    private List<Emprestimo> listEmprestimos;
    private ObservableList<Emprestimo> observableListEmprestimos;
    
    private List<Cliente> listClientes;
    private ObservableList<Cliente> observableListClientes;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	tableColumnEmprestimoCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
    	tableColumnEmprestimoData.setCellValueFactory(new PropertyValueFactory<>("dataString"));
    	tableColumnEmprestimoValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
    	
    	carregarComboBoxClientes();
    }
    
    public void carregarComboBoxClientes() {
        listClientes = clienteService.findAll();
        observableListClientes = FXCollections.observableArrayList(listClientes);
        comboBoxClientes.setItems(observableListClientes);
    }
    
    @FXML
    public void handleButtonConfirmar() {
    	String inicio = datePickerInicio.getValue().toString();
        String termino = datePickerTermino.getValue().toString();
        Cliente cliente = (Cliente)comboBoxClientes.getSelectionModel().getSelectedItem();
        
        listEmprestimos = (List<Emprestimo>) emprestimoService.findByClienteAndPeriodo(cliente, inicio, termino);        
        observableListEmprestimos = FXCollections.observableArrayList(listEmprestimos);
        tableViewRelatorio.setItems(observableListEmprestimos);
        tableViewRelatorio.refresh();
    }
    
    
}
