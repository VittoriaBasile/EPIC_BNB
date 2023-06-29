package EPICODE.EPIC_BNB.entities.payload;

import java.util.List;

import EPICODE.EPIC_BNB.entities.Servizi;
import EPICODE.EPIC_BNB.entities.TipoAlloggio;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlloggioCreatePayload {
	@NotNull(message = "Il nome è obbligatorio")
	String nome;
	@NotNull(message = "La tipologia dell' alloggio è obbligatoria")
	TipoAlloggio tipologia;
	@NotNull(message = "Il numero di posti letto è obbligatorio")
	int postiLetto;
	@NotNull(message = "Le immagini sono obbligatorie")
	List<String> image;
	@NotNull(message = "I servizi sono obbligatori")
	List<Servizi> servizi;
	@Email(message = "Non hai inserito un indirizzo email valido")
	String userEmail;
	@NotNull(message = "La via è obbligatoria")
	String viaIndirizzo;
	@NotNull(message = "La città è obbligatoria")
	String cittàIndirizzo;
	@NotNull(message = "La regione è obbligatoria")
	String regioneIndirizzo;
	@NotNull(message = "Lo Stato è obbligatorio")
	String StatoIndirizzo;

	public AlloggioCreatePayload(@NotNull(message = "Il nome è obbligatorio") String nome,
			@NotNull(message = "La tipologia dell' alloggio è obbligatoria") TipoAlloggio tipologia,
			@NotNull(message = "Il numero di posti letto è obbligatorio") int postiLetto,
			@NotNull(message = "Le immagini sono obbligatorie") List<String> image,
			@NotNull(message = "I servizi sono obbligatori") List<Servizi> servizi,
			@Email(message = "Non hai inserito un indirizzo email valido") String userEmail,
			@NotNull(message = "La via è obbligatoria") String viaIndirizzo,
			@NotNull(message = "La città è obbligatoria") String cittàIndirizzo,
			@NotNull(message = "La regione è obbligatoria") String regioneIndirizzo,
			@NotNull(message = "Lo Stato è obbligatorio") String statoIndirizzo) {
		super();
		this.nome = nome;
		this.tipologia = tipologia;
		this.postiLetto = postiLetto;
		this.image = image;
		this.servizi = servizi;
		this.userEmail = userEmail;
		this.viaIndirizzo = viaIndirizzo;
		this.cittàIndirizzo = cittàIndirizzo;
		this.regioneIndirizzo = regioneIndirizzo;
		this.StatoIndirizzo = statoIndirizzo;
	}

}
