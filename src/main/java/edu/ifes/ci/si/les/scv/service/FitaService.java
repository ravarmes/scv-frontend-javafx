package edu.ifes.ci.si.les.scv.service;

import edu.ifes.ci.si.les.scv.model.Fita;
import edu.ifes.ci.si.les.scv.resources.exceptions.StandardError;
import java.io.IOException;
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

public class FitaService {

    private final String URL = "http://localhost:8080/fitas/";
    //private final String URL = "https://scv-backend-spring-postgres.herokuapp.com/fitas/";

    private final Client client = ClientBuilder.newClient();

    public Fita find(Integer id) {
        try {
            WebTarget target = client.target(URL + id);
            String json = target.request().get(String.class);
            ObjectMapper mapper = new ObjectMapper();
            Fita fita = mapper.readValue(json, Fita.class);
            return fita;
        } catch (IOException ex) {
            Logger.getLogger(FitaService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

	public List<Fita> findAll() {
		try {
			WebTarget target = client.target(URL);
			String json = target.request().get(String.class);
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<List<Fita>> mapType = new TypeReference<List<Fita>>() {
			};
			List<Fita> lista = mapper.readValue(json, mapType);
			return lista;
		} catch (IOException ex) {
			Logger.getLogger(FitaService.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}
	
	public List<Fita> findByDanificadaAndDisponivel(Boolean danificada, Boolean disponivel) {
		try {
			WebTarget target = client.target(URL + "/findByDanificadaAndDisponivel/"+danificada+"/"+disponivel);
			String json = target.request().get(String.class);
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<List<Fita>> mapType = new TypeReference<List<Fita>>() {
			};
			List<Fita> lista = mapper.readValue(json, mapType);
			return lista;
		} catch (IOException ex) {
			Logger.getLogger(FitaService.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

    public String insert(Fita fita) {
        try {
            WebTarget target = client.target(URL);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(fita);
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

    public String update(Fita fita) {
        try {
            WebTarget target = client.target(URL+fita.getId());
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(fita);
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

}
