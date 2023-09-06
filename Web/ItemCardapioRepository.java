package com.example.Prova1ConsumidorMarcosDias;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemCardapioRepository extends JpaRepository<ItemCardapio,Integer> {
    List<ItemCardapio> findByRestauranteId(int idRestaurante);
}
