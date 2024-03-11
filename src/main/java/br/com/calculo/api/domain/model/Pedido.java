package br.com.calculo.api.domain.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Pedido {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Valid
    @NotNull
    @OneToMany(cascade = CascadeType.ALL)
    private List<ItemPedido> items = new ArrayList<>();

    @Valid
    @NotNull
    @OneToMany(cascade = CascadeType.ALL)
    private List<ItemPedido> additional = new ArrayList<>();

    @NotNull
    private BigDecimal descount;

    public Pedido() {
    }

    public Pedido(List<ItemPedido> items, List<ItemPedido> additional, BigDecimal descount) {
        this.items = items;
        this.additional = additional;
        this.descount = descount;
    }

    public List<ItemPedido> getItems() {
        return items;
    }

    public void setItems(List<ItemPedido> items) {
        this.items = items;
    }

    public List<ItemPedido> getAdditional() {
        return additional;
    }

    public void setAdditional(List<ItemPedido> additional) {
        this.additional = additional;
    }

    public void adicionarItem(ItemPedido item) {
        items.add(item);
    }

    public void adicionarAdicional(ItemPedido adicional) {
        additional.add(adicional);
    }

    public BigDecimal calcularTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (ItemPedido item : items) {
            total = total.add(item.getValor());
        }
        for (ItemPedido adicional : additional) {
            total = total.add(adicional.getValor());
        }
        return total;
    }
}