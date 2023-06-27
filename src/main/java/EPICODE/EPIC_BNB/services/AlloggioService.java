package EPICODE.EPIC_BNB.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import EPICODE.EPIC_BNB.entities.Alloggio;
import EPICODE.EPIC_BNB.entities.Indirizzo;
import EPICODE.EPIC_BNB.entities.User;
import EPICODE.EPIC_BNB.entities.payload.AlloggioCreatePayload;
import EPICODE.EPIC_BNB.exceptions.NotFoundException;
import EPICODE.EPIC_BNB.repositories.AlloggioRepository;

@Service
public class AlloggioService {
	@Autowired
	AlloggioRepository alloggioRepo;
	@Autowired
	UsersService userService;
	@Autowired
	IndirizzoService indirizzoService;

	public Alloggio create(AlloggioCreatePayload a) {
		User user = userService.findByEmail(a.getUserEmail());
		Indirizzo newIndirizzo = new Indirizzo(a.getViaIndirizzo(), a.getCittàIndirizzo(), a.getRegioneIndirizzo(),
				a.getStatoIndirizzo());
		indirizzoService.create(newIndirizzo);
		Alloggio newAlloggio = new Alloggio(a.getNome(), a.getTipologia(), a.getPostiLetto(), a.getImage(),
				a.getServizi(), user, newIndirizzo);
		return alloggioRepo.save(newAlloggio);
	}

	public Page<Alloggio> find(int page, int size, String sortBy) {
		if (size < 0)
			size = 10;
		if (size > 100)
			size = 100;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

		return alloggioRepo.findAll(pageable);
	}

	public Alloggio findById(UUID id) throws NotFoundException {
		return alloggioRepo.findById(id)
				.orElseThrow(() -> new NotFoundException("Alloggio con Id:" + id + "non trovato!!"));
	}

	public Alloggio findByNome(String nome) throws NotFoundException {
		return alloggioRepo.findByNome(nome)
				.orElseThrow(() -> new NotFoundException("Alloggio con nome:" + nome + "non trovato!!"));
	}

	public Alloggio findByIdAndUpdate(UUID id, Alloggio a) throws NotFoundException {
		Alloggio found = this.findById(id);

		found.setId(id);
		found.setNome(a.getNome());
		found.setTipologia(a.getTipologia());
		found.setPostiLetto(a.getPostiLetto());
		found.setImage(a.getImage());
		found.setUser(userService.findByIdAndUpdate(a.getUser().getId(), a.getUser()));
		found.setIndirizzo(indirizzoService.findByIdAndUpdate(a.getIndirizzo().getId(), a.getIndirizzo()));
		return alloggioRepo.save(found);
	}

	public void findByIdAndDelete(UUID id) throws NotFoundException {
		Alloggio found = this.findById(id);
		alloggioRepo.delete(found);
	}

}
