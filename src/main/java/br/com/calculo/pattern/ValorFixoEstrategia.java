package br.com.calculo.pattern;

import java.math.BigDecimal;

public class ValorFixoEstrategia implements ValorAplicavel {

    @Override
    public BigDecimal aplicarValor(BigDecimal valorBase, BigDecimal valorAdicional) {
        return valorBase.add(valorAdicional);
    }
}
