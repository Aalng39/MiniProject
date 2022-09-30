package vttp2022.app.miniproject.Controller;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import vttp2022.app.miniproject.Model.PokemonAttribute;
import vttp2022.app.miniproject.Service.PokeRedisService;
import vttp2022.app.miniproject.Service.PokemonService;

@Controller
public class NoUserController {

    @Autowired
    PokemonService service;

    @Autowired
    PokeRedisService redisService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/")
    public String getPokemonDisplayPage(@ModelAttribute PokemonAttribute pokemonAttribute, Model model){

        List<String> typesList = service.getTypesList();
        List<PokemonAttribute> pokemon = service.getPokemonDisplay();
        model.addAttribute("pokemon", pokemon);
        model.addAttribute("typelist", typesList);
        model.addAttribute("searchpokemon",  pokemonAttribute);
        return "nouser";
    }
    @GetMapping("/{name}")
    public String getPokemonDetails(@PathVariable String name, @ModelAttribute PokemonAttribute pokemonAttribute, Model model){

        List<String> typesList = service.getTypesList();

        List<PokemonAttribute> pokemonD = service.getPokemonDetails(name);
        model.addAttribute("pokemonD" , pokemonD);
        model.addAttribute("typelist", typesList);
        model.addAttribute("searchpokemon",  pokemonAttribute);
        return "nouserdetails";
    }

    @GetMapping("/search")
    public String searchPokemonDetails(@RequestParam String name, @ModelAttribute PokemonAttribute pokemonAttribute, Model model){

        List<String> typesList = service.getTypesList();       
        List<PokemonAttribute> pokemonD = service.getPokemonDetails(name);
        model.addAttribute("pokemonD" , pokemonD);
        model.addAttribute("typelist", typesList);
        model.addAttribute("searchpokemon",  pokemonAttribute);

        return "nouserdetails";
    }

    @GetMapping("/Types/{type}")
    public String getPokemonDisplayPageFromType(@PathVariable String type, @ModelAttribute PokemonAttribute pokemonAttribute, Model model){
    
        List<String> typesList = service.getTypesList();

        List<PokemonAttribute> pokemonByType = service.getPokemonDisplayFromType(type);
        model.addAttribute("pokemon", pokemonByType);
        model.addAttribute("typelist", typesList);
        model.addAttribute("searchpokemon",  pokemonAttribute);
        model.addAttribute("typex", type);
        return "nousertype";
    }

    @GetMapping("/Page/{pageNo}")
    public String getPokemonByPage(@PathVariable String pageNo, @ModelAttribute PokemonAttribute pokemonAttribute, Model model){
    if(Integer.valueOf(pageNo) != 1){
        List<String> typesList = service.getTypesList();
        List<PokemonAttribute> pokemon = service.getPokemonDisplayByPage(pageNo);
        model.addAttribute("pokemon", pokemon);
        model.addAttribute("typelist", typesList);
        model.addAttribute("searchpokemon",  pokemonAttribute);
    
        String previous = String.valueOf((Integer.valueOf(pageNo)) - 1);
        String next = String.valueOf((Integer.valueOf(pageNo)) + 1);
        model.addAttribute("previouspage", previous);
        model.addAttribute("nextpage", next);
        return "nouserpage2onward";
    
            }else{
            return "redirect:/";
        }
    }

    @GetMapping("/Types/{typex}/Page/{pageNo}")
    public String getPokemonTypeByPage(@PathVariable String typex, @PathVariable String pageNo, @ModelAttribute PokemonAttribute pokemonAttribute, Model model){

        if(Integer.valueOf(pageNo) != 1){
        List<String> typesList = service.getTypesList();
        List<PokemonAttribute> pokemonByType = service.getPokemonPageFromType(typex, pageNo);
        model.addAttribute("pokemon", pokemonByType);
        model.addAttribute("typelist", typesList);
        model.addAttribute("searchpokemon",  pokemonAttribute);

        String previous = String.valueOf((Integer.valueOf(pageNo)) - 1);
        String next = String.valueOf((Integer.valueOf(pageNo)) + 1);
        model.addAttribute("previouspage", previous);
        model.addAttribute("nextpage", next);
        model.addAttribute("typex", typex);
        return "nousertypepagex";

        }else{
        return "redirect:/Types/{typex}";
        }
    }

}