package br.com.infnet.TourArchitect.repository;

import br.com.infnet.TourArchitect.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
}