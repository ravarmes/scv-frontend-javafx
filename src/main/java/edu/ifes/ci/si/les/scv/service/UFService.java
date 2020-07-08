package edu.ifes.ci.si.les.scv.service;

import edu.ifes.ci.si.les.scv.model.UF;
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

public class UFService {

    private final String URL = "http://localhost:8080/ufs/";
    //private final String URL = "https://scv-backend-spring-postgres.herokuapp.com/ufs/";

    private final Client client = ClientBuilder.newClient();

    public UF find(String sigla) {
        try {
            WebTarget target = client.target(URL + sigla);
            String json = target.request().get(String.class);
            ObjectMapper mapper = new ObjectMapper();
            UF uf = mapper.readValue(json, UF.class);
            return uf;
        } catch (IOException ex) {
            Logger.getLogger(UFService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<UF> findAll() {
        try {
            WebTarget target = client.target(URL);
            String json = target.request().get(String.class);
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<UF>> mapType = new TypeReference<List<UF>>() {};
            List<UF> lista = mapper.readValue(json, mapType);
            return lista;
        } catch (IOException ex) {
            Logger.getLogger(UFService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

        public String insert(UF uf) {
        try {
            WebTarget target = client.target(URL);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(uf);
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

    public String update(UF uf) {
        try {
            WebTarget target = client.target(URL+uf.getId());
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(uf);
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
