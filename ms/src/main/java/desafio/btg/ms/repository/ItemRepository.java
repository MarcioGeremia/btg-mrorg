package desafio.btg.ms.repository;

import desafio.btg.ms.domain.Item;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ItemRepository {

    private final JdbcTemplate jdbcTemplate;

    public ItemRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Item item) {
        String sql = "INSERT INTO itensPedido (codigoPedido, produto, quantidade, preco) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                item.getCodigoPedido(),
                item.getProduto(),
                item.getQuantidade(),
                item.getPreco());
    }
}