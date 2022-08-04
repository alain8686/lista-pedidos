package br.com.bluesoft.desafio.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Fornecedor {
    @Id
    private String cnpj;

    private String nome;

    public String getCnpj() {
        return cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}