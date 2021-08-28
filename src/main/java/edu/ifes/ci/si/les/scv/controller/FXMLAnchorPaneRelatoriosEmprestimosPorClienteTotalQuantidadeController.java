package edu.ifes.ci.si.les.scv.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import edu.ifes.ci.si.les.scv.model.reports.RelatorioDeEmprestimoPorClienteTotalQuantidade;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Rafael Vargas Mesquita	
 */
public class FXMLAnchorPaneRelatoriosEmprestimosPorClienteTotalQuantidadeController implements Initializable {

    
    @FXML
    private TableView<RelatorioDeEmprestimoPorClienteTotalQuantidade> tableViewRelatorio;
    @FXML
    private TableColumn<RelatorioDeEmprestimoPorClienteTotalQuantidade, String> tableColumnCliente;
    @FXML
    private TableColumn<RelatorioDeEmprestimoPorClienteTotalQuantidade, Double> tableColumnTotal;
    @FXML
    private TableColumn<RelatorioDeEmprestimoPorClienteTotalQuantidade, Integer> tableColumnQuantidade;
    @FXML
    private DatePicker datePickerInicio;
    @FXML
    private DatePicker datePickerTermino;
    
    private final EmprestimoService emprestimoService = new EmprestimoService();
    
    private Collection<ArrayList<?>> collection;
    private List<RelatorioDeEmprestimoPorClienteTotalQuantidade> list;
    private ObservableList<RelatorioDeEmprestimoPorClienteTotalQuantidade> observableList;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	tableColumnCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        tableColumnQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        tableColumnTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
    }    
    
    @FXML
    public void handleButtonConfirmar() {
    	String inicio = datePickerInicio.getValue().toString(); 
        String termino = datePickerTermino.getValue().toString();
        collection = (Collection<ArrayList<?>>) emprestimoService.findTotaisAndQuantidadesEmprestimosOfClientesByPeriodo(inicio, termino);
        list = new ArrayList();

        for(ArrayList<?> arrayList: collection) {
        	RelatorioDeEmprestimoPorClienteTotalQuantidade linha = new RelatorioDeEmprestimoPorClienteTotalQuantidade();
        	linha.setCliente((String)arrayList.get(0));
        	linha.setTotal((Double)arrayList.get(1));
        	linha.setQuantidade((Integer)arrayList.get(2));
        	list.add(linha);
        }
        
        observableList = FXCollections.observableArrayList(list);
        tableViewRelatorio.setItems(observableList);
        tableViewRelatorio.refresh();
    }
    
    
}
