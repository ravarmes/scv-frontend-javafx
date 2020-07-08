package edu.ifes.ci.si.les.scv.model;

import java.io.Serializable;
import javax.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Fita implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Boolean danificada;

    private Boolean disponivel;

    @ManyToOne
    @JoinColumn(name = "filme_id")
    private Filme filme;
    
    @Override
    public String toString() {
    	return this.getFilme().getTitulo() + " - " + this.getId();
    }

}
