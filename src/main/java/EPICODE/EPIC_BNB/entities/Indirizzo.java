package EPICODE.EPIC_BNB.entities;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "indirizzi")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Indirizzo {
	@Id
	@GeneratedValue
	private UUID id;
	private String via;
	private String città;
	private String stato;
	@OneToMany(mappedBy = "indirizzo")
	@JsonIgnore
	private List<Annuncio> annunci;

	public Indirizzo(String via, String città, String stato) {

		this.via = via;
		this.città = città;
		this.stato = stato;

	}

}
