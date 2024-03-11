package br.com.calculo.api.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
public class Adicional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotNull
    private BigDecimal valor;

    @NotBlank
    private String tipo;

    public Adicional() {
    }

    public Adicional(String nome, BigDecimal valor, String tipo) {
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getTipo() {
        return tipo;
    }



    public BigDecimal aplicar(BigDecimal total) {
        if ("real".equals(tipo)) {
            return total.add(valor);
        } else if ("percent".equals(tipo)) {
            BigDecimal percentual = valor.divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
            return total.add(total.multiply(percentual));
        }
        return total;
    }


}
