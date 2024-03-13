package br.com.calculo.pattern;

public class ValorAplicavelFactory {

    public static ValorAplicavel getEstragegia(String tipo) {

        switch (tipo) {
            case "fixo":
                return new ValorFixoEstrategia();
            case "percentual":
                return new ValorPercentualEstrategia();
            default:
                throw new IllegalArgumentException("Tipo de estratégia desconhecido: " + tipo);
        }
    }
}
