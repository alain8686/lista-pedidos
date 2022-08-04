package br.com.bluesoft.desafio.model;

import javax.persistence.*;

@Entity
public class PedidoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Produto produto;

    @Column(nullable = true)
    private Double total;

    @Column(nullable = false)
    private Double preco;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Double getPreco() {
        return preco;
    }

    public Double getTotal() {
        return total;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
