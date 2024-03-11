package br.com.calculo.api.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultadoCalculo {

    private Pedido pedido;
    private List<Map<String, Object>> valoresAdicionais;
    private List<Map<String, Object>> descontos;

    public ResultadoCalculo(Pedido pedido, List<Map<String, Object>> valoresAdicionais, List<Map<String, Object>> descontos) {
        this.pedido = pedido;
        this.valoresAdicionais = valoresAdicionais;
        this.descontos = descontos;
    }

    public BigDecimal calcularPagamento() {
        BigDecimal total = pedido.calcularTotal();

        for (Map<String, Object> adicional: valoresAdicionais) {
            String tipo = (String) adicional.get("tipo");
            BigDecimal valor = new BigDecimal(adicional.get("valor").toString());

            if ("reais".equals(tipo)){
                total = total.add(valor);
            }
            else if ("percentual".equals(tipo)) {
                BigDecimal percentual = valor.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                total = total.subtract(percentual.multiply(total));
            }
        }
        return total;
    }

    public Map<String, BigDecimal> dividirConta(Map<String, BigDecimal> amigos) {
        BigDecimal total = calcularPagamento();
        BigDecimal proporcaoTotal = total.divide(BigDecimal.valueOf(amigos.values().stream()
                .mapToDouble(BigDecimal::doubleValue)
                .sum()), 2, RoundingMode.HALF_UP);

        Map<String, BigDecimal> pagamentos = new HashMap<>();
        for (Map.Entry<String, BigDecimal> entry : amigos.entrySet()) {
            String amigo = entry.getKey();
            BigDecimal valorGasto = entry.getValue();
            pagamentos.put(amigo, valorGasto.multiply(proporcaoTotal).setScale(2, RoundingMode.HALF_UP));

        }

        return pagamentos;
    }

}
