package br.com.calculo.model;

import br.com.calculo.pattern.ValorAplicavel;
import jakarta.persistence.*;
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

    private String tipo;

    @Transient
    private ValorAplicavel estrategiaAplicacao;

    public Adicional() {
    }

    public Adicional(@NotBlank String nome, @NotNull BigDecimal valor, String tipo) {
        this.nome = nome;
        this.valor = valor;
        this.tipo = tipo;
    }

    public Adicional(String nome, BigDecimal valor, ValorAplicavel estrategia) {
        this.nome = nome;
        this.valor = valor;
        this.estrategiaAplicacao = estrategia;
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

    public ValorAplicavel getEstrategiaAplicacao() {
        return estrategiaAplicacao;
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
