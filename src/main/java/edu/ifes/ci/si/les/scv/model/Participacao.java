package edu.ifes.ci.si.les.scv.model;

import java.io.Serializable;
import javax.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Participacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String personagem;

    @JsonIgnore //Evitando referência cíclica
    @ManyToOne
    @JoinColumn(name = "filme_id")
    private Filme filme;

    @ManyToOne
    @JoinColumn(name = "artista_id")
    private Artista artista;

}
