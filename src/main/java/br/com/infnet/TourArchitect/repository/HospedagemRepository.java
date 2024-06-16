package br.com.infnet.TourArchitect.repository;

import br.com.infnet.TourArchitect.domain.Hospedagem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface HospedagemRepository extends JpaRepository<Hospedagem, UUID> {
}
