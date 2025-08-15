package desafio.btg.ms.service;

import desafio.btg.ms.domain.Cliente;
import desafio.btg.ms.domain.Item;
import desafio.btg.ms.domain.Pedido;
import desafio.btg.ms.repository.ClienteRepository;
import desafio.btg.ms.repository.ItemRepository;
import desafio.btg.ms.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

class PedidoServiceTest {

    private ClienteRepository clienteRepository;
    private PedidoRepository pedidoRepository;
    private ItemRepository itemRepository;
    private PedidoService service;

    @BeforeEach
    void setUp() {
        clienteRepository = mock(ClienteRepository.class);
        pedidoRepository = mock(PedidoRepository.class);
        itemRepository = mock(ItemRepository.class);
        service = new PedidoService(clienteRepository, pedidoRepository, itemRepository);
    }

    @Test
    void deveSalvarPedidoComClienteExistenteEItens() {
        Pedido pedido = Pedido.builder()
                .codigoCliente(1)
                .itens(List.of(Item.builder().build()))
                .build();

        Cliente cliente = Cliente.builder().codigoCliente(1).build();
        Pedido pedidoSalvo = Pedido.builder().codigoPedido(99).codigoCliente(1).build();

        when(clienteRepository.findByCodigoCliente(1)).thenReturn(cliente);
        when(pedidoRepository.save(pedido)).thenReturn(pedidoSalvo);

        service.salvarPedido(pedido);

        verify(clienteRepository, never()).save(any());
        verify(pedidoRepository).save(pedido);
        verify(itemRepository).save(any(Item.class));
    }

    @Test
    void deveSalvarPedidoComClienteInexistenteSemItens() {
        Pedido pedido = Pedido.builder()
                .codigoCliente(2)
                .build();

        when(clienteRepository.findByCodigoCliente(2)).thenReturn(null);
        when(pedidoRepository.save(pedido)).thenReturn(Pedido.builder().codigoPedido(88).codigoCliente(2).build());

        service.salvarPedido(pedido);

        verify(clienteRepository).save(any(Cliente.class));
        verify(pedidoRepository).save(pedido);
        verify(itemRepository, never()).save(any());
    }
}
