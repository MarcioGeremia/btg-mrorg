package desafio.btg.api.service;

import desafio.btg.api.dto.ItemDTO;
import desafio.btg.api.dto.PedidoDTO;
import desafio.btg.api.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PedidoServiceTest {

    private PedidoRepository repository;
    private PedidoService service;

    @BeforeEach
    void setUp() {
        repository = mock(PedidoRepository.class);
        service = new PedidoService(repository);
    }

    @Test
    void deveCalcularValorTotal() {
        Integer codigoPedido = 1;
        BigDecimal valorEsperado = new BigDecimal("120.50");

        when(repository.calcularValorTotal(codigoPedido)).thenReturn(valorEsperado);

        BigDecimal resultado = service.calcularValorTotal(codigoPedido);

        assertEquals(valorEsperado, resultado);
        verify(repository).calcularValorTotal(codigoPedido);
    }

    @Test
    void deveBuscarQuantidadePedidosPorCliente() {
        Integer codigoCliente = 2;
        Integer quantidadeEsperada = 4;

        when(repository.quantidadePedidosPorCliente(codigoCliente)).thenReturn(quantidadeEsperada);

        Integer resultado = service.quantidadePedidosPorCliente(codigoCliente);

        assertEquals(quantidadeEsperada, resultado);
        verify(repository).quantidadePedidosPorCliente(codigoCliente);
    }

    @Test
    void deveListarPedidosPorCliente() {
        Integer codigoCliente = 3;
        List<PedidoDTO> pedidos = List.of(
                PedidoDTO.builder()
                        .codigoPedido(10)
                        .codigoCliente(codigoCliente)
                        .itens(List.of(
                                ItemDTO.builder().produto("Produto A").quantidade(2).preco(new BigDecimal("50.00")).build()
                        ))
                        .build()
        );

        when(repository.listarPedidosPorCliente(codigoCliente)).thenReturn(pedidos);

        List<PedidoDTO> resultado = service.listarPedidosPorCliente(codigoCliente);

        assertEquals(pedidos, resultado);
        verify(repository).listarPedidosPorCliente(codigoCliente);
    }
}