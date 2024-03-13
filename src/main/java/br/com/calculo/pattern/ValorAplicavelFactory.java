package br.com.calculo.pattern;

public enum ValorAplicavelFactory {

    FIXO {
        @Override
        public ValorAplicavel getEstrategia() {
            return new ValorFixoEstrategia();
        }
    },
    PERCENTUAL {
        @Override
        public ValorAplicavel getEstrategia() {
            return new ValorPercentualEstrategia();
        }
    };

    public abstract ValorAplicavel getEstrategia();


}
