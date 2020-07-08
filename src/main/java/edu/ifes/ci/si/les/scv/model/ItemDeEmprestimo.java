package edu.ifes.ci.si.les.scv.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class ItemDeEmprestimo implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @EmbeddedId
    private ItemDeEmprestimoPK id = new ItemDeEmprestimoPK();

    private Double valor;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date entrega;

    @Builder
    public ItemDeEmprestimo(Emprestimo emprestimo, Fita fita, Double valor, Date entrega) {
        this.id.setEmprestimo(emprestimo);
        this.id.setFita(fita);
        this.valor = valor;
        this.entrega = entrega;
    }

    @JsonIgnore
    public Emprestimo getEmprestimo() {
        return id.getEmprestimo();
    }

    public void setEmprestimo(Emprestimo emprestimo) {
        id.setEmprestimo(emprestimo);
    }

    public Fita getFita() {
        return id.getFita();
    }

    public void setFita(Fita fita) {
        id.setFita(fita);
    }

}
