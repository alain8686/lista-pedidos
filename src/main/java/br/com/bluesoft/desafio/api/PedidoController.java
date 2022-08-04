package br.com.bluesoft.desafio.api;


import br.com.bluesoft.desafio.dto.OrdemDto;
import br.com.bluesoft.desafio.dto.PedidoDto;
import br.com.bluesoft.desafio.exception.NaoProdutoDisponivel;
import br.com.bluesoft.desafio.model.Pedido;
import br.com.bluesoft.desafio.repository.PedidoRepository;
import br.com.bluesoft.desafio.services.IPedidoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    private PedidoRepository pedidosRepository;

    private IPedidoService pedidoService;

    private ModelMapper modelMapper;

    @Autowired
    public PedidoController(PedidoRepository pedidosRepository, IPedidoService pedidoService, ModelMapper modelMapper){
        this.pedidosRepository = pedidosRepository;
        this.pedidoService = pedidoService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<PedidoDto> findAll() {
        List<Pedido> pedidos = new ArrayList<>();
        pedidosRepository.findAll().forEach(pedidos::add);

        System.out.println(pedidos);
        return pedidos.stream().map(pedido -> modelMapper.map(pedido, PedidoDto.class)).collect(Collectors.toList());
    }

    @PostMapping(value = "/novo_pedido")
    public List<PedidoDto> salvarPedido(@RequestBody List<OrdemDto> ordens){
        try{
            return pedidoService.criaPedidos(ordens).stream()
                    .map(pedido -> modelMapper.map(pedido, PedidoDto.class)).collect(Collectors.toList());
        }
        catch (NaoProdutoDisponivel ex){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Produto não disponivel", ex);
        }
    }
}
