package desafio.btg.ms.repository;

import desafio.btg.ms.domain.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ItemRepositoryTest {

    private JdbcTemplate jdbcTemplate;
    private ItemRepository repository;

    @BeforeEach
    void setUp() {
        jdbcTemplate = mock(JdbcTemplate.class);
        repository = new ItemRepository(jdbcTemplate);
    }

    @Test
    void deveSalvarItem() {
        // Arrange
        Item item = Item.builder()
                .codigoPedido(101)
                .produto("Caneta Azul")
                .quantidade(3)
                .preco(2.50)
                .build();

        // Act
        repository.save(item);

        // Assert
        verify(jdbcTemplate).update(
                "INSERT INTO itensPedido (codigoPedido, produto, quantidade, preco) VALUES (?, ?, ?, ?)",
                101,
                "Caneta Azul",
                3,
                2.50
        );
    }
}
