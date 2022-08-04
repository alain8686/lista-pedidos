package br.com.bluesoft.desafio.services;

import br.com.bluesoft.desafio.dao.IFornecedorDao;
import br.com.bluesoft.desafio.dto.EstoqueFornecedorDetalheDto;
import br.com.bluesoft.desafio.dto.EstoqueFornecedorDto;
import br.com.bluesoft.desafio.dto.OrdemDto;
import br.com.bluesoft.desafio.exception.NaoProdutoDisponivel;
import br.com.bluesoft.desafio.model.Fornecedor;
import br.com.bluesoft.desafio.model.Pedido;
import br.com.bluesoft.desafio.model.PedidoItem;
import br.com.bluesoft.desafio.model.Produto;
import br.com.bluesoft.desafio.repository.FornecedorRepository;
import br.com.bluesoft.desafio.repository.PedidoRepository;
import br.com.bluesoft.desafio.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PedidoService implements IPedidoService{
    private PedidoRepository pedidosRepository;

    private ProdutoRepository produtoRepository;

    private IFornecedorDao fornecedorDao;

    private FornecedorRepository fornecedorRepository;

    public PedidoService(FornecedorRepository fornecedorRepository, IFornecedorDao fornecedorDao,
                         ProdutoRepository produtoRepository, PedidoRepository pedidosRepository){
        this.fornecedorRepository = fornecedorRepository;
        this.fornecedorDao = fornecedorDao;
        this.produtoRepository = produtoRepository;
        this.pedidosRepository = pedidosRepository;
    }

    public List<Pedido> criaPedidos(List<OrdemDto> ordens) throws NaoProdutoDisponivel{
        List<Pedido> melhoresOferta = new ArrayList<>();
        for(OrdemDto ordem: ordens)
            if(ordem.getQuantidade() > 0)
                melhoresOferta.add(melhorOfertaPedido(ordem));
        if(melhoresOferta.size() == 0)
            throw new NaoProdutoDisponivel("Pedido vazio");

        List<Pedido> pedidos = new ArrayList<>();
        Map<String, List<Pedido>> gruposPedidos = melhoresOferta.stream()
                .collect(Collectors.groupingBy(pedido -> pedido.getFornecedor().getCnpj()));
        for(List<Pedido> grupo: gruposPedidos.values()){
            Pedido pedido = new Pedido();
            pedido.setFornecedor(grupo.get(0).getFornecedor());
            pedido.setItens(new ArrayList<>());

            for(Pedido melhorPedido: grupo)
                pedido.getItens().add(melhorPedido.getItens().get(0));
            pedidos.add(pedido);
        }

        for(Pedido pedido: pedidos)
            pedidosRepository.save(pedido);

        return pedidos;
    }

    private Pedido melhorOfertaPedido(OrdemDto ordem) throws NaoProdutoDisponivel{
        Produto produto = produtoRepository.findById(ordem.getGtin()).get();
        EstoqueFornecedorDto[] estoqueFornecedores = fornecedorDao.fornecedoreOrdem(produto);

        if(ordem.getQuantidade() == null)
            ordem.setQuantidade(Integer.MIN_VALUE);

        Pedido melhorPedido = new Pedido();
        melhorPedido.setItens(new ArrayList<>());
        for(EstoqueFornecedorDto estoque: estoqueFornecedores){
            String cnpj = estoque.getCnpj();

            Fornecedor fornecedor;
            if(fornecedorRepository.existsById(cnpj))
                fornecedor = fornecedorRepository.findById(cnpj).get();
            else{
                fornecedor = new Fornecedor();
                fornecedor.setNome(estoque.getNome());
                fornecedor.setCnpj(estoque.getCnpj());
                fornecedorRepository.save(fornecedor);
            }

            for(EstoqueFornecedorDetalheDto estoqueDetalhe: estoque.getPrecos()){
                if(estoqueDetalhe.getQuantidade_minima() <= ordem.getQuantidade() &&
                        (melhorPedido.getItens().size() == 0 ||
                        estoqueDetalhe.getPreco() < melhorPedido.getItens().get(0).getPreco())){
                    melhorPedido.setFornecedor(fornecedor);

                    PedidoItem pedidoItem = new PedidoItem();
                    pedidoItem.setPreco(estoqueDetalhe.getPreco());
                    pedidoItem.setTotal(ordem.getQuantidade() * estoqueDetalhe.getPreco());
                    pedidoItem.setProduto(produto);
                    melhorPedido.setItens(List.of(pedidoItem));
                }
            }
        }

        if(melhorPedido.getItens().size() == 0)
            throw new NaoProdutoDisponivel("Estamos sem estoque do produto " + produto.getGtin());

        return melhorPedido;
    }
}
