package br.com.calculo.api.controller;

import br.com.calculo.api.domain.dto.PedidoDTO;
import br.com.calculo.api.domain.model.Pedido;
import br.com.calculo.api.domain.model.ResultadoCalculo;
import br.com.calculo.api.domain.repository.PedidoRepository;
import br.com.calculo.api.domain.service.CadastroPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    CadastroPedidoService cadastroPedidoService;

    @GetMapping
    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, BigDecimal> calcularPagamento(@RequestBody PedidoDTO pedidoDTO) {

    ResultadoCalculo resultado = cadastroPedidoService.salvar(pedidoDTO.getPedido());

    return resultado.dividirConta(pedidoDTO.getAmigos());

    }
}
