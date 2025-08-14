package desafio.btg.ms.domain;

import lombok.Data;

import java.util.List;

@Data
public class Pedido {
    private int codigoPedido;
    private int codigoCliente;
    private List<Item> itens;
}