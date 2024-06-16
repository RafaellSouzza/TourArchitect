package br.com.infnet.TourArchitect.controller;

import br.com.infnet.TourArchitect.domain.Pacote;
import br.com.infnet.TourArchitect.domain.Usuario;
import br.com.infnet.TourArchitect.domain.Cartao;
import br.com.infnet.TourArchitect.repository.PacoteRepository;
import br.com.infnet.TourArchitect.repository.UsuarioRepository;
import br.com.infnet.TourArchitect.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/pacotes")
public class PacoteController {

    @Autowired
    private PacoteRepository pacoteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @PostMapping("/{pacoteId}/compra")
    public Pacote comprar(@PathVariable UUID pacoteId, @RequestParam UUID usuarioId, @RequestParam UUID cartaoId) {
        Pacote pacote = pacoteRepository.findById(pacoteId)
                .orElseThrow(() -> new RuntimeException("Pacote não encontrado"));
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Cartao cartao = cartaoRepository.findById(cartaoId)
                .orElseThrow(() -> new RuntimeException("Cartão não encontrado"));

        try {
            pacote.comprar(usuario, cartao);
            pacoteRepository.save(pacote);
            usuarioRepository.save(usuario);
            return pacote;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
