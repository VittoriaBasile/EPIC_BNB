package EPICODE.EPIC_BNB.entities.payload;

import java.util.List;

import EPICODE.EPIC_BNB.entities.Servizi;
import EPICODE.EPIC_BNB.entities.TipoAlloggio;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AnnuncioCreatePayload {
	@NotNull(message = "Il nome è obbligatorio")
	String nome;
	@NotNull(message = "La descrizione è obbligatoria")
	String descrizione;
	@NotNull(message = "Il prezzo è obbligatorio")
	double prezzo;
	@NotNull(message = "La tipologia dell' alloggio è obbligatoria")
	TipoAlloggio tipologia;
	@NotNull(message = "Il numero di posti letto è obbligatorio")
	int postiLetto;
	@NotNull(message = "Le immagini sono obbligatorie")
	List<String> image;
	@NotNull(message = "I servizi sono obbligatori")
	List<Servizi> servizi;
	@NotNull(message = "L' indirizzo googleMaps è obbligatorio")
	String googleMaps;
	@Email(message = "Non hai inserito un indirizzo email valido")
	String userEmail;
	@NotNull(message = "La via è obbligatoria")
	String viaIndirizzo;
	@NotNull(message = "La città è obbligatoria")
	String cittàIndirizzo;
	@NotNull(message = "La regione è obbligatoria")

	String statoIndirizzo;
}
