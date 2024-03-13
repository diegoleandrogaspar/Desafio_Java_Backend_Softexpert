package br.com.calculo.pattern;

import java.math.BigDecimal;

public interface ValorAplicavel {

    BigDecimal aplicarValor(BigDecimal valorBase, BigDecimal valorAdicional);
}
