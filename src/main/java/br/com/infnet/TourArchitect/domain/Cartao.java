package br.com.infnet.TourArchitect.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Data
@Entity
public class Cartao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String numero;
    private Boolean ativo;
    private LocalDate validade;
    private double limite;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transacao> transacoes = new ArrayList<>();

    private final int TRANSACAO_INTERVALO_TEMPO = 2;
    private final int TRANSACAO_QUANTIDADE_ALTA_FREQUENCIA = 3;
    private final int TRANSACAO_MERCHANT_DUPLICADAS = 2;

    public void criarTransacao(double valor, String descricao) throws Exception {
        if (!this.ativo) {
            throw new Exception("Cartão não está ativo");
        }

        Transacao transacao = new Transacao();
        transacao.setDescricao(descricao);
        transacao.setValor(valor);
        transacao.setDtTransacao(LocalDateTime.now());

        if (!this.validarLimite(transacao)) {
            throw new Exception("Cartão não possui limite para esta transação");
        }

        if (!this.validarTransacao(transacao)) {
            throw new Exception("Transação inválida");
        }

        this.setLimite(this.getLimite() - transacao.getValor());
        transacao.setCodigoAutorizacao(UUID.randomUUID());
        this.transacoes.add(transacao);
    }

    private boolean validarTransacao(Transacao transacao) {
        List<Transacao> ultimasTransacoes = this.transacoes.stream()
                .filter(x -> x.getDtTransacao().isAfter(LocalDateTime.now().minusMinutes(this.TRANSACAO_INTERVALO_TEMPO)))
                .toList();

        if (ultimasTransacoes.size() >= this.TRANSACAO_QUANTIDADE_ALTA_FREQUENCIA)
            return false;

        List<Transacao> transacoesRepetidas = ultimasTransacoes.stream()
                .filter(x -> x.getDescricao().equals(transacao.getDescricao()) && x.getValor() == transacao.getValor())
                .toList();

        return transacoesRepetidas.size() < this.TRANSACAO_MERCHANT_DUPLICADAS;
    }

    private boolean validarLimite(Transacao transacao) {
        return this.limite > transacao.getValor();
    }
}
