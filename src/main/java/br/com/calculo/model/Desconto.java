package br.com.calculo.model;

import br.com.calculo.pattern.ValorAplicavel;
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

    @NotNull
    @Column(name = "tipo_desconto")
    private String tipo;

    @Transient
    private ValorAplicavel estrategiaAplicacao;

    public Desconto() {
    }

    public Desconto(BigDecimal desconto, ValorAplicavel estrategiaAplicacao) {
        this.desconto = desconto;
        this.estrategiaAplicacao = estrategiaAplicacao;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public String getTipo() {
        return tipo;
    }

    public BigDecimal aplicar(BigDecimal valorTotal) {
        BigDecimal percentualDesconto = desconto.divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
        return valorTotal.subtract(valorTotal.multiply(percentualDesconto));
    }
}
