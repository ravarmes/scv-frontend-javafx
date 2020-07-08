package edu.ifes.ci.si.les.scv.model;

import java.io.Serializable;
import javax.persistence.*;
import lombok.*;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public abstract class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private String cpf;

    private String rua;

    private Integer numero;

    @ManyToOne
    @JoinColumn(name = "bairro_id")
    private Bairro bairro;

}
