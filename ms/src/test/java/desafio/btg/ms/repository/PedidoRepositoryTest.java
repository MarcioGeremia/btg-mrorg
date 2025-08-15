package desafio.btg.ms.repository;

import desafio.btg.ms.domain.Pedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PedidoRepositoryTest {

    private JdbcTemplate jdbcTemplate;
    private PedidoRepository repository;

    @BeforeEach
    void setUp() {
        jdbcTemplate = mock(JdbcTemplate.class);
        repository = new PedidoRepository(jdbcTemplate);
    }

    @Test
    void deveCriarKeyHolder() {
        KeyHolder keyHolder = repository.criarKeyHolder();
        assertNotNull(keyHolder);
    }

    @Test
    void deveSalvarPedido() {
        Pedido pedido = Pedido.builder()
                .codigoCliente(123)
                .build();

        when(jdbcTemplate.update(any(), any(KeyHolder.class))).thenAnswer(invocation -> {
            KeyHolder kh = invocation.getArgument(1);
            kh.getKeyList().add(Map.of("codigoPedido", 456));
            return 1;
        });

        Pedido resultado = repository.save(pedido);

        assertEquals(456, resultado.getCodigoPedido());
        assertEquals(123, resultado.getCodigoCliente());
    }

    @Test
    void deveNaoPossuirIdGerado() {
        Pedido pedido = Pedido.builder()
                .codigoCliente(789)
                .build();

        when(jdbcTemplate.update(any(), any(KeyHolder.class))).thenReturn(1);

        Pedido resultado = repository.save(pedido);

        assertNull(resultado.getCodigoPedido());
        assertEquals(789, resultado.getCodigoCliente());
    }
}