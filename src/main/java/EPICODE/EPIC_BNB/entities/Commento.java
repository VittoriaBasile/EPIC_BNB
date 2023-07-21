package EPICODE.EPIC_BNB.entities;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
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
@Table(name = "commenti")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Commento {
	@Id
	@GeneratedValue
	private UUID id;
	private LocalDate dataInserimento;
	@Column(length = 700)
	private String bodyCommento;

	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnoreProperties("commenti")
	private User user;
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnoreProperties("commenti")
	private Annuncio annuncio;

	public Commento(String bodyCommento, LocalDate dataInserimento, User user, Annuncio annuncio) {
		this.bodyCommento = bodyCommento;
		this.user = user;
		this.annuncio = annuncio;
		this.dataInserimento = dataInserimento;

	}

}
