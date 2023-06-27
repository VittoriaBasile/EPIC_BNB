package EPICODE.EPIC_BNB.entities;

import java.util.List;
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
@Table(name = "alloggi")
@Getter
@Setter
@ToString
@NoArgsConstructor

public class Alloggio {
	@Id
	@GeneratedValue
	private UUID id;
	private String nome;
	private TipoAlloggio tipologia;
	private int postiLetto;
	private List<String> image;
	private List<String> servizi;
	@ManyToOne
	private User user;
	@ManyToOne
	private Indirizzo indirizzo;

	public Alloggio(String nome, TipoAlloggio tipologia, int postiLetto, List<String> image, List<String> servizi,
			User user, Indirizzo indirizzo) {

		this.nome = nome;
		this.tipologia = tipologia;
		this.postiLetto = postiLetto;
		this.image = image;
		this.servizi = servizi;
		this.user = user;
		this.indirizzo = indirizzo;
	}

}
