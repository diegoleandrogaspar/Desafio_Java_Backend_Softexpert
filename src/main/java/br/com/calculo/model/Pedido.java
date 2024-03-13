package br.com.calculo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ItemPedido> itens = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Adicional> adicionais = new ArrayList<>();

    @ElementCollection
    @OneToMany(cascade = CascadeType.ALL)
    private List<Desconto> descontos = new ArrayList<>();

    public Pedido() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItems(List<ItemPedido> items) {
        this.itens = items;
    }

    public List<Adicional> getAdicionais() {
        return adicionais;
    }

    public void setAdicionais(List<Adicional> adicionais) {
        this.adicionais = adicionais;
    }

    public List<Desconto> getDescontos() {
        return descontos;
    }

    public void setDescontos(List<Desconto> descontos) {
        this.descontos = descontos;
    }

    public BigDecimal calcularTotal() {
        BigDecimal totalItens = itens.stream()
                .map(ItemPedido::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalAdicionais = adicionais.stream()
                .map(adicional -> adicional.aplicar(totalItens))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal subtotal = totalItens.add(totalAdicionais);

        // Aplica os descontos ao subtotal
        BigDecimal subtotalComDescontos = descontos.stream()
                .map(desconto -> desconto.aplicar(subtotal)) // Aplica o desconto em cada desconto
                .reduce(subtotal, BigDecimal::subtract); // Subtrai os descontos do subtotal

        return subtotalComDescontos;
    }

    public void adicionarDesconto(Desconto desconto) {
        descontos.add(desconto);
    }

    public void removerDesconto(Desconto desconto) {
        descontos.remove(desconto);
    }

    public void adicionarItem(String nome, BigDecimal valor) {
        ItemPedido itemPedido = new ItemPedido(nome, valor);
        itens.add(itemPedido);
    }

    public void adicionarAdicional(Adicional adicional) {
        adicionais.add(adicional);
    }
}


