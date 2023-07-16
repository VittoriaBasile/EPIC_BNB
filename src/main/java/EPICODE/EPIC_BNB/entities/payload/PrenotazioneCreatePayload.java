package EPICODE.EPIC_BNB.entities.payload;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrenotazioneCreatePayload {
	@NotNull(message = "La data è obbligatoria")
	LocalDate dataInizio;
	@NotNull(message = "La data è obbligatoria")
	LocalDate dataFine;
	@NotNull(message = "Il numero degli ospiti è obbligatoria")
	int numeroOspiti;
	@Email(message = "Non hai inserito un indirizzo email valido")
	String userEmail;
	@NotNull(message = "Il nome dell' annuncio è obbligatorio")
	String nomeAnnuncio;
}
