package EPICODE.EPIC_BNB.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import EPICODE.EPIC_BNB.entities.Annuncio;
import EPICODE.EPIC_BNB.entities.Prenotazione;
import EPICODE.EPIC_BNB.entities.User;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, UUID> {
	List<Prenotazione> findByDataInizioAndAnnuncio(LocalDate dataInizio, Annuncio annuncio);

	List<Prenotazione> findByUser(User user);

	Prenotazione findByIdAndUser(UUID prenotazioneId, User user);
}
