package desafio.btg.ms.repository;

import desafio.btg.ms.domain.Pedido;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class PedidoRepository {

    private final JdbcTemplate jdbcTemplate;

    public PedidoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    protected KeyHolder criarKeyHolder() {
        return new GeneratedKeyHolder();
    }

    public Pedido save(Pedido pedido) {
        String sql = "INSERT INTO pedidos (codigoCliente) VALUES (?)";
        KeyHolder keyHolder = criarKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, pedido.getCodigoCliente());
            return ps;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();
        if (generatedId != null) {
            pedido.setCodigoPedido(generatedId.intValue());
        }

        return pedido;
    }

}
