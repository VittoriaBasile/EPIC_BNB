package EPICODE.EPIC_BNB.entities.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommentoCreatePayload {
	@NotNull(message = "Il body del commento è obbligatorio")
	String bodyCommento;
	@Email(message = "Non hai inserito un indirizzo email valido")
	String userEmail;
	@NotNull(message = "L' annuncio è obbligatorio")
	String nomeAnnuncio;
}
