package EPICODE.EPIC_BNB.entities;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private User user;
	@ManyToOne(fetch = FetchType.EAGER)
	private Annuncio annuncio;
	private double prezzo;

	public Prenotazione(LocalDate dataInizio, LocalDate dataFine, int numeroOspiti, User user, Annuncio annuncio) {

		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.numeroOspiti = numeroOspiti;
		this.user = user;
		this.annuncio = annuncio;
		this.prezzo = ((this.getAnnuncio().getPrezzo() * numeroOspiti) + (5 * numeroOspiti))
				* ChronoUnit.DAYS.between(dataInizio, dataFine);
	}

}
