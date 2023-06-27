package EPICODE.EPIC_BNB.entities;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "prenotazioni")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Prenotazione {
	@Id
	@GeneratedValue
	private UUID id;
	private LocalDate dataInizio;
	private LocalDate dataFine;
	private int numeroOspiti;
	@ManyToOne
	private User user;
	@ManyToOne
	private Annuncio annuncio;

	public Prenotazione(LocalDate dataInizio, int numeroOspiti, User user, Annuncio annuncio) {

		this.dataInizio = dataInizio;
		this.numeroOspiti = numeroOspiti;
		this.user = user;
		this.annuncio = annuncio;
		this.dataFine = dataInizio.plusDays(7);
	}

}
