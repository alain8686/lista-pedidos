package br.com.bluesoft.desafio.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Data
public class ProdutoDto {
    private String gtin;
    private String nome;
}
