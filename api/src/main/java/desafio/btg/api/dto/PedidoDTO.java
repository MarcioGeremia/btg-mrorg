package desafio.btg.api.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PedidoDTO {
    private Integer codigoPedido;
    private Integer codigoCliente;
    private List<ItemDTO> itens;
}
