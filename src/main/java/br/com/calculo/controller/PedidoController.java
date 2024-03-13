package br.com.calculo.controller;

import br.com.calculo.dto.PedidoDTO;
import br.com.calculo.service.CadastroPedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

    private final CadastroPedidoService cadastroPedidoService;

    public PedidoController(CadastroPedidoService cadastroPedidoService) {
        this.cadastroPedidoService = cadastroPedidoService;
    }

    @GetMapping
    public List<PedidoDTO> listar() {
        return cadastroPedidoService.listarTodos();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDTO criarPedido(@RequestBody PedidoDTO pedidoDTO) {
        return cadastroPedidoService.salvar(pedidoDTO);
    }
}


