package EPICODE.EPIC_BNB.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import EPICODE.EPIC_BNB.entities.Annuncio;
import EPICODE.EPIC_BNB.entities.User;
import EPICODE.EPIC_BNB.entities.Valutazione;

@Repository
public interface ValutazioneRepository extends JpaRepository<Valutazione, UUID> {
	Optional<Valutazione> findByAnnuncioAndUser(Annuncio annuncio, User user);

}
