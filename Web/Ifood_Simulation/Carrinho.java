package com.example.Prova1ConsumidorMarcosDias;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.util.Objects;

public class Carrinho implements Serializable {
    @Value("0")
    private int quantia;

    @Value("0")
    private static double total;

    @NotBlank
    private ItemCardapio itemCardapio;

    public int getQuantia() {
        return quantia;
    }

    public void setQuantia(int quantia) {
        this.quantia = quantia;
    }

    public static double getTotal() {
        return total;
    }

    public static void setTotal(double total) {
        Carrinho.total = total;
    }

    public ItemCardapio getItemCardapio() {
        return itemCardapio;
    }

    public void setItemCardapio(ItemCardapio itemCardapio) {
        this.itemCardapio = itemCardapio;
    }

    public void somaQuantidade(){
        this.quantia = this.quantia + 1;
    }

    public void subQuantidade(){
        this.quantia = this.quantia -1;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof ItemCardapio)) return false;
        ItemCardapio that = (ItemCardapio) o;
        return this.itemCardapio.getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.itemCardapio.getId());
    }



}

