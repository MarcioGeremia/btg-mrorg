package desafio.btg.ms.repository;

import desafio.btg.ms.domain.Cliente;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ClienteRepository {

    private final JdbcTemplate jdbcTemplate;

    public ClienteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Cliente findByCodigoCliente(Integer codigoCliente) {
        String sql = "SELECT codigoCliente FROM clientes WHERE codigoCliente = ?";
        RowMapper<Cliente> mapper = (rs, rowNum) -> Cliente.builder()
                .codigoCliente(rs.getInt("codigoCliente"))
                .build();
        return jdbcTemplate.query(sql, mapper, codigoCliente)
                .stream()
                .findFirst()
                .orElse(null);
    }

    public void save(Cliente cliente) {
        String sql = "INSERT INTO clientes (codigoCliente) VALUES (?)";
        jdbcTemplate.update(sql, cliente.getCodigoCliente());
    }

}
