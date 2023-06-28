package EPICODE.EPIC_BNB.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import EPICODE.EPIC_BNB.entities.Annuncio;

@Repository
public interface AnnuncioRepository extends JpaRepository<Annuncio, UUID> {
	Optional<Annuncio> findByNome(String nome);

	@Query("SELECT a FROM Annuncio a WHERE a.alloggio.indirizzo.via LIKE '%'||:filter||'%' OR a.alloggio.indirizzo.città LIKE '%'||:filter||'%' OR a.alloggio.indirizzo.regione LIKE '%'||:filter||'%' OR a.alloggio.indirizzo.Stato LIKE '%'||:filter||'%' OR a.nome LIKE '%'||:filter||'%' ")
	List<Annuncio> findByFilter(@Param("filter") String filter);

	List<Annuncio> findByAlloggioUserId(UUID idUser);

}
