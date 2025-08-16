package desafio.btg.ms.repository;

import desafio.btg.ms.domain.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ClienteRepositoryTest {

    private JdbcTemplate jdbcTemplate;
    private ClienteRepository repository;

    @BeforeEach
    void setUp() {
        jdbcTemplate = mock(JdbcTemplate.class);
        repository = new ClienteRepository(jdbcTemplate);
    }

    @Test
    void deveSalvarCliente() {
        Cliente cliente = Cliente.builder().codigoCliente(123).build();

        repository.save(cliente);

        verify(jdbcTemplate).update("INSERT INTO clientes (codigoCliente) VALUES (?)", 123);
    }

    @Test
    void deveBuscarClientePorCodigo() throws SQLException {
        ArgumentCaptor<RowMapper<Cliente>> captor =  ArgumentCaptor.forClass(RowMapper.class);
        when(jdbcTemplate.query(anyString(), captor.capture(), eq(42)))
                .thenReturn(List.of(Cliente.builder().codigoCliente(42).build()));

        repository.findByCodigoCliente(42);

        verify(jdbcTemplate).query(anyString(), captor.capture(), eq(42));

        RowMapper<Cliente> mapper = captor.getValue();
        ResultSet rs = mock(ResultSet.class);
        when(rs.getInt("codigoCliente")).thenReturn(42);
        Cliente clienteMapeado = mapper.mapRow(rs, 0);

        assertThat(clienteMapeado).isNotNull();
        assertThat(clienteMapeado.getCodigoCliente()).isEqualTo(42);
    }

    @Test
    void deveRetornarNullQuandoClienteNaoExiste() {
        ArgumentCaptor<RowMapper<Cliente>> captor = ArgumentCaptor.forClass(RowMapper.class);
        when(jdbcTemplate.query(anyString(), captor.capture(), eq(999))).thenReturn(List.of());

        Cliente resultado = repository.findByCodigoCliente(999);

        assertThat(resultado).isNull();
    }

}
