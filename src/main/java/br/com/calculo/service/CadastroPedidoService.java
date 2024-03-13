package br.com.calculo.service;


import br.com.calculo.dto.PedidoDTO;
import br.com.calculo.model.*;
import br.com.calculo.pattern.ValorAplicavelFactory;
import br.com.calculo.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CadastroPedidoService {

    private final PedidoRepository pedidoRepository;

    @Autowired
    public CadastroPedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

   public PedidoDTO salvar(PedidoDTO pedidoDTO) {
        Pedido pedido = criarPedido(pedidoDTO);
        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        return criarPedidoDTO(pedidoSalvo);
   }

    public List<PedidoDTO> listarTodos() {
      List<Pedido> pedidos = pedidoRepository.findAll();
      List<PedidoDTO> pedidosDTO = new ArrayList<>();

      for (Pedido pedido : pedidos) {
          pedidosDTO.add(criarPedidoDTO(pedido));
      }
       return pedidosDTO;
    }

   private Pedido criarPedido(PedidoDTO pedidoDTO) {
        Pedido pedido = new Pedido();

        // Processar itens
       pedidoDTO.itens().forEach(item ->
               pedido.adicionarItem(item.nome(), item.preco()))
       );


       //Processar adicionais
       pedidoDTO.adicionais().forEach(adicional ->
               pedido.adicionarAdicional(new Adicional(adicional.nome(), adicional.preco(), adicional.tipo()))
       );

       // Processar descontos, se houver
       if (pedidoDTO.descontos() != null) {
           pedidoDTO.descontos().forEach(desconto ->
                   pedido.adicionarDesconto(new Desconto(desconto.valor(), ValorAplicavelFactory.getEstrategia(desconto.tipo())))
       );
   }
       return pedido;
   }

   private PedidoDTO criarPedidoDTO(Pedido pedido) {

       List<PedidoDTO.ItemPedidoDTO> itensDTO = new ArrayList<>();
       List<PedidoDTO.AdicionalDTO> adicionaisDTO = new ArrayList<>();
       List<PedidoDTO.DescontoDTO> descontosDTO = new ArrayList<>();

       // Converter itens do pedido para ItemPedidoDTO
       pedido.getItens().forEach(item ->
               itensDTO.add(new PedidoDTO.ItemPedidoDTO(item.getNome(), item.getValor()))
       );

       // Converter adicionais do pedido para AdicionalDTO
       pedido.getAdicionais().forEach(adicional ->
               adicionaisDTO.add(new PedidoDTO.AdicionalDTO(adicional.getNome(), adicional.getValor(), adicional.getTipo()))
       );

       // Converter descontos do pedido para DescontoDTO
       if (pedido.getDescontos() != null) {
           descontosDTO.add(new PedidoDTO.DescontoDTO(pedido.getDescontos().getValor(), pedido.getDescontos().getTipo()));
       }

       return new PedidoDTO(itensDTO, adicionaisDTO, descontosDTO);
   }
    }


}
