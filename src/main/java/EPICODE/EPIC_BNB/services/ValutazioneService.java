package EPICODE.EPIC_BNB.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import EPICODE.EPIC_BNB.entities.Annuncio;
import EPICODE.EPIC_BNB.entities.User;
import EPICODE.EPIC_BNB.entities.Valutazione;
import EPICODE.EPIC_BNB.entities.payload.ValutazioneCreatePayload;
import EPICODE.EPIC_BNB.exceptions.BadRequestException;
import EPICODE.EPIC_BNB.exceptions.NotFoundException;
import EPICODE.EPIC_BNB.repositories.ValutazioneRepository;

@Service
public class ValutazioneService {
	@Autowired
	ValutazioneRepository valutazioneRepo;
	@Autowired
	UsersService usersService;
	@Autowired
	AnnuncioService annuncioService;

	public Valutazione create(ValutazioneCreatePayload v) {

		User user = usersService.findByEmail(v.getUserEmail());

		String encodeNomeAnnuncio = v.getNomeAnnuncio().replaceAll(" ", "-");
		Annuncio annuncio = annuncioService.findByNome(encodeNomeAnnuncio);

		Valutazione newValutazione = new Valutazione(v.getValore(), user, annuncio);
		Optional<Valutazione> valutazione = valutazioneRepo.findByAnnuncioAndUser(annuncio, user);
		if (!valutazione.isEmpty()) {
			throw new BadRequestException("Hai già fornito una valutazione per questo annuncio");
		}

		return valutazioneRepo.save(newValutazione);

	}

	public List<Valutazione> findValutazioniByAnnuncio(String nomeAnnuncio) {
		String encodeNomeAnnuncio = nomeAnnuncio.replaceAll(" ", "-");
		Annuncio annuncio = annuncioService.findByNome(encodeNomeAnnuncio);
		List<Valutazione> valutazioni = annuncio.getValutazioni();

		if (valutazioni.isEmpty())
			throw new NotFoundException("Nessuna valutazione trovata per l' annuncio: " + nomeAnnuncio);
		else
			return valutazioni;

	}

//	public double getMediaValutazionePerAnnuncio(String nomeAnnuncio) {
//		String encodeNomeAnnuncio = nomeAnnuncio.replaceAll(" ", "-");
//		Annuncio annuncio = annuncioService.findByNome(encodeNomeAnnuncio);
//		List<Valutazione> valutazioni = annuncio.getValutazioni();
//		double tot = 0;
//
//		for (Valutazione valutazione : valutazioni) {
//			tot += valutazione.getValore();
//		}
//		if (tot == 0) {
//			return tot;
//
//		} else {
//			return tot / valutazioni.size();
//		}

// }

	public Optional<Valutazione> findValutazioniByAnnuncioAndUser(String nomeAnnuncio, String userEmail) {
		String encodeNomeAnnuncio = nomeAnnuncio.replaceAll(" ", "-");
		Annuncio annuncio = annuncioService.findByNome(encodeNomeAnnuncio);
		User user = usersService.findByEmail(userEmail);
		Optional<Valutazione> valutazione = valutazioneRepo.findByAnnuncioAndUser(annuncio, user);

		if (valutazione == null)
			throw new NotFoundException("Nessuna valutazione trovata per l' annuncio: " + nomeAnnuncio
					+ "dallu user conn email " + userEmail);
		else
			return valutazione;

	}
}
