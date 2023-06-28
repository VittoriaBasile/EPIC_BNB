package EPICODE.EPIC_BNB.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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

import EPICODE.EPIC_BNB.entities.Annuncio;
import EPICODE.EPIC_BNB.entities.payload.AnnuncioCreatePayload;
import EPICODE.EPIC_BNB.exceptions.NotFoundException;
import EPICODE.EPIC_BNB.services.AnnuncioService;

@RestController
@RequestMapping("/annunci")
@PreAuthorize("hasAuthority('USER')")
public class AnnuncioController {
	@Autowired
	private AnnuncioService annuncioService;

	@GetMapping("")
	public Page<Annuncio> getAnnunci(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(required = false) String nome) {
		return annuncioService.find(page, size, sortBy);
	}

	@GetMapping("/filter")
	public List<Annuncio> getAnnunciByFilter(@RequestParam(required = true) String filter) {
		return annuncioService.findByFilter(filter);
	}

	@GetMapping("/userId")
	public List<Annuncio> getAnnunciByUser(@RequestParam("userId") UUID userId) {
		return annuncioService.findAnnuncioByUserId(userId);
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public Annuncio saveAnnuncio(@RequestBody @Validated AnnuncioCreatePayload body) {
		return annuncioService.create(body);
	}

	@PutMapping("/{annuncioId}")
	public Annuncio updateAnnuncio(@PathVariable UUID annuncioId, @RequestBody Annuncio body) {
		Annuncio annuncio = annuncioService.findByIdAndUpdate(annuncioId, body);
		if (annuncio == null) {
			throw new NotFoundException("Nessun annuncio trovato con id: " + annuncioId);
		} else
			return annuncio;
	}

	@DeleteMapping("/{annuncioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAnnuncio(@PathVariable UUID annuncioId) {
		annuncioService.findByIdAndDelete(annuncioId);
	}

}
