package edu.ifes.ci.si.les.scv.model;

import java.util.Date;
import java.io.Serializable;
import javax.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Devolucao implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @EmbeddedId
    private DevolucaoPK id = new DevolucaoPK();

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date data;

    @Builder
    public Devolucao(ItemDeEmprestimo itemDeEmprestimo, Date data) {
        this.id.setItemDeEmprestimo(itemDeEmprestimo);
        this.data = data;
    }

    public Integer getEmprestimoId() { //Método criado para facilitar a visualização da chave primária desta devolução
        return id.getItemDeEmprestimo().getEmprestimo().getId();
    }

    public Integer getFitaId() { //Método criado para facilitar a visualização da chave primária desta devolução
        return id.getItemDeEmprestimo().getFita().getId();
    }

    @JsonIgnore
    public ItemDeEmprestimo getItemDeEmprestimo() {
        return id.getItemDeEmprestimo();
    }

}
