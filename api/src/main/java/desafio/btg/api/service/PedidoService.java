package desafio.btg.api.service;

import desafio.btg.api.repository.PedidoRepository;
import desafio.btg.api.dto.PedidoDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository repository;

    public PedidoService(PedidoRepository repository) {
        this.repository = repository;
    }

    public BigDecimal calcularValorTotal(Integer codigoPedido) {
        return repository.calcularValorTotal(codigoPedido);
    }

    public Integer quantidadePedidosPorCliente(Integer codigoCliente) {
        return repository.quantidadePedidosPorCliente(codigoCliente);
    }

    public List<PedidoDTO> listarPedidosPorCliente(Integer codigoCliente) {
        return repository.listarPedidosPorCliente(codigoCliente);
    }

}
