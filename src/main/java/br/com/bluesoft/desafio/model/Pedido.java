package br.com.bluesoft.desafio.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Fornecedor fornecedor;

    @OneToMany(cascade = CascadeType.ALL)
    private List<PedidoItem> itens;

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public Integer getId() {
        return id;
    }

    public List<PedidoItem> getItens() {
        return itens;
    }

    public void setItens(List<PedidoItem> itens) {
        this.itens = itens;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
