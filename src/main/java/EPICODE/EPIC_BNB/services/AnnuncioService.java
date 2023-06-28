package EPICODE.EPIC_BNB.services;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import EPICODE.EPIC_BNB.entities.Alloggio;
import EPICODE.EPIC_BNB.entities.Annuncio;
import EPICODE.EPIC_BNB.entities.Indirizzo;
import EPICODE.EPIC_BNB.entities.User;
import EPICODE.EPIC_BNB.entities.payload.AlloggioCreatePayload;
import EPICODE.EPIC_BNB.entities.payload.AnnuncioCreatePayload;
import EPICODE.EPIC_BNB.exceptions.NotFoundException;
import EPICODE.EPIC_BNB.repositories.AnnuncioRepository;

@Service
public class AnnuncioService {
	@Autowired
	AnnuncioRepository annunciorepo;
	@Autowired
	AlloggioService alloggioService;
	@Autowired
	UsersService usersService;
	@Autowired
	IndirizzoService indirizzoService;

	public Annuncio create(AnnuncioCreatePayload a) {
		User user = usersService.findByEmail(a.getUserEmail());
		Indirizzo newIndirizzo = new Indirizzo(a.getViaIndirizzo(), a.getCittàIndirizzo(), a.getRegioneIndirizzo(),
				a.getStatoIndirizzo());
		indirizzoService.create(newIndirizzo);
		AlloggioCreatePayload alloggioPayload = new AlloggioCreatePayload(a.getNome(), a.getTipologia(),
				a.getPostiLetto(), a.getImage(), a.getServizi(), a.getUserEmail(), newIndirizzo.getVia(),
				newIndirizzo.getCittà(), newIndirizzo.getRegione(), newIndirizzo.getStato());
		alloggioService.create(alloggioPayload);
		Alloggio alloggio = alloggioService.findByNome(a.getNome());
		Annuncio newAnnuncio = new Annuncio(a.getNomeAnnuncio(), a.getPrezzo(), alloggio);
		return annunciorepo.save(newAnnuncio);
	}

	public Page<Annuncio> find(int page, int size, String sortBy) {
		if (size < 0)
			size = 10;
		if (size > 100)
			size = 100;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

		return annunciorepo.findAll(pageable);
	}

	public List<Annuncio> findAnnuncioByUserId(UUID idUser) {
		List<Annuncio> annunciPerUser = annunciorepo.findByAlloggioUserId(idUser);
		if (annunciPerUser.isEmpty())
			throw new NotFoundException("Non hai ancora pubblicato nessun annuncio");
		else
			return annunciPerUser;

	}

	public Annuncio findById(UUID id) throws NotFoundException {
		return annunciorepo.findById(id)
				.orElseThrow(() -> new NotFoundException("Annuncio con Id:" + id + "non trovato!!"));
	}

	public Annuncio findByNome(String nome) throws NotFoundException {
		return annunciorepo.findByNome(nome)
				.orElseThrow(() -> new NotFoundException("Annuncio con nome:" + nome + "non trovato!!"));
	}

	public List<Annuncio> findByFilter(String filter) {
		List<Annuncio> annunci = annunciorepo.findByFilter(filter);
		if (annunci.isEmpty()) {
			throw new NotFoundException("Nessun annuncio trovato");
		} else
			return annunci.stream().sorted(Comparator.comparing(Annuncio::getNome)).toList();
	}

	public Annuncio findByIdAndUpdate(UUID id, Annuncio a) throws NotFoundException {
		Annuncio found = this.findById(id);

		found.setId(id);
		found.setNome(a.getNome());
		found.setPrezzo(a.getPrezzo());
		found.setAlloggio(alloggioService.findByIdAndUpdate(a.getAlloggio().getId(), a.getAlloggio()));

		return annunciorepo.save(found);
	}

	public void findByIdAndDelete(UUID id) throws NotFoundException {
		Annuncio found = this.findById(id);
		annunciorepo.delete(found);
	}

}
