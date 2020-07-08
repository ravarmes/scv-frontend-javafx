package edu.ifes.ci.si.les.scv.model;

import javax.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Data 
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Cliente {

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

    private Double debito;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date nascimento;

    @ElementCollection
	@CollectionTable(name="TELEFONE")
	private Set<String> telefones = new HashSet<>();
    
    @Builder
    public Cliente(Integer id, String nome, String cpf, String rua, Integer numero, Bairro bairro, Double debito, Date nascimento) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.debito = debito;
        this.nascimento = nascimento;
    }
    
    @Override
    public String toString() {
    	return this.getNome();
    }

}
