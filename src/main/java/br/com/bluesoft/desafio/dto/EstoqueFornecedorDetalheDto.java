package br.com.bluesoft.desafio.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class EstoqueFornecedorDetalheDto {
    Double preco;
    Integer quantidade_minima;
}
