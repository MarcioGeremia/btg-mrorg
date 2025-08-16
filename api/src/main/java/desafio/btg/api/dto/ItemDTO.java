package desafio.btg.api.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ItemDTO {
    private String produto;
    private Integer quantidade;
    private BigDecimal preco;
}
