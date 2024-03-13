package br.com.calculo.dto;

import br.com.calculo.model.Adicional;
import br.com.calculo.model.ItemPedido;

import java.math.BigDecimal;
import java.util.List;

public record PedidoDTO(List<ItemPedidoDTO> itens, List<AdicionalDTO> adicionais, List<DescontoDTO> descontos) {

    public record ItemPedidoDTO(String nome, BigDecimal preco) {
    }

    public record AdicionalDTO(String nome, BigDecimal preco, String tipo) {
    }

    public record DescontoDTO(BigDecimal valor, String tipo){
    }


}
