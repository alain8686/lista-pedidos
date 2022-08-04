package br.com.bluesoft.desafio.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class OrdemDto {
    private String gtin;
    private Integer quantidade;
}
