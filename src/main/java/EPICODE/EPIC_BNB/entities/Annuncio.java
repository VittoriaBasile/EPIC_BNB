package EPICODE.EPIC_BNB.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "annunci")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Annuncio {
	@Id
	@GeneratedValue
	private UUID id;
	private String nome;
	private double prezzo;
	private LocalDate dataInserimento;
	@OneToOne
	private Alloggio alloggio;
	@OneToMany(mappedBy = "annuncio")
	private List<Prenotazione> prenotazioni;

	public Annuncio(String nome, double prezzo, Alloggio alloggio) {
		super();
		this.nome = nome;
		this.prezzo = prezzo;
		this.alloggio = alloggio;
		this.dataInserimento = LocalDate.now();
	}

}
