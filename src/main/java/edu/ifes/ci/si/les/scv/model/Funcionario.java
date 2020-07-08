package edu.ifes.ci.si.les.scv.model;

import javax.persistence.*;
import lombok.*;

@Entity
@Data 
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Funcionario extends Pessoa {

    private static final long serialVersionUID = 1L;

    private String login;

    private String senha;

    @Builder
    public Funcionario(Integer id, String nome, String cpf, String rua, Integer numero, Bairro bairro, String login, String senha) {
        super(id, nome, cpf, rua, numero, bairro);
        this.login = login;
        this.senha = senha;
    }

}
