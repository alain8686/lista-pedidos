package br.com.bluesoft.desafio.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class PedidoDto {
    Integer id;
    FornecedorDto fornecedor;
    List<PedidoItemDto> itens;
}
