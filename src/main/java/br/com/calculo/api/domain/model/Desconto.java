package br.com.calculo.api.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
public class Desconto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull
    @Column(name = "valor_desconto")
    private BigDecimal desconto;

    public Desconto() {
    }

    public Desconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public BigDecimal aplicar(BigDecimal valorTotal) {
        BigDecimal percentualDesconto = desconto.divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
        return valorTotal.subtract(valorTotal.multiply(percentualDesconto));
    }

}
