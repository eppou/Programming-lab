package com.example.Prova1ConsumidorMarcosDias;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
@Controller
public class CarrinhoController {

    private static final  String SESSION_CARRINHO = "sessionCarrinho";

    @Autowired
    RestauranteRepository restauranteRepository;

    @Autowired
    ItemCardapioRepository itemRepository;

    @GetMapping(value = {"/index", "/"})
    public String mostraInicio(){
        return "/index";
    }

    @GetMapping(value = "/restaurantes")
    public String mostraRestaurantes(Model model){
        model.addAttribute("listaRestaurantes",restauranteRepository.findAll());
        return "mostra-restaurantes";
    }

    @GetMapping(value = "/mostrarcardapio/{id}")
    public String mostrarCardapio(@PathVariable ("id") int id, Model model){
        List cardapio = itemRepository.findByRestauranteId(id);

        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("O id do restaurante é inválido:" + id));

        model.addAttribute("cardapio",cardapio);
        model.addAttribute("restaurante",restaurante);


        return "cardapio";
    }

    @GetMapping(value = "/pedir/{id}")
    public String addPedido (@PathVariable("id") int id,HttpServletRequest request){

        ItemCardapio itemCardapio = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("O id do item é inválido:" + id));

        List<Carrinho> carrinho = (List<Carrinho>)
                request.getSession().getAttribute(SESSION_CARRINHO);

        if (CollectionUtils.isEmpty(carrinho)){
            carrinho = new ArrayList<>();
        }

        boolean surpass = false;

        for (Carrinho item: carrinho) {
            if (item.getItemCardapio().getId() == itemCardapio.getId()){
                item.somaQuantidade();
                surpass = true;
            }
        }

        if(surpass == false) {
            Carrinho compra = new Carrinho();
            compra.setQuantia(1);
            compra.setItemCardapio(itemCardapio);
            carrinho.add(compra);
        }

        Carrinho.setTotal(Carrinho.getTotal()+ itemCardapio.getPreco());

        request.getSession().setAttribute(SESSION_CARRINHO,carrinho);

        return "redirect:/pedido";
    }

    @GetMapping("/pedido")
    public String mostraPedido(Model model,HttpServletRequest request){

        List<Carrinho> carrinho = (List<Carrinho>) request.getSession().getAttribute(SESSION_CARRINHO);


        model.addAttribute("sessionCarrinho",
                !CollectionUtils.isEmpty(carrinho) ? carrinho : new ArrayList<>());

        model.addAttribute("total", Carrinho.getTotal());

        return "carrinho";
    }

    @GetMapping("/pedido/aumentar/{id}")
    public String aumentaPedido(@PathVariable("id") int id,HttpServletRequest request ){

        ItemCardapio itemCardapio = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("O id do item é inválido:" + id));

        List<Carrinho> carrinho = (List<Carrinho>)
                request.getSession().getAttribute(SESSION_CARRINHO);

        for (Carrinho item: carrinho) {

            if (item.getItemCardapio().getId() == itemCardapio.getId()){
                item.somaQuantidade();
                Carrinho.setTotal(Carrinho.getTotal() + item.getItemCardapio().getPreco());
            }
        }
        request.getSession().setAttribute(SESSION_CARRINHO,carrinho);

        return "redirect:/pedido";
    }

    @GetMapping("/pedido/diminuir/{id}")
    public String diminuiPedido(@PathVariable("id") int id,HttpServletRequest request ){

        ItemCardapio itemCardapio = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("O id do item é inválido:" + id));

        List<Carrinho> carrinho = (List<Carrinho>)
                request.getSession().getAttribute(SESSION_CARRINHO);

        for (Carrinho item: carrinho) {

            if (item.getItemCardapio().getId() == itemCardapio.getId()){
                item.subQuantidade();
                Carrinho.setTotal(Carrinho.getTotal() - item.getItemCardapio().getPreco());
            }
            if(item.getQuantia() <= 0){
                carrinho.remove(item);
                request.getSession().setAttribute(SESSION_CARRINHO, carrinho);
                break;
            }
        }


        request.getSession().setAttribute(SESSION_CARRINHO,carrinho);

        return "redirect:/pedido";
    }
}
