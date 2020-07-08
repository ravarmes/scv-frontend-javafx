package edu.ifes.ci.si.les.scv.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Filme implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String titulo;

    private String genero;

    private String duracao;
    
    private byte[] imagem;

    @ManyToOne
    @JoinColumn(name = "tipodefilme_id")
    private TipoDeFilme tipoDeFilme;

    @ManyToMany
    @JoinTable(name = "FILME_DIRETOR",
            joinColumns = @JoinColumn(name = "filme_id"),
            inverseJoinColumns = @JoinColumn(name = "diretor_id")
    )
    private Collection<Diretor> diretores = new ArrayList<>();

    //orphanRemoval = true: utilizado para remover filhos (participações e filme_diretor) sem pai (filme) em caso de atualizaçao do filme (para um número de 'filhos' menor que o anterior)
    @OneToMany(mappedBy = "filme", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Participacao> participacoes = new ArrayList<>();

    @Builder
    public Filme(Integer id, String titulo, String genero, String duracao, TipoDeFilme tipoDeFilme) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.duracao = duracao;
        this.tipoDeFilme = tipoDeFilme;
    }

}
