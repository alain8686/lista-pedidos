package br.com.bluesoft.desafio.dao;

import br.com.bluesoft.desafio.dto.EstoqueFornecedorDto;
import br.com.bluesoft.desafio.model.Produto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;


@Repository
public class FornecedorDao implements IFornecedorDao{
    private RestTemplate restTemplate;

    @Value("${fornecedores.api}")
    private String forncedorApi;

    public FornecedorDao() {
        restTemplate = new RestTemplate();
    }

    public EstoqueFornecedorDto[] fornecedoreOrdem(Produto produto){
        String url = String.format(forncedorApi, produto.getGtin());

        EstoqueFornecedorDto[] response = restTemplate.getForObject(url, EstoqueFornecedorDto[].class);

        return response;
    }
}