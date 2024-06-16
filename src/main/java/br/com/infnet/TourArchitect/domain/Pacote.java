package br.com.infnet.TourArchitect.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Data
@Entity
public class Pacote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String destino;
    private LocalDateTime dataPartida;
    private LocalDateTime dataRetorno;
    @ManyToOne
    private TipoTransporte tipoTransporte;
    @ManyToOne
    private Hospedagem hospedagem;
    private double valor;

    public void comprar(Usuario usuario, Cartao cartao) throws Exception {
        double valorFinal = this.valor;

        if (usuario.getAssinatura() != null && usuario.getAssinatura().isAtivo()) {
            double desconto = usuario.getAssinatura().getDesconto();
            valorFinal = this.valor - (this.valor * (desconto / 100));
        }

        cartao.criarTransacao(valorFinal, "Compra de pacote: " + this.destino);

        usuario.getPacotes().add(this);
    }
}
