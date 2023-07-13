package EPICODE.EPIC_BNB.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	ValutazioneService valutazioneService;
	@Autowired
	UsersService usersService;

	@GetMapping("/nomeAnnuncio")
	public List<Valutazione> getValutazioneByAnnuncio(@RequestParam("nomeAnnuncio") String nomeAnnuncio) {
		List<Valutazione> valutazioniPerAnnuncio = valutazioneService.findValutazioniByAnnuncio(nomeAnnuncio);
		return valutazioniPerAnnuncio;
	}

	@GetMapping("/nome")
	public double getValutazioneMediaPerAnnuncio(@RequestParam("nome") String nomeAnnuncio) {
		return valutazioneService.getMediaValutazionePerAnnuncio(nomeAnnuncio);

	}

	@GetMapping("/{nomeAnnuncio}/user/{userEmail}")
	public Optional<Valutazione> findValutazioneByAnnuncioAndUser(@PathVariable("nomeAnnuncio") String nomeAnnuncio,
			@PathVariable("userEmail") String userEmail) {
		Optional<Valutazione> valutazione = valutazioneService.findValutazioniByAnnuncioAndUser(nomeAnnuncio,
				userEmail);
		return valutazione;
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public Valutazione saveValutazione(@RequestBody @Validated ValutazioneCreatePayload body) {
		Valutazione newValutazione = valutazioneService.create(body);
		return newValutazione;
	}

}
