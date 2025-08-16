package desafio.btg.api.repository;

import desafio.btg.api.dto.ItemDTO;
import desafio.btg.api.dto.PedidoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PedidoRepositoryTest {

    private JdbcTemplate jdbcTemplate;
    private PedidoRepository pedidoRepository;

    @BeforeEach
    void setUp() {
        jdbcTemplate = mock(JdbcTemplate.class);
        pedidoRepository = new PedidoRepository(jdbcTemplate);
    }

    @Test
    void deveCalcularValorTotal() {
        Integer codigoPedido = 1;
        BigDecimal valorEsperado = new BigDecimal("150.75");

        when(jdbcTemplate.queryForObject(anyString(), eq(BigDecimal.class), eq(codigoPedido)))
                .thenReturn(valorEsperado);

        BigDecimal resultado = pedidoRepository.calcularValorTotal(codigoPedido);

        assertEquals(valorEsperado, resultado);
        verify(jdbcTemplate).queryForObject(anyString(), eq(BigDecimal.class), eq(codigoPedido));
    }

    @Test
    void deveBuscarQuantidadePedidosPorCliente() {
        Integer codigoCliente = 2;
        Integer quantidadeEsperada = 3;

        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), eq(codigoCliente)))
                .thenReturn(quantidadeEsperada);

        Integer resultado = pedidoRepository.quantidadePedidosPorCliente(codigoCliente);

        assertEquals(quantidadeEsperada, resultado);
        verify(jdbcTemplate).queryForObject(anyString(), eq(Integer.class), eq(codigoCliente));
    }

    @Test
    void deveListarPedidosPorCliente() throws SQLException {
        Integer codigoCliente = 3;

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getInt("codigoPedido")).thenReturn(10, 10);
        when(resultSet.getInt("codigoCliente")).thenReturn(codigoCliente);
        when(resultSet.getString("produto")).thenReturn("Produto A", "Produto B");
        when(resultSet.getInt("quantidade")).thenReturn(2, 1);
        when(resultSet.getBigDecimal("preco")).thenReturn(new BigDecimal("50.00"), new BigDecimal("25.00"));

        ArgumentCaptor<ResultSetExtractor<List<PedidoDTO>>> captor = ArgumentCaptor.forClass(ResultSetExtractor.class);

        when(jdbcTemplate.query(anyString(), captor.capture(), eq(codigoCliente)))
                .thenAnswer(invocation -> captor.getValue().extractData(resultSet));

        List<PedidoDTO> resultado = pedidoRepository.listarPedidosPorCliente(codigoCliente);

        assertEquals(1, resultado.size());
        PedidoDTO pedido = resultado.get(0);
        assertEquals(10, pedido.getCodigoPedido());
        assertEquals(codigoCliente, pedido.getCodigoCliente());
        assertEquals(2, pedido.getItens().size());

        ItemDTO item1 = pedido.getItens().get(0);
        assertEquals("Produto A", item1.getProduto());
        assertEquals(2, item1.getQuantidade());
        assertEquals(new BigDecimal("50.00"), item1.getPreco());

        ItemDTO item2 = pedido.getItens().get(1);
        assertEquals("Produto B", item2.getProduto());
        assertEquals(1, item2.getQuantidade());
        assertEquals(new BigDecimal("25.00"), item2.getPreco());

        verify(jdbcTemplate).query(anyString(), any(ResultSetExtractor.class), eq(codigoCliente));
    }
}