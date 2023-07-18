package EPICODE.EPIC_BNB.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import EPICODE.EPIC_BNB.entities.Indirizzo;
import EPICODE.EPIC_BNB.exceptions.NotFoundException;
import EPICODE.EPIC_BNB.repositories.IndirizzoRepository;

@Service
public class IndirizzoService {
	@Autowired
	private IndirizzoRepository indirizzoRepo;

	public Indirizzo create(Indirizzo i) {

		Indirizzo newIndirizzo = new Indirizzo(i.getVia(), i.getCittà(), i.getStato());
		return indirizzoRepo.save(newIndirizzo);
	}

	public Page<Indirizzo> find(int page, int size, String sortBy) {
		if (size < 0)
			size = 10;
		if (size > 100)
			size = 100;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

		return indirizzoRepo.findAll(pageable);
	}

	public Indirizzo findById(UUID id) throws NotFoundException {
		return indirizzoRepo.findById(id)
				.orElseThrow(() -> new NotFoundException("Indirizzo con Id:" + id + "non trovato!!"));
	}

	public Indirizzo findByVia(String via) throws NotFoundException {
		return indirizzoRepo.findByVia(via)
				.orElseThrow(() -> new NotFoundException("Indirizzo con via:" + via + "non trovato!!"));
	}

	public Indirizzo findByIdAndUpdate(UUID id, Indirizzo i) throws NotFoundException {
		Indirizzo found = this.findById(id);

		found.setVia(i.getVia());
		found.setCittà(i.getCittà());
		found.setStato(i.getStato());

		return indirizzoRepo.save(found);
	}

	public void findByIdAndDelete(UUID id) throws NotFoundException {
		Indirizzo found = this.findById(id);
		indirizzoRepo.delete(found);
	}

}
