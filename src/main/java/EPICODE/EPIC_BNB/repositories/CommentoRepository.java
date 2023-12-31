package EPICODE.EPIC_BNB.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import EPICODE.EPIC_BNB.entities.Commento;

@Repository
public interface CommentoRepository extends JpaRepository<Commento, UUID> {

}
