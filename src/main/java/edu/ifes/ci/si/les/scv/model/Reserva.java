package edu.ifes.ci.si.les.scv.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import edu.ifes.ci.si.les.scv.model.enums.StatusReserva;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date data;

    private Integer status;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "fita_id")
    private Fita fita;

    @Builder
    public Reserva(Integer id, Date data, StatusReserva status, Cliente cliente, Fita fita) {
        this.id = id;
        this.data = data;
		this.status = (status==null) ? null : status.getCod();
        this.cliente = cliente;
        this.fita = fita;
    }
    
    public StatusReserva getStatus() {
		return StatusReserva.toEnum(status);
	}

	public void setStatus(StatusReserva status) {
		this.status = status.getCod();
	}

}
