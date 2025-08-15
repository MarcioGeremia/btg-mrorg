package desafio.btg.ms.listener;

import desafio.btg.ms.domain.Pedido;
import desafio.btg.ms.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class PedidoListenerTest {

    private PedidoService service;
    private PedidoListener listener;

    @BeforeEach
    void setUp() {
        service = mock(PedidoService.class);
        listener = new PedidoListener(service);
    }

    @Test
    void deveExecutarComSucesso() {
        // Arrange
        Pedido pedido = Pedido.builder().codigoPedido(123).build();

        // Act
        listener.receive(pedido);

        // Assert
        verify(service, times(1)).salvarPedido(pedido);
    }

}
