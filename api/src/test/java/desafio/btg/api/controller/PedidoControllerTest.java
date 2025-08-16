package desafio.btg.api.controller;

import desafio.btg.api.dto.PedidoDTO;
import desafio.btg.api.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PedidoControllerTest {

    private PedidoService pedidoService;
    private PedidoController pedidoController;

    @BeforeEach
    void setUp() {
        pedidoService = mock(PedidoService.class);
        pedidoController = new PedidoController(pedidoService);
    }

    @Test
    void deveConsultarValorTotal() {
        Integer codigoPedido = 1;
        BigDecimal valorEsperado = new BigDecimal("99.90");

        when(pedidoService.calcularValorTotal(codigoPedido)).thenReturn(valorEsperado);

        ResponseEntity<BigDecimal> response = pedidoController.consultarValorTotal(codigoPedido);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(valorEsperado, response.getBody());
        verify(pedidoService).calcularValorTotal(codigoPedido);
    }

    @Test
    void deveConsultarQuantidadePedidos() {
        Integer codigoCliente = 2;
        Integer quantidadeEsperada = 5;

        when(pedidoService.quantidadePedidosPorCliente(codigoCliente)).thenReturn(quantidadeEsperada);

        ResponseEntity<Integer> response = pedidoController.consultarQuantidadePedidos(codigoCliente);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(quantidadeEsperada, response.getBody());
        verify(pedidoService).quantidadePedidosPorCliente(codigoCliente);
    }

    @Test
    void deveListarPedidosPorCliente() {
        Integer codigoCliente = 3;
        List<PedidoDTO> pedidos = List.of(
                PedidoDTO.builder().codigoPedido(1).codigoCliente(codigoCliente).build(),
                PedidoDTO.builder().codigoPedido(2).codigoCliente(codigoCliente).build()
        );

        when(pedidoService.listarPedidosPorCliente(codigoCliente)).thenReturn(pedidos);

        ResponseEntity<List<PedidoDTO>> response = pedidoController.listarPedidosPorCliente(codigoCliente);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(pedidos, response.getBody());
        verify(pedidoService).listarPedidosPorCliente(codigoCliente);
    }
}