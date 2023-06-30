package EPICODE.EPIC_BNB.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import EPICODE.EPIC_BNB.entities.Prenotazione;
import EPICODE.EPIC_BNB.entities.User;
import EPICODE.EPIC_BNB.entities.payload.PrenotazioneCreatePayload;
import EPICODE.EPIC_BNB.services.PrenotazioneService;
import EPICODE.EPIC_BNB.services.UsersService;

@RestController
@RequestMapping("/prenotazioni")
@PreAuthorize("hasAuthority('USER')")
public class PrenotazioneController {
	@Autowired
	PrenotazioneService prenotazioneService;
	@Autowired
	UsersService usersService;

//TESTATA
	@GetMapping("")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Page<Prenotazione> getPrenotazioni(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {

		return prenotazioneService.find(page, size, sortBy);

	}

//TESTATA
	@GetMapping("/me")
	@PreAuthorize("hasAuthority('USER')")
	public List<Prenotazione> getPrenotazioniPerUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User user = usersService.findByUsername(username);
		return prenotazioneService.findByUser(user);
	}

//TESTATA
	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	// @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
	public Prenotazione savePrenotazione(@RequestBody @Validated PrenotazioneCreatePayload body) {
		return prenotazioneService.create(body);
	}

	// TESTATA
	@DeleteMapping("/me/{prenotazioneId}")
	@PreAuthorize("hasAnyAuthority('USER')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable UUID prenotazioneId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User user = usersService.findByUsername(username);
		Prenotazione prenotazione = prenotazioneService.FindByIdAndUser(prenotazioneId, user);
		if (prenotazione.getDataInizio().minusDays(2).isAfter(LocalDate.now())
				|| prenotazione.getDataInizio().minusDays(2).isEqual(LocalDate.now())) {
			prenotazione.setPrezzo(prenotazione.getPrezzo() * 30 / 100);
		}

		prenotazioneService.findByIdAndDelete(prenotazioneId);
	}

}
