package desafio.btg.api.repository;

import desafio.btg.api.dto.ItemDTO;
import desafio.btg.api.dto.PedidoDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PedidoRepository {

    private final JdbcTemplate jdbcTemplate;

    public PedidoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public BigDecimal calcularValorTotal(Integer codigoPedido) {
        String sql = "SELECT SUM(i.quantidade * i.preco) " +
                "FROM itensPedido i " +
                "WHERE i.codigoPedido = ?";

        return jdbcTemplate.queryForObject(sql, BigDecimal.class, codigoPedido);
    }

    public Integer quantidadePedidosPorCliente(Integer codigoCliente) {
        String sql = "SELECT COUNT(*) FROM pedidos WHERE codigoCliente = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, codigoCliente);
    }

    public List<PedidoDTO> listarPedidosPorCliente(Integer codigoCliente) {
        String sql = "SELECT p.codigoPedido, p.codigoCliente, " +
                "i.codigoItem, i.produto, i.quantidade, i.preco " +
                "FROM pedidos p " +
                "JOIN itensPedido i ON p.codigoPedido = i.codigoPedido " +
                "WHERE p.codigoCliente = ? " +
                "ORDER BY p.codigoPedido, i.codigoItem";

        return jdbcTemplate.query(sql, rs -> {
            Map<Integer, PedidoDTO> pedidosMap = new LinkedHashMap<>();

            while (rs.next()) {
                int codigoPedido = rs.getInt("codigoPedido");

                PedidoDTO pedido = pedidosMap.computeIfAbsent(codigoPedido, id ->
                        {
                            try {
                                return PedidoDTO.builder()
                                        .codigoPedido(id)
                                        .codigoCliente(rs.getInt("codigoCliente"))
                                        .itens(new ArrayList<>())
                                        .build();
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        }
                );

                ItemDTO item = ItemDTO.builder()
                        .produto(rs.getString("produto"))
                        .quantidade(rs.getInt("quantidade"))
                        .preco(rs.getBigDecimal("preco"))
                        .build();

                pedido.getItens().add(item);
            }

            return new ArrayList<>(pedidosMap.values());
        }, codigoCliente);
    }

}
