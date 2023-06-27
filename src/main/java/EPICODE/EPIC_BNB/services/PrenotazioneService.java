package EPICODE.EPIC_BNB.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import EPICODE.EPIC_BNB.entities.Annuncio;
import EPICODE.EPIC_BNB.entities.Prenotazione;
import EPICODE.EPIC_BNB.entities.User;
import EPICODE.EPIC_BNB.entities.payload.PrenotazioneCreatePayload;
import EPICODE.EPIC_BNB.exceptions.NotFoundException;
import EPICODE.EPIC_BNB.repositories.PrenotazioneRepository;

@Service
public class PrenotazioneService {
	@Autowired
	PrenotazioneRepository prenotazioneRepo;
	@Autowired
	UsersService usersService;
	@Autowired
	AnnuncioService annuncioService;

	public Prenotazione create(PrenotazioneCreatePayload p) {
		User user = usersService.findByEmail(p.getUserEmail());
		Annuncio annuncio = annuncioService.findByNome(p.getNomeAnnuncio());
		Prenotazione newPrenotazione = new Prenotazione(p.getDataInizio(), p.getNumeroOspiti(), user, annuncio);
		return prenotazioneRepo.save(newPrenotazione);
	}

	public Page<Prenotazione> find(int page, int size, String sortBy) {
		if (size < 0)
			size = 10;
		if (size > 100)
			size = 100;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

		return prenotazioneRepo.findAll(pageable);
	}

	public Prenotazione findById(UUID id) throws NotFoundException {
		return prenotazioneRepo.findById(id)
				.orElseThrow(() -> new NotFoundException("Prenotazione con Id:" + id + "non trovata!!"));
	}

	public void findByIdAndDelete(UUID id) throws NotFoundException {
		Prenotazione found = this.findById(id);
		prenotazioneRepo.delete(found);
	}

}
