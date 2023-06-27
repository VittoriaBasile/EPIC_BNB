package EPICODE.EPIC_BNB.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import EPICODE.EPIC_BNB.entities.Annuncio;

@Repository
public interface AnnuncioRepository extends JpaRepository<Annuncio, UUID> {
	Optional<Annuncio> findByNome(String nome);
}
