package desafio.btg.ms.repository;

import desafio.btg.ms.domain.Pedido;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
public class PedidoRepository {

    private final JdbcTemplate jdbcTemplate;

    public PedidoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Pedido save(Pedido pedido) {
        String sql = "INSERT INTO pedidos (codigoCliente) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"codigoPedido"});
            ps.setInt(1, pedido.getCodigoCliente());
            return ps;
        }, keyHolder);

        int codigoPedido = keyHolder.getKey().intValue();
        pedido.setCodigoPedido(codigoPedido);
        return pedido;
    }
}