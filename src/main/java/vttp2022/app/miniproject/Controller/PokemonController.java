package vttp2022.app.miniproject.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import vttp2022.app.miniproject.Model.PokemonAttribute;
import vttp2022.app.miniproject.Service.PokemonService;

@Controller
public class PokemonController {

    @Autowired
    PokemonService service;

    @GetMapping("/Pokemon")
    public String getPokemonDisplayPage(Model model){

        service.getTypes();

        List<PokemonAttribute> pokemon = service.getPokemonDisplay();
        model.addAttribute("pokemon", pokemon);
        return "pokemon";
        }
    @GetMapping("/Pokemon/{name}")
    public String getPokemonDetails(@PathVariable String name, Model model){
        service.getPokemonDetails(name);
        return "pokemondetails";
    }
    
}
