package br.com.calculo.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ResultadoCalculo {

    private Pedido pedido;
    private List<Adicional> adicionais;
    private List<Desconto> descontos;

    public ResultadoCalculo(Pedido pedido, List<Adicional> adicionais, List<Desconto> descontos) {
        this.pedido = pedido;
        this.adicionais = adicionais;
        this.descontos = descontos;
    }

    public ResultadoCalculo(Pedido pedidoSalvo, List<Adicional> adicionais, Desconto desconto) {
        if (pedidoSalvo != null) {
            this.pedido = pedidoSalvo;
        } else {
            throw new IllegalArgumentException("O pedido salvo não pode ser nulo.");
        }

        if (adicionais != null) {
            this.adicionais = adicionais;
        } else {
            throw new IllegalArgumentException("A lista de adicionais não pode ser nula.");
        }

        if (desconto != null) {
            this.descontos = Collections.singletonList(desconto);
        } else {
            throw new IllegalArgumentException("O desconto não pode ser nulo.");
        }
    }

    public BigDecimal calcularPagamento() {
        if (this.pedido != null) {
            BigDecimal total = pedido.calcularTotal();

            total = aplicarAdicionais(total);

            total = aplicarDescontos(total);

            return total.setScale(2, RoundingMode.HALF_UP);
        } else {
            return BigDecimal.ZERO;
        }
    }

    private BigDecimal aplicarAdicionais(BigDecimal total) {
        for (Adicional adicional : adicionais) {
            if (adicional.isEscolhaCliente()) {
                total = adicional.aplicar(total);
            }
        }
        return total;
    }

    private BigDecimal aplicarDescontos(BigDecimal total) {
        for (Desconto desconto : descontos) {
            total = desconto.aplicar(total);
        }
        return total;
    }

    public Map<String, BigDecimal> dividirConta(Map<String, BigDecimal> amigos) {
        BigDecimal total = calcularPagamento();

        if (total.compareTo(BigDecimal.ZERO) != 0) {
            BigDecimal proporcaoTotal = total.divide(BigDecimal.valueOf(amigos.values().stream()
                    .mapToDouble(BigDecimal::doubleValue)
                    .sum()), 2, RoundingMode.HALF_UP);

            return amigos.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, entry ->
                            entry.getValue().multiply(proporcaoTotal).setScale(2, RoundingMode.HALF_UP)));
        } else {
            return amigos.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, entry -> BigDecimal.ZERO));
        }
    }
}