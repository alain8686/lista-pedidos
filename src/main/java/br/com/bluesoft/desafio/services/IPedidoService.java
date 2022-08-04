package br.com.bluesoft.desafio.services;

import br.com.bluesoft.desafio.dto.OrdemDto;
import br.com.bluesoft.desafio.exception.NaoProdutoDisponivel;
import br.com.bluesoft.desafio.model.Pedido;

import java.util.List;

public interface IPedidoService {
    List<Pedido> criaPedidos(List<OrdemDto> ordens) throws NaoProdutoDisponivel;
}
