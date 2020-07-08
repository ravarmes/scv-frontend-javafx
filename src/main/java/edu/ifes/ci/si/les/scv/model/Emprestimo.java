package edu.ifes.ci.si.les.scv.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Collection;
import javax.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Emprestimo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date data;

    private Double valor;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    //orphanRemoval = true: utilizado para remover filhos (itens) sem pai (empréstimo) em caso de atualizaçao do empréstimo (para um número de itens menor que o anterior)
    @OneToMany(mappedBy = "id.emprestimo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<ItemDeEmprestimo> itens = new ArrayList<>();
    
    private String dataString; //Atributo criado apenas para exibição formatada no TableView

    @Builder
    public Emprestimo(Integer id, Date data, Double valor, Cliente cliente) {
        this.id = id;
        this.data = data;
        this.valor = valor;
        this.cliente = cliente;
    }
    
    public String getDataString() {
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	return sdf.format(this.data);
    }

}
