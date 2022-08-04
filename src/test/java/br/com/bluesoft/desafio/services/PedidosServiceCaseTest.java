package br.com.bluesoft.desafio.services;

import br.com.bluesoft.desafio.dto.EstoqueFornecedorDetalheDto;
import br.com.bluesoft.desafio.dto.EstoqueFornecedorDto;
import br.com.bluesoft.desafio.dto.OrdemDto;
import br.com.bluesoft.desafio.exception.NaoProdutoDisponivel;
import br.com.bluesoft.desafio.model.Pedido;
import br.com.bluesoft.desafio.model.PedidoItem;
import br.com.bluesoft.desafio.model.Produto;
import br.com.bluesoft.desafio.repository.PedidoRepository;
import br.com.bluesoft.desafio.repository.ProdutoRepository;
import br.com.bluesoft.desafio.dao.FornecedorDao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Stream;

import static org.junit.Assert.fail;


@RunWith(SpringJUnit4ClassRunner.class)
public class PedidosServiceCaseTest {
    @Mock
    private FornecedorDao fornecedorDao;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testeMelhorEscolha() {
        Iterable<Produto> produtos = produtoRepository.findAll();

        for(Produto produto: produtos){
            EstoqueFornecedorDto[] estoque = fornecedorDao.fornecedoreOrdem(produto);

            if(estoque.length > 0){
                Entry<String, EstoqueFornecedorDetalheDto> minPreco = melhorPreco(estoque, -1, -1.0);
                Entry<String, EstoqueFornecedorDetalheDto> melhorPreco = melhorPreco(estoque,
                        minPreco.getValue().getQuantidade_minima(), minPreco.getValue().getPreco());

                OrdemDto ordem = new OrdemDto();
                ordem.setQuantidade(minPreco.getValue().getQuantidade_minima() + 1);
                ordem.setGtin(produto.getGtin());

                try {
                    List<Pedido> pedidos = pedidoService.criaPedidos(List.of(ordem));
                    asserFornecedor(pedidos, melhorPreco.getKey(), produto.getGtin());
                }
                catch (NaoProdutoDisponivel ex){
                    fail("Produto existente não foi encontrado");
                }
            }
        }
    }

    @Test
    public void testeSemQuantidadeProduto() {
        Iterable<Produto> produtos = produtoRepository.findAll();

        for(Produto produto: produtos){
            EstoqueFornecedorDto[] estoque = fornecedorDao.fornecedoreOrdem(produto);

            if(estoque.length > 0){
                Entry<String, EstoqueFornecedorDetalheDto> minPreco = melhorPreco(estoque, -1, -1.0);

                OrdemDto ordem = new OrdemDto();
                ordem.setGtin(produto.getGtin());

                try {
                    List<Pedido> pedidos = pedidoService.criaPedidos(List.of(ordem));
                    asserFornecedor(pedidos, minPreco.getKey(), produto.getGtin());
                }
                catch (NaoProdutoDisponivel ex){
                    fail("Produto existente não foi encontrado");
                }
            }
        }
    }

    public void testeCancelamentoPedido(){
        Iterable<Produto> produtos = produtoRepository.findAll();

        for(Produto produto: produtos){
            EstoqueFornecedorDto[] estoque = fornecedorDao.fornecedoreOrdem(produto);
            if(estoque.length == 0){
                OrdemDto ordem = new OrdemDto();
                ordem.setGtin(produto.getGtin());

                try {
                    pedidoService.criaPedidos(List.of(ordem));
                    fail("Produto " + produto.getGtin() + " não se encontra em estoque e não teve cmensagem de erro");
                }
                catch(NaoProdutoDisponivel ex){
                }
            }
        }
    }

    public void testePersistenciaDados(){
        Iterable<Produto> produtos = produtoRepository.findAll();

        for(Produto produto: produtos){
            OrdemDto ordem = new OrdemDto();
            ordem.setGtin(produto.getGtin());

            try {
                List<Pedido> pedidos = pedidoService.criaPedidos(List.of(ordem));
                for(Pedido pedido: pedidos){
                    if(!pedidoRepository.existsById(pedido.getId()))
                        fail("Pedido foi devolvido para a aplicação mais não persistido no banco");
                }
            }
            catch(NaoProdutoDisponivel ex){
            }
        }
    }

    private static void asserFornecedor(List<Pedido> pedidos, String cnpj, String gtin){
        for(Pedido pedido: pedidos){
            if(pedido.getFornecedor().getCnpj() == cnpj){
                Boolean found = false;
                for(PedidoItem item: pedido.getItens()){
                    if(item.getProduto().getGtin() == gtin) {
                        found = true;
                        break;
                    }
                }
                if(!found){
                    fail("Ordem não foi alocado com o melhor fornecedor");
                    break;
                }
            }
        }
    }

    private static Entry<String, EstoqueFornecedorDetalheDto> melhorPreco(EstoqueFornecedorDto[] estoque,
                                                                                 Integer minQuantidade,
                                                                                 Double minPreco){
        EstoqueFornecedorDetalheDto melhorPreco = null;
        String cnpj = null;

        for(EstoqueFornecedorDto estoqueFornecedor: estoque){
            Stream<EstoqueFornecedorDetalheDto> stream = estoqueFornecedor.getPrecos().stream()
                    .filter(preco -> preco.getQuantidade_minima() > minQuantidade && preco.getPreco() > minPreco);
            EstoqueFornecedorDetalheDto maxEstoquePreco = stream
                    .min((preco1, preco2) -> Double.compare(preco1.getPreco(), preco2.getPreco())).get();
            if(melhorPreco == null || maxEstoquePreco.getPreco() < melhorPreco.getPreco()){
                melhorPreco = maxEstoquePreco;
                cnpj = estoqueFornecedor.getCnpj();
            }
        }
        if(melhorPreco != null)
            return new AbstractMap.SimpleEntry(cnpj, melhorPreco);
        else
            return null;
    }
}
