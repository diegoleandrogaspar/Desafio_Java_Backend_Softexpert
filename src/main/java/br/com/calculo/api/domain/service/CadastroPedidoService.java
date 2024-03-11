package br.com.calculo.api.domain.service;


import br.com.calculo.api.domain.model.*;
import br.com.calculo.api.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CadastroPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public ResultadoCalculo salvar(Pedido pedidoRecebido) {
        Pedido pedidoSalvo = pedidoRepository.save(pedidoRecebido);
        return new ResultadoCalculo(pedidoSalvo, pedidoRecebido.getAdicional(), pedidoRecebido.getDesconto());
    }


    public Pedido criarPedidoAPartirDoMap(Map<String, Object> pedidoMap) {
        Pedido pedido = new Pedido();

        // Itens do pedido
        List<Map<String, Object>> items = (List<Map<String, Object>>) pedidoMap.get("items");
        if (items != null) {
            List<ItemPedido> itemPedidos = new ArrayList<>();
            for (Map<String, Object> item : items) {
                String name = (String) item.get("name");
                BigDecimal price = new BigDecimal(item.get("price").toString());
                ItemPedido itemPedido = new ItemPedido(name, price);
                itemPedidos.add(itemPedido);
            }
            pedido.setItems(itemPedidos);
        }

        // Adicionais
        List<Map<String, Object>> adicionais = (List<Map<String, Object>>) pedidoMap.get("adicionais");
        if (adicionais != null) {
            List<Adicional> adicionalList = new ArrayList<>();
            for (Map<String, Object> adicional : adicionais) {
                String nome = (String) adicional.get("name");
                BigDecimal valor = new BigDecimal(adicional.get("price").toString());
                String tipo = (String) adicional.get("type");
                Adicional adicionalObj = new Adicional(nome, valor, tipo);
                adicionalList.add(adicionalObj);
            }
            pedido.setAdicionais(adicionalList);
        }

        // Desconto
        Map<String, Object> descontoMap = (Map<String, Object>) pedidoMap.get("desconto");
        if (descontoMap != null) {
            BigDecimal valorDesconto = new BigDecimal(descontoMap.get("value").toString());
            Desconto desconto = new Desconto(valorDesconto);
            pedido.setDesconto(desconto);
        }

        return pedido;
    }

}
