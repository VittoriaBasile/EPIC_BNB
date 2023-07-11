package EPICODE.EPIC_BNB.entities;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "valutazioni")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Valutazione {
	@Id
	@GeneratedValue
	private UUID id;
	private int valore;
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnore
	private User user;
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnore
	private Annuncio annuncio;

	public Valutazione(int valore, User user, Annuncio annuncio) {

		this.valore = valore;
		this.user = user;
		this.annuncio = annuncio;
	}

}
