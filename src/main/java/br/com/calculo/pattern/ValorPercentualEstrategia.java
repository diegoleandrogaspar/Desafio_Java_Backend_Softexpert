package br.com.calculo.pattern;

import java.math.BigDecimal;

public class ValorPercentualEstrategia implements ValorAplicavel{

    @Override
    public BigDecimal aplicarValor(BigDecimal valorBase, BigDecimal percentual) {
        BigDecimal valorPercentual = valorBase.multiply(percentual.divide(BigDecimal.valueOf(100)));
        return valorBase.add(valorPercentual);
    }
}
