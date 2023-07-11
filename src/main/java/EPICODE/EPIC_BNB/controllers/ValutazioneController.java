package EPICODE.EPIC_BNB.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import EPICODE.EPIC_BNB.entities.Valutazione;
import EPICODE.EPIC_BNB.entities.payload.ValutazioneCreatePayload;
import EPICODE.EPIC_BNB.services.UsersService;
import EPICODE.EPIC_BNB.services.ValutazioneService;

@RestController
@RequestMapping("/valutazioni")
@PreAuthorize("hasAnyAuthority('USER','ADMIN')")
public class ValutazioneController {

	@Autowired
	ValutazioneService valatazioneService;
	@Autowired
	UsersService usersService;

	@GetMapping("/nomeAnnuncio")
	public List<Valutazione> getValutazioneByAnnuncio(@RequestParam("nomeAnnuncio") String nomeAnnuncio) {
		List<Valutazione> valutazioniPerAnnuncio = valatazioneService.findValutazioniByAnnuncio(nomeAnnuncio);
		return valutazioniPerAnnuncio;
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public Valutazione saveValutazione(@RequestBody @Validated ValutazioneCreatePayload body) {
		Valutazione newValutazione = valatazioneService.create(body);
		return newValutazione;
	}

}
