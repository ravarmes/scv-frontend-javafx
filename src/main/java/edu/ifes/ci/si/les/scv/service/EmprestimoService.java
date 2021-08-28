package edu.ifes.ci.si.les.scv.service;

import edu.ifes.ci.si.les.scv.model.Cliente;
import edu.ifes.ci.si.les.scv.model.Emprestimo;
import edu.ifes.ci.si.les.scv.resources.exceptions.StandardError;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class EmprestimoService {

    private final String URL = "http://localhost:8080/emprestimos/";
    //private final String URL = "https://scv-backend-spring-postgres.herokuapp.com/emprestimos/";

    private final Client client = ClientBuilder.newClient();

    public Emprestimo find(Integer id) {
        try {
            WebTarget target = client.target(URL + id);
            String json = target.request().get(String.class);
            ObjectMapper mapper = new ObjectMapper();
            Emprestimo emprestimo = mapper.readValue(json, Emprestimo.class);
            return emprestimo;
        } catch (IOException ex) {
            Logger.getLogger(EmprestimoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Emprestimo> findAll() {
        try {
            WebTarget target = client.target(URL);
            String json = target.request().get(String.class);
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Emprestimo>> mapType = new TypeReference<List<Emprestimo>>() {};
            List<Emprestimo> lista = mapper.readValue(json, mapType);
            return lista;
        } catch (IOException ex) {
            Logger.getLogger(EmprestimoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String insert(Emprestimo emprestimo) {
        try {
            WebTarget target = client.target(URL);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(emprestimo);
            Response response = target.request().post(Entity.entity(json, "application/json;charset=UTF-8"));
            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                String stringError = response.readEntity(String.class);
                StandardError standardError = mapper.readValue(stringError, StandardError.class);
                return standardError.getMessage();
            }
        } catch (IOException ex) {
            Logger.getLogger(EmprestimoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public String update(Emprestimo emprestimo) {
        try {
        	WebTarget target = client.target(URL + emprestimo.getId());
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(emprestimo);
            Response response = target.request().put(Entity.entity(json, "application/json;charset=UTF-8"));
            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                String stringError = response.readEntity(String.class);
                StandardError standardError = mapper.readValue(stringError, StandardError.class);
                return standardError.getMessage();
            }
        } catch (IOException ex) {
            Logger.getLogger(EmprestimoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public String delete(Integer id) {
        try {
            WebTarget target = client.target(URL + id);
            ObjectMapper mapper = new ObjectMapper();
            Response response = target.request().delete();
            if (response.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
                String stringError = response.readEntity(String.class);
                StandardError standardError = mapper.readValue(stringError, StandardError.class);
                return standardError.getMessage();
            }
        } catch (IOException ex) {
            Logger.getLogger(EmprestimoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    public List<Emprestimo> findByClienteAndPeriodo(Cliente cliente, String inicio, String termino) {
    	try {
            WebTarget target = client.target(URL + "findByClienteAndPeriodo/"+cliente.getId()+"/"+inicio+"/"+termino);
            String json = target.request().get(String.class);
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Emprestimo>> mapType = new TypeReference<List<Emprestimo>>() {};
            List<Emprestimo> lista = mapper.readValue(json, mapType);
            return lista;
        } catch (IOException ex) {
            Logger.getLogger(EmprestimoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Collection<?> findTotaisAndQuantidadesEmprestimosOfClientesByPeriodo(String inicio, String termino) {
        try {
            WebTarget target = client.target(URL + "findTotaisAndQuantidadesEmprestimosOfClientesByPeriodo/"+inicio+"/"+termino);
            String json = target.request().get(String.class);
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<Collection<?>> mapType = new TypeReference<Collection<?>>() {};
            Collection<?> collection = mapper.readValue(json, mapType);
            return collection;
        } catch (IOException ex) {
            Logger.getLogger(EmprestimoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
