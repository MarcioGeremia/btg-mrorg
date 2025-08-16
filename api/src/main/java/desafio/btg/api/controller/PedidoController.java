package desafio.btg.api.controller;

import desafio.btg.api.dto.PedidoDTO;
import desafio.btg.api.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping("/{codigoPedido}/valor-total")
    public ResponseEntity<BigDecimal> consultarValorTotal(@PathVariable Integer codigoPedido) {
        return ResponseEntity.ok(pedidoService.calcularValorTotal(codigoPedido));
    }

    @GetMapping("/cliente/{codigoCliente}/quantidade")
    public ResponseEntity<Integer> consultarQuantidadePedidos(@PathVariable Integer codigoCliente) {
        return ResponseEntity.ok(pedidoService.quantidadePedidosPorCliente(codigoCliente));
    }

    @GetMapping("/cliente/{codigoCliente}")
    public ResponseEntity<List<PedidoDTO>> listarPedidosPorCliente(@PathVariable Integer codigoCliente) {
        return ResponseEntity.ok(pedidoService.listarPedidosPorCliente(codigoCliente));
    }

}
