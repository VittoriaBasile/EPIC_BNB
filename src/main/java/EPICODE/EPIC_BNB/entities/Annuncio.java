package EPICODE.EPIC_BNB.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	private TipoAlloggio tipologia;
	private int postiLetto;
	private List<String> image;
	private List<Servizi> servizi;
	@ManyToOne
	@JsonIgnore
	private User user;
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Indirizzo indirizzo;
	@OneToMany(mappedBy = "annuncio")
	@JsonIgnore
	private List<Prenotazione> prenotazioni;

	public Annuncio(String nome, double prezzo, LocalDate dataInserimento, TipoAlloggio tipologia, int postiLetto,
			List<String> image, List<Servizi> servizi, User user, Indirizzo indirizzo) {
		super();
		this.nome = nome;
		this.prezzo = prezzo;
		this.dataInserimento = dataInserimento;
		this.tipologia = tipologia;
		this.postiLetto = postiLetto;
		this.image = image;
		this.servizi = servizi;
		this.user = user;
		this.indirizzo = indirizzo;
	}

}
