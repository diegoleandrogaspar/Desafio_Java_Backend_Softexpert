package br.com.calculo.api.domain.service;


import br.com.calculo.api.domain.model.Pedido;
import br.com.calculo.api.domain.model.ResultadoCalculo;
import br.com.calculo.api.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CadastroPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public ResultadoCalculo salvar(Pedido pedido, List<Map<String, Object>> valoresAdicionais, List<Map<String, Object>> descontos) {
      Pedido pedidoSalvo = pedidoRepository.save(pedido);

      return new ResultadoCalculo(pedidoSalvo,valoresAdicionais, descontos);
    }

}
