package br.com.infnet.TourArchitect.repository;

import br.com.infnet.TourArchitect.domain.Pacote;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PacoteRepository extends JpaRepository<Pacote, UUID> {
}
