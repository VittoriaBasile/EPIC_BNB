package EPICODE.EPIC_BNB.services;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import EPICODE.EPIC_BNB.entities.Annuncio;
import EPICODE.EPIC_BNB.entities.Commento;
import EPICODE.EPIC_BNB.entities.User;
import EPICODE.EPIC_BNB.entities.payload.CommentoCreatePayload;
import EPICODE.EPIC_BNB.exceptions.NotFoundException;
import EPICODE.EPIC_BNB.repositories.AnnuncioRepository;
import EPICODE.EPIC_BNB.repositories.CommentoRepository;

@Service
public class CommentoService {
	@Autowired
	CommentoRepository commentoRepo;
	@Autowired
	UsersService usersService;
	@Autowired
	AnnuncioService annuncioService;
	@Autowired
	AnnuncioRepository annuncioRepo;

	public Commento create(CommentoCreatePayload c) {

		User user = usersService.findByEmail(c.getUserEmail());

		String encodeNomeAnnuncio = c.getNomeAnnuncio().replaceAll(" ", "-");
		Annuncio annuncio = annuncioService.findByNome(encodeNomeAnnuncio);

		Commento newCommento = new Commento(c.getBodyCommento(), LocalDate.now(), user, annuncio);

		return commentoRepo.save(newCommento);

	}

	public List<Commento> findCommentiByAnnuncio(String nomeAnnuncio) {
		String encodeNomeAnnuncio = nomeAnnuncio.replaceAll(" ", "-");
		Annuncio annuncio = annuncioService.findByNome(encodeNomeAnnuncio);
		List<Commento> commenti = annuncio.getCommenti();

		if (commenti.isEmpty())
			throw new NotFoundException("Nessun commento trovato per l' annuncio: " + nomeAnnuncio);
		else
			return commenti;

	}

	public Commento findById(UUID id) {
		return commentoRepo.findById(id)
				.orElseThrow(() -> new NotFoundException("Commento con Id:" + id + "non trovato!!"));
	}

	public Commento findByIdAndUpdate(UUID id, CommentoCreatePayload c) {
		Commento found = this.findById(id);
		if (found == null) {
			throw new NotFoundException("Nessun commento trovato");
		} else {
			found.setId(id);
			found.setBodyCommento(c.getBodyCommento());

			return commentoRepo.save(found);
		}

	}

	public void findByIdAndDelete(UUID id) {
		Commento found = this.findById(id);
		if (found == null) {
			throw new NotFoundException("Nessun commento trovato");
		} else {
			Annuncio annuncio = found.getAnnuncio();

			annuncio.getCommenti().remove(found);
			found.setAnnuncio(null);
			annuncioRepo.save(annuncio);
			commentoRepo.delete(found);
		}

	}

}
