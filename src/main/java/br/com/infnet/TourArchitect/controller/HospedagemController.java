package br.com.infnet.TourArchitect.controller;

import br.com.infnet.TourArchitect.domain.Hospedagem;
import br.com.infnet.TourArchitect.repository.HospedagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/hospedagens")
public class HospedagemController {

    @Autowired
    private HospedagemRepository repository;

    @GetMapping
    public List<Hospedagem> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public Hospedagem create(@RequestBody Hospedagem hospedagem) {
        return repository.save(hospedagem);
    }

    @GetMapping("/{id}")
    public Hospedagem getById(@PathVariable UUID id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Hospedagem não encontrada"));
    }

    @PutMapping("/{id}")
    public Hospedagem update(@PathVariable UUID id, @RequestBody Hospedagem hospedagem) {
        Hospedagem existingHospedagem = repository.findById(id).orElseThrow(() -> new RuntimeException("Hospedagem não encontrada"));
        existingHospedagem.setTipo(hospedagem.getTipo());
        existingHospedagem.setCapacidade(hospedagem.getCapacidade());
        existingHospedagem.setCusto(hospedagem.getCusto());
        return repository.save(existingHospedagem);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        repository.deleteById(id);
    }
}