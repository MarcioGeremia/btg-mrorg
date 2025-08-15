package desafio.btg.ms.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Pedido {
    private Integer codigoPedido;
    private Integer codigoCliente;
    private List<Item> itens;
}