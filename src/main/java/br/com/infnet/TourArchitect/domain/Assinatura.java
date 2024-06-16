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
public class Assinatura {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private boolean ativo;
    private LocalDateTime dtAssinatura;
    private double valor;
    private double desconto;
    @ManyToOne
    private Plano plano;
}
