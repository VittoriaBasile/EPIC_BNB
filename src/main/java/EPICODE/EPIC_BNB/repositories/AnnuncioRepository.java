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

	@Query("SELECT a FROM Annuncio a WHERE LOWER(a.indirizzo.via) LIKE '%'||LOWER(:filter)||'%' OR LOWER(a.indirizzo.città) LIKE '%'||LOWER(:filter)||'%' OR LOWER(a.indirizzo.regione) LIKE '%'||LOWER(:filter)||'%' OR LOWER(a.indirizzo.stato) LIKE '%'||LOWER(:filter)||'%' OR LOWER(a.nome) LIKE '%'||LOWER(:filter)||'%' ")
	List<Annuncio> findByFilter(@Param("filter") String filter);

	List<Annuncio> findByUserId(UUID idUser);

	Optional<Annuncio> findByIdAndUserId(UUID annuncioId, UUID userId);

	@Query("SELECT a FROM Annuncio a WHERE a.prezzo>= :prezzoMinimo AND a.prezzo <= :prezzoMassimo")
	List<Annuncio> findByPrezzoRange(@Param("prezzoMinimo") double prezzoMinimo,
			@Param("prezzoMassimo") double prezzoMassimo);

}
