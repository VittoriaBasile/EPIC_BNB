package EPICODE.EPIC_BNB.controllers;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;

import EPICODE.EPIC_BNB.entities.Annuncio;
import EPICODE.EPIC_BNB.entities.TipoAlloggio;
import EPICODE.EPIC_BNB.entities.User;
import EPICODE.EPIC_BNB.entities.payload.AnnuncioCreatePayload;
import EPICODE.EPIC_BNB.exceptions.NotFoundException;
import EPICODE.EPIC_BNB.services.AnnuncioService;
import EPICODE.EPIC_BNB.services.UsersService;

@RestController
@RequestMapping("/annunci")
@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")

public class AnnuncioController {
	@Autowired
	private AnnuncioService annuncioService;
	@Autowired
	UsersService usersService;

//TESTATA
	@GetMapping("")
	public Page<Annuncio> getAnnunci(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(required = false) String nome) {
		return annuncioService.find(page, size, sortBy);
	}

	// TESTATA
	@GetMapping("/filter")
	public List<Annuncio> getAnnunciByFilter(@RequestParam(required = true) String filter) {
		String replacedFilter = filter.replace(" ", "-");
		String encodedFilter = UriUtils.encode(replacedFilter, StandardCharsets.UTF_8);
		return annuncioService.findByFilter(encodedFilter);
	}

	@GetMapping("/prezzo")
	public List<Annuncio> getAnnunciByPrezzoRange(@RequestParam("prezzoMinimo") double prezzoMinimo,
			@RequestParam("prezzoMassimo") double prezzoMassimo) {

		List<Annuncio> annunci = annuncioService.findByPrezzi(prezzoMinimo, prezzoMassimo);
		return annunci;
	}

	@GetMapping("/tipo")
	public List<Annuncio> getAnnunciByTipoAlloggio(@RequestParam("tipo") TipoAlloggio tipo) {

		List<Annuncio> annunci = annuncioService.findAnnunciByTipoAlloggio(tipo);
		return annunci;
	}

	// TESTATA
	@GetMapping("/me")
	public List<Annuncio> getAnnunciByUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User user = usersService.findByUsername(username);
		return annuncioService.findAnnunciByUserId(user.getId());
	}

	// TESTATA
	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public Annuncio saveAnnuncio(@RequestBody @Validated AnnuncioCreatePayload body) {

		return annuncioService.create(body);
	}

	// TESTATA
	@PutMapping("/me/{annuncioId}")
	public Annuncio updateAnnuncio(@PathVariable UUID annuncioId, @RequestBody AnnuncioCreatePayload body) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User user = usersService.findByUsername(username);
		Optional<Annuncio> singoloAnnuncioPerUser = annuncioService.findAnnuncioByIdAndUserId(annuncioId, user.getId());
		if (singoloAnnuncioPerUser == null) {
			throw new NotFoundException("Nessun annuncio trovato con id: " + annuncioId);
		} else {
			Annuncio annuncioModificato = annuncioService.findByIdAndUpdate(annuncioId, body);
			return annuncioModificato;
		}

	}

	// TESTATA
	@DeleteMapping("/me/{annuncioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAnnuncio(@PathVariable UUID annuncioId) {
		annuncioService.findByIdAndDelete(annuncioId);
	}

}
