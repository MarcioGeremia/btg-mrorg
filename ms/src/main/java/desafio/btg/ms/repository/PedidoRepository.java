package desafio.btg.ms.repository;

import desafio.btg.ms.domain.Pedido;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class PedidoRepository {

    private final JdbcTemplate jdbcTemplate;

    public PedidoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Pedido findByCodigoPedido(Integer codigoPedido) {
        String sql = "SELECT codigoPedido FROM pedidos WHERE codigoPedido = ?";
        RowMapper<Pedido> mapper = (rs, rowNum) -> Pedido.builder()
                .codigoPedido(rs.getInt("codigoPedido"))
                .build();
        return jdbcTemplate.query(sql, mapper, codigoPedido)
                .stream()
                .findFirst()
                .orElse(null);
    }

    public void save(Pedido pedido) {
        String sql = "INSERT INTO pedidos (codigoPedido, codigoCliente) VALUES (?, ?)";
        jdbcTemplate.update(sql, pedido.getCodigoPedido(), pedido.getCodigoCliente());
    }

}
