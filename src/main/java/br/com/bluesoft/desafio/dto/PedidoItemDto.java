package br.com.bluesoft.desafio.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class PedidoItemDto {
    private Double total;
    private Double preco;
    private ProdutoDto produto;
}
