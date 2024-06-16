package br.com.infnet.TourArchitect.repository;

import br.com.infnet.TourArchitect.domain.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CartaoRepository extends JpaRepository<Cartao, UUID> {
}
