package desafio.btg.ms.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Item {
    private Integer codigoPedido;
    private String produto;
    private Integer quantidade;
    private Double preco;
}
