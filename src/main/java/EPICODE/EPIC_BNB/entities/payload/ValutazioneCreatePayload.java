package EPICODE.EPIC_BNB.entities.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ValutazioneCreatePayload {
	@NotNull(message = "Il valore della valutazione è obbligatorio")
	int valore;
	@Email(message = "Non hai inserito un indirizzo email valido")
	String userEmail;
	@NotNull(message = "L' annuncio è obbligatorio")
	String nomeAnnuncio;

}
