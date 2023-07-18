package EPICODE.EPIC_BNB.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import EPICODE.EPIC_BNB.entities.Indirizzo;

@Repository
public interface IndirizzoRepository extends JpaRepository<Indirizzo, UUID> {
	Optional<Indirizzo> findByVia(String via);

	Indirizzo findByViaAndCittàAndStato(String via, String città, String stato);
}
