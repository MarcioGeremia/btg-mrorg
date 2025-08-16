package desafio.btg.ms.service;

import desafio.btg.ms.domain.Cliente;
import desafio.btg.ms.domain.Item;
import desafio.btg.ms.domain.Pedido;
import desafio.btg.ms.repository.ClienteRepository;
import desafio.btg.ms.repository.ItemRepository;
import desafio.btg.ms.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.isNull;

@Service
public class PedidoService {

    private final ClienteRepository clienteRepository;
    private final PedidoRepository pedidoRepository;
    private final ItemRepository itemRepository;

    public PedidoService(ClienteRepository clienteRepository, PedidoRepository pedidoRepository, ItemRepository itemRepository) {
        this.clienteRepository = clienteRepository;
        this.pedidoRepository = pedidoRepository;
        this.itemRepository = itemRepository;
    }

    @Transactional
    public void salvarPedido(Pedido pedido) {
        Cliente cliente = clienteRepository.findByCodigoCliente(pedido.getCodigoCliente());
        if(isNull(cliente))
            clienteRepository.save(Cliente.builder().codigoCliente(pedido.getCodigoCliente()).build());
        Pedido pedidoBase = pedidoRepository.findByCodigoPedido(pedido.getCodigoPedido());
        if(isNull(pedidoBase)) {
            pedidoRepository.save(pedido);
            if (pedido.getItens() != null) {
                for (Item item : pedido.getItens()) {
                    item.setCodigoPedido(pedido.getCodigoPedido());
                    itemRepository.save(item);
                }
            }
        }
    }
}