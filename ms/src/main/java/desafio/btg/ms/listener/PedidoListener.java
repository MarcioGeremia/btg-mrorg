package desafio.btg.ms.listener;

import desafio.btg.ms.domain.Pedido;
import desafio.btg.ms.service.PedidoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class PedidoListener {

    private final PedidoService pedidoService;

    public PedidoListener(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @RabbitListener(queues = {"${queue.name}"})
    public void receive(@Payload Pedido pedido) {
        pedidoService.salvarPedido(pedido);
    }

}
