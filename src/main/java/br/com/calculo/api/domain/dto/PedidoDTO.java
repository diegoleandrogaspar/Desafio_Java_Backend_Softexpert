package br.com.calculo.api.domain.dto;

import br.com.calculo.api.domain.model.Pedido;

import java.math.BigDecimal;
import java.util.Map;

public class PedidoDTO {

    private Pedido pedido;
    private Map<String, BigDecimal> amigos;

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Map<String, BigDecimal> getAmigos() {
        return amigos;
    }

    public void setAmigos(Map<String, BigDecimal> amigos) {
        this.amigos = amigos;
    }


}
