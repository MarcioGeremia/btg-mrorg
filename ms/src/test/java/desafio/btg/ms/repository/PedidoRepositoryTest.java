package desafio.btg.ms.repository;

import desafio.btg.ms.domain.Pedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
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
    void deveBuscarPorCodigoPedido() throws SQLException {
        ArgumentCaptor<RowMapper<Pedido>> captor =  ArgumentCaptor.forClass(RowMapper.class);
        when(jdbcTemplate.query(anyString(), captor.capture(), eq(42)))
                .thenReturn(List.of(Pedido.builder().codigoPedido(42).build()));

        repository.findByCodigoPedido(42);

        verify(jdbcTemplate).query(anyString(), captor.capture(), eq(42));

        RowMapper<Pedido> mapper = captor.getValue();
        ResultSet rs = mock(ResultSet.class);
        when(rs.getInt("codigoPedido")).thenReturn(42);
        Pedido pedido = mapper.mapRow(rs, 0);

        assertThat(pedido).isNotNull();
        assertThat(pedido.getCodigoPedido()).isEqualTo(42);
    }

    @Test
    void deveSalvarPedido() {
        Pedido cliente = Pedido.builder().codigoPedido(123).codigoCliente(123).build();

        repository.save(cliente);

        verify(jdbcTemplate).update("INSERT INTO pedidos (codigoPedido, codigoCliente) VALUES (?, ?)",
                123, 123);
    }
}