package br.com.infnet.TourArchitect.controller;

import br.com.infnet.TourArchitect.domain.Cartao;
import br.com.infnet.TourArchitect.domain.Plano;
import br.com.infnet.TourArchitect.domain.Usuario;
import br.com.infnet.TourArchitect.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @GetMapping
    public List<Usuario> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public Usuario create(@RequestBody Usuario usuario) {
        try {
            usuario.criar(usuario.getNome(),
                    usuario.getEmail(),
                    usuario.getSenha(),
                    usuario.getCpf(),
                    new Cartao(),
                    new Plano());
            return repository.save(usuario);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Usuario getById(@PathVariable UUID id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    @PutMapping("/{id}")
    public Usuario update(@PathVariable UUID id, @RequestBody Usuario usuario) {
        Usuario existingUsuario = repository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        existingUsuario.setNome(usuario.getNome());
        existingUsuario.setEmail(usuario.getEmail());
        existingUsuario.setSenha(usuario.getSenha());
        existingUsuario.setCpf(usuario.getCpf());
        existingUsuario.atualizarClassificacao();
        return repository.save(existingUsuario);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        repository.deleteById(id);
    }
}
