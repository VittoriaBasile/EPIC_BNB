package EPICODE.EPIC_BNB.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
	private String descrizione;
	private double prezzo;
	private LocalDate dataInserimento;
	private TipoAlloggio tipologia;
	private int postiLetto;
	private List<String> image;
	private List<Servizi> servizi;
	@Column(length = 700)
	private String googleMaps;
	@ManyToOne

	private User user;
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Indirizzo indirizzo;
	@OneToMany(mappedBy = "annuncio")
	@JsonIgnore
	private List<Prenotazione> prenotazioni;
	@OneToMany(mappedBy = "annuncio", fetch = FetchType.EAGER)
	@JsonIgnore
	private List<Commento> commenti;
	@OneToMany(mappedBy = "annuncio", fetch = FetchType.EAGER)
	@JsonIgnore
	private List<Valutazione> valutazioni;

	public Annuncio(String nome, String descrizione, double prezzo, LocalDate dataInserimento, TipoAlloggio tipologia,
			int postiLetto, List<String> image, List<Servizi> servizi, String googleMaps, User user,
			Indirizzo indirizzo) {

		this.nome = nome;
		this.descrizione = descrizione;
		this.prezzo = prezzo;
		this.dataInserimento = dataInserimento;
		this.tipologia = tipologia;
		this.postiLetto = postiLetto;
		this.image = image;
		this.servizi = servizi;
		this.googleMaps = googleMaps;
		this.user = user;
		this.indirizzo = indirizzo;
	}

}
