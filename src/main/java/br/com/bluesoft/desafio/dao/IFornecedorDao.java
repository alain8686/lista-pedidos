package br.com.bluesoft.desafio.dao;

import br.com.bluesoft.desafio.dto.EstoqueFornecedorDto;
import br.com.bluesoft.desafio.model.Produto;


public interface IFornecedorDao {
    EstoqueFornecedorDto[] fornecedoreOrdem(Produto produto);
}