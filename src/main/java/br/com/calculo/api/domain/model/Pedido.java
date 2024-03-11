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


@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Pedido {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ItemPedido> items = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Adicional> adicionais = new ArrayList<>();

    @Valid
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "id_desconto")
    private Desconto desconto;

    public Pedido() {
    }

    public Long getId() {
        return id;
    }

    public List<ItemPedido> getItems() {
        return items;
    }

    public List<Adicional> getAdicional() {
        return adicionais;
    }

    public Desconto getDesconto() {
        return desconto;
    }

    public void setItems(List<ItemPedido> items) {
        this.items = items;
    }

    public void setAdicionais(List<Adicional> adicionais) {
        this.adicionais = adicionais;
    }

    public void setDesconto(Desconto desconto) {
        this.desconto = desconto;
    }

    public BigDecimal calcularTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (ItemPedido item : items) {
            total = total.add(item.getValor());
        }
        for (Adicional adicional : adicionais) {
            total = total.add(adicional.getValor());
        }
        return total;
    }


    }


