package EPICODE.EPIC_BNB.services;

import java.time.temporal.ChronoUnit;
import java.util.List;
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
import EPICODE.EPIC_BNB.exceptions.BadRequestException;
import EPICODE.EPIC_BNB.exceptions.NotFoundException;
import EPICODE.EPIC_BNB.repositories.AnnuncioRepository;
import EPICODE.EPIC_BNB.repositories.PrenotazioneRepository;

@Service
public class PrenotazioneService {
	@Autowired
	PrenotazioneRepository prenotazioneRepo;
	@Autowired
	UsersService usersService;
	@Autowired
	AnnuncioService annuncioService;
	@Autowired
	AnnuncioRepository annuncioRepo;

	public Prenotazione create(PrenotazioneCreatePayload p) {
		User user = usersService.findByEmail(p.getUserEmail());
		String nomeAnnuncio = p.getNomeAnnuncio().replaceAll(" ", "-");
		Annuncio annuncio = annuncioService.findByNome(nomeAnnuncio);

		List<Prenotazione> prenotazioni = prenotazioneRepo.findByDataInizioAndAnnuncio(p.getDataInizio(), annuncio);

		if (!prenotazioni.isEmpty()) {
			throw new BadRequestException("Impossibile prenotare in questa data, alloggio già prenotato");
		}

		Prenotazione newPrenotazione = new Prenotazione(p.getDataInizio(), p.getDataFine(), p.getNumeroOspiti(), user,
				annuncio);

		double prezzoNotte = newPrenotazione.getAnnuncio().getPrezzo();
		int notti = (int) ChronoUnit.DAYS.between(p.getDataInizio(), p.getDataFine());
		int tassaSoggiorno = (5 * p.getNumeroOspiti());
		double prezzoNotti = prezzoNotte * notti;
		double prezzoTotale = prezzoNotti * p.getNumeroOspiti();
		prezzoTotale += tassaSoggiorno;

		newPrenotazione.setPrezzo(prezzoTotale);

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

	public Prenotazione findById(UUID id) {
		return prenotazioneRepo.findById(id)
				.orElseThrow(() -> new NotFoundException("Prenotazione con Id:" + id + "non trovata!!"));
	}

	public List<Prenotazione> findByUser(User user) {
		List<Prenotazione> prenotazioni = prenotazioneRepo.findByUser(user);
		if (prenotazioni.isEmpty()) {
			throw new NotFoundException("Nessuna prenotazione trovata per questo utente: " + user);
		} else
			return prenotazioni;
	}

	public Prenotazione findByIdAndUser(UUID prenotazioneId, User user) {
		Prenotazione prenotazione = prenotazioneRepo.findByIdAndUser(prenotazioneId, user);
		if (prenotazione == null) {
			throw new NotFoundException(
					"Nessuna prenotazione trovata con id: " + prenotazioneId + "per questo user: " + user);
		} else
			return prenotazione;

	}

	public List<Prenotazione> findByAnnuncio(String nomeAnnuncio) {
		Annuncio annuncio = annuncioService.findByNome(nomeAnnuncio);
		List<Prenotazione> prenotazioniDataInizioAndAnnuncio = prenotazioneRepo.findByAnnuncio(annuncio);
		if (prenotazioniDataInizioAndAnnuncio.isEmpty()) {
			throw new NotFoundException("Nessuna prenotazione trovata per l' annuncio: " + nomeAnnuncio);
		} else
			return prenotazioniDataInizioAndAnnuncio;
	}

	public void findByIdAndDelete(UUID id) {
		Prenotazione found = this.findById(id);
		if (found == null) {
			throw new NotFoundException("Nessuna prenotazione trovata");
		} else {
			Annuncio annuncio = found.getAnnuncio();

			annuncio.getPrenotazioni().remove(found);
			found.setAnnuncio(null);
			annuncioRepo.save(annuncio);
			prenotazioneRepo.delete(found);
		}

	}

}
