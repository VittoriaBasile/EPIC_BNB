package EPICODE.EPIC_BNB.services;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import EPICODE.EPIC_BNB.entities.Annuncio;
import EPICODE.EPIC_BNB.entities.Indirizzo;
import EPICODE.EPIC_BNB.entities.User;
import EPICODE.EPIC_BNB.entities.payload.AnnuncioCreatePayload;
import EPICODE.EPIC_BNB.exceptions.NotFoundException;
import EPICODE.EPIC_BNB.repositories.AnnuncioRepository;
import EPICODE.EPIC_BNB.repositories.IndirizzoRepository;

@Service
public class AnnuncioService {
	@Autowired
	AnnuncioRepository annuncioRepo;

	@Autowired
	UsersService usersService;
	@Autowired
	IndirizzoService indirizzoService;
	@Autowired
	IndirizzoRepository indirizzoRepo;

	public Annuncio create(AnnuncioCreatePayload a) {

		User user = usersService.findByEmail(a.getUserEmail());
		String encodeVia = a.getViaIndirizzo().replaceAll(" ", "-");
		Indirizzo existingIndirizzo = indirizzoRepo.findByViaAndCittàAndRegioneAndStato(encodeVia,
				a.getCittàIndirizzo(), a.getRegioneIndirizzo(), a.getStatoIndirizzo());
		Indirizzo newIndirizzo;
		if (existingIndirizzo != null) {

			newIndirizzo = existingIndirizzo;
		} else {

			newIndirizzo = new Indirizzo(encodeVia, a.getCittàIndirizzo(), a.getRegioneIndirizzo(),
					a.getStatoIndirizzo());
			indirizzoRepo.save(newIndirizzo);
		}
		String encodeNome = a.getNome().replaceAll(" ", "-");
		Annuncio newAnnuncio = new Annuncio(encodeNome, a.getPrezzo(), LocalDate.now(), a.getTipologia(),
				a.getPostiLetto(), a.getImage(), a.getServizi(), user, newIndirizzo);

		return annuncioRepo.save(newAnnuncio);

	}

	public Page<Annuncio> find(int page, int size, String sortBy) {
		if (size < 0)
			size = 10;
		if (size > 100)
			size = 100;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

		return annuncioRepo.findAll(pageable);
	}

	public List<Annuncio> findAnnunciByUserId(UUID idUser) {
		List<Annuncio> annunciPerUser = annuncioRepo.findByUserId(idUser);
		if (annunciPerUser.isEmpty())
			throw new NotFoundException("Non hai ancora pubblicato nessun annuncio");
		else
			return annunciPerUser;

	}

	public Optional<Annuncio> findAnnuncioByIdAndUserId(UUID annuncioId, UUID idUser) {
		Optional<Annuncio> annuncioPerUser = annuncioRepo.findByIdAndUserId(annuncioId, idUser);
		if (annuncioPerUser == null)
			throw new NotFoundException(
					"Nessun annuncio con id: " + annuncioId + " trovato per lo user con id: " + idUser);
		else
			return annuncioPerUser;

	}

	public List<Annuncio> findByPrezzi(double prezzoMinimo, double prezzoMassimo) {
		List<Annuncio> annunci = annuncioRepo.findByPrezzoRange(prezzoMinimo, prezzoMassimo);
		if (annunci.isEmpty()) {
			throw new NotFoundException(
					"Nessun annuncio trovato con prezzo compreso tra " + prezzoMinimo + " e " + prezzoMassimo);
		} else
			return annunci;
	}

	public Annuncio findById(UUID id) {
		return annuncioRepo.findById(id)
				.orElseThrow(() -> new NotFoundException("Annuncio con Id:" + id + "non trovato!!"));
	}

	public Annuncio findByNome(String nome) {
		return annuncioRepo.findByNome(nome)
				.orElseThrow(() -> new NotFoundException("Annuncio con nome:" + nome + "non trovato!!"));
	}

	public List<Annuncio> findByFilter(String filter) {
		List<Annuncio> annunci = annuncioRepo.findByFilter(filter);
		if (annunci.isEmpty()) {
			throw new NotFoundException("Nessun annuncio trovato");
		} else
			return annunci.stream().sorted(Comparator.comparing(Annuncio::getNome)).toList();
	}

	public Annuncio findByIdAndUpdate(UUID id, AnnuncioCreatePayload a) {
		Annuncio found = this.findById(id);
		if (found == null) {
			throw new NotFoundException("Nessun annuncio trovato");
		} else {
			found.setId(id);
			found.setNome(a.getNome());
			found.setPrezzo(a.getPrezzo());
			found.setTipologia(a.getTipologia());
			found.setPostiLetto(a.getPostiLetto());
			found.setImage(a.getImage());
			found.setServizi(a.getServizi());
			Indirizzo existingIndirizzo = indirizzoRepo.findByViaAndCittàAndRegioneAndStato(a.getViaIndirizzo(),
					a.getCittàIndirizzo(), a.getRegioneIndirizzo(), a.getStatoIndirizzo());
			Indirizzo newIndirizzo;
			if (existingIndirizzo != null) {

				newIndirizzo = existingIndirizzo;
			} else {

				newIndirizzo = new Indirizzo(a.getViaIndirizzo(), a.getCittàIndirizzo(), a.getRegioneIndirizzo(),
						a.getStatoIndirizzo());
				indirizzoRepo.save(newIndirizzo);
			}
			found.setIndirizzo(newIndirizzo);
			return annuncioRepo.save(found);
		}

	}

	public void findByIdAndDelete(UUID id) {
		Annuncio found = this.findById(id);
		if (found == null) {
			throw new NotFoundException("Nessun annuncio trovato");
		} else
			annuncioRepo.delete(found);
	}

}
