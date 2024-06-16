package br.com.infnet.TourArchitect.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Base64;
import java.util.InputMismatchException;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Data
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nome;
    private String senha;
    private String cpf;
    private String email;
    private String classificacao;

    @ManyToOne
    private Assinatura assinatura;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cartao> cartoes = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pacote> pacotes = new ArrayList<>();

    public void criar(String nome, String email, String senha, String cpf, Cartao cartao, Plano plano) throws Exception {
        if (!isCpfValido(cpf)) {
            throw new Exception("CPF está inválido");
        }

        this.setCpf(cpf);
        this.setNome(nome);
        this.setEmail(email);
        this.setSenha(encodeSenha(senha));
        this.setAssinatura(assinatura);

        if (assinatura != null && assinatura.getPlano() != null) {
            cartao.criarTransacao(assinatura.getValor(), assinatura.getPlano().getDescricao());
        } else {
            cartao.criarTransacao(0.0, "Sem plano associado");
        }
        this.cartoes.add(cartao);

        atualizarClassificacao();
    }

    public void atualizarClassificacao() {
        int numeroViagens = this.pacotes.size();
        if (numeroViagens > 5) {
            this.classificacao = "Prêmio";
        } else if (numeroViagens >= 4) {
            this.classificacao = "Ouro";
        } else {
            this.classificacao = "Básico";
        }
    }

    private String encodeSenha(String senha) {
        return Base64.getEncoder().encodeToString(senha.getBytes());
    }

    private boolean isCpfValido(String CPF) {
        if (CPF.equals("00000000000") || CPF.equals("11111111111") || CPF.equals("22222222222") ||
                CPF.equals("33333333333") || CPF.equals("44444444444") || CPF.equals("55555555555") ||
                CPF.equals("66666666666") || CPF.equals("77777777777") || CPF.equals("88888888888") ||
                CPF.equals("99999999999") || (CPF.length() != 11))
            return false;

        char dig10, dig11;
        int sm, i, r, num, peso;

        try {
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            dig10 = (r == 10 || r == 11) ? '0' : (char) (r + 48);

            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            dig11 = (r == 10 || r == 11) ? '0' : (char) (r + 48);

            return (dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10));
        } catch (InputMismatchException erro) {
            return false;
        }
    }
}
