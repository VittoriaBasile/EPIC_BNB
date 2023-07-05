package EPICODE.EPIC_BNB.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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

import EPICODE.EPIC_BNB.entities.Commento;
import EPICODE.EPIC_BNB.entities.User;
import EPICODE.EPIC_BNB.entities.payload.CommentoCreatePayload;
import EPICODE.EPIC_BNB.exceptions.NotFoundException;
import EPICODE.EPIC_BNB.services.CommentoService;
import EPICODE.EPIC_BNB.services.UsersService;

@RestController
@RequestMapping("/commenti")
@PreAuthorize("hasAnyAuthority('USER','ADMIN')")
public class CommentoController {

	@Autowired
	CommentoService commentoService;
	@Autowired
	UsersService usersService;

	@GetMapping("/nomeAnnuncio")
	public List<Commento> getCommentiByAnnuncio(@RequestParam("nomeAnnuncio") String nomeAnnuncio) {
		List<Commento> commentiPerAnnuncio = commentoService.findCommentiByAnnuncio(nomeAnnuncio);
		return commentiPerAnnuncio;
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public Commento saveCommento(@RequestBody @Validated CommentoCreatePayload body) {
		Commento newCommento = commentoService.create(body);
		return newCommento;
	}

	@PutMapping("/me/{commentoId}")
	public Commento updateCommento(@PathVariable UUID commentoId, @RequestBody CommentoCreatePayload body) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User user = usersService.findByUsername(username);
		Commento singoloCommento = commentoService.findById(commentoId);
		if (singoloCommento == null) {
			throw new NotFoundException("Nessun commento trovato con id: " + commentoId);
		} else {
			Commento commentoModificato = commentoService.findByIdAndUpdate(commentoId, body);
			return commentoModificato;
		}

	}

	@DeleteMapping("/me/{commentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCommento(@PathVariable UUID commentoId) {
		commentoService.findByIdAndDelete(commentoId);
	}
}
