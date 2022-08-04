package br.com.bluesoft.desafio.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class FornecedorDto {
    private String cnpj;
    private String nome;
}
