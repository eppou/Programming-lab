package com.example.Prova1ConsumidorMarcosDias;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Restaurante implements  Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;
    @OneToMany(mappedBy="restaurante")
    private List<ItemCardapio> cardapio ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List getCardapio() {
        return cardapio;
    }

    public void setCardapio(List<ItemCardapio> cardapio) {
        this.cardapio = cardapio;
    }
}
