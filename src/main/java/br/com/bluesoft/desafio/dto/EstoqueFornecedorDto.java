package br.com.bluesoft.desafio.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class EstoqueFornecedorDto {
    String nome;
    String cnpj;
    List<EstoqueFornecedorDetalheDto> precos;
}
