package EPICODE.EPIC_BNB.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import EPICODE.EPIC_BNB.entities.Alloggio;

@Repository
public interface AlloggioRepository extends JpaRepository<Alloggio, UUID> {
	Optional<Alloggio> findByNome(String nome);
}
