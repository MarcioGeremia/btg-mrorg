package desafio.btg.ms.domain;

import lombok.Data;

@Data
public class Item {
    private int codigoPedido;
    private String produto;
    private int quantidade;
    private double preco;
}
