package vttp2022.app.miniproject.Controller;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp2022.app.miniproject.Model.CurrentUser;
import vttp2022.app.miniproject.Model.PokemonAttribute;
import vttp2022.app.miniproject.Service.PokeRedisService;
import vttp2022.app.miniproject.Service.PokemonService;

@Controller
public class PokemonController {
    private static final Logger logger = LoggerFactory.getLogger(PokemonController.class);

    @Autowired
    PokemonService service;

    @Autowired
    PokeRedisService redisService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/Login")
    public String login(Model model){
        model.addAttribute("pokelist", new PokemonAttribute());
        return "userpage";
    }

    @PostMapping("/Pokemon")
    public String loginPokemonDisplayPage(@ModelAttribute PokemonAttribute pokemonAttribute, Model model){

        CurrentUser.setCurrentUser(pokemonAttribute.getUserId());
        if(redisTemplate.hasKey(pokemonAttribute.getUserId())){
            logger.info("Welcome " + pokemonAttribute.getUserId());
        }else{
            List<String> newList = new LinkedList<>();
            pokemonAttribute.setSavedName(newList);
            redisService.save(pokemonAttribute);
        }

        List<String> typesList = service.getTypesList();
        List<PokemonAttribute> pokemon = service.getPokemonDisplay();
        model.addAttribute("pokemon", pokemon);
        model.addAttribute("typelist", typesList);
        model.addAttribute("searchpokemon",  pokemonAttribute);
        return "pokemon";
    }

    @GetMapping("/Pokemon")
    public String getPokemonDisplayPage(@ModelAttribute PokemonAttribute pokemonAttribute, Model model){

        pokemonAttribute.setUserId(CurrentUser.getCurrentUser());

        List<String> typesList = service.getTypesList();
        List<PokemonAttribute> pokemon = service.getPokemonDisplay();
        model.addAttribute("pokemon", pokemon);
        model.addAttribute("typelist", typesList);
        model.addAttribute("searchpokemon",  pokemonAttribute);
        return "pokemon";
    }
    @GetMapping("/Pokemon/MyTeam")
        public String getMyPokemonTeam(@ModelAttribute PokemonAttribute pokemonAttribute, Model model){

            pokemonAttribute.setUserId(CurrentUser.getCurrentUser());

            PokemonAttribute userPokeAtt = redisService.loginWithId(pokemonAttribute.getUserId());

            List<PokemonAttribute> pokemon = new LinkedList<>();
            for(String allName : userPokeAtt.getSavedName()){
            PokemonAttribute poke = service.getMyPokemon(allName);
            pokemon.add(poke);
            }
            List<String> typesList = service.getTypesList();
            model.addAttribute("pokemon", pokemon);
            model.addAttribute("typelist", typesList);
            model.addAttribute("searchpokemon",  pokemonAttribute);
            return "mypokemon";
        }

    @PostMapping("/Pokemon/MyTeam")
    public String addPokemonToMyTeam(@ModelAttribute PokemonAttribute pokemonAttribute, Model model){

        pokemonAttribute.setUserId(CurrentUser.getCurrentUser());
        String pokeName = pokemonAttribute.getName();
        logger.info(pokemonAttribute.getName());

        PokemonAttribute userPokeAtt = redisService.loginWithId(pokemonAttribute.getUserId());
        userPokeAtt.getSavedName().add(pokeName);
        
        for(String name : userPokeAtt.getSavedName()){
            logger.info(name);}
        redisService.save(userPokeAtt);

        List<PokemonAttribute> pokemon = new LinkedList<>();
        for(String allName : userPokeAtt.getSavedName()){
        PokemonAttribute poke = service.getMyPokemon(allName);
        pokemon.add(poke);
        }
        List<String> typesList = service.getTypesList();
        model.addAttribute("pokemon", pokemon);
        model.addAttribute("typelist", typesList);
        model.addAttribute("searchpokemon", pokemonAttribute);
        return "mypokemon";
    }

    @GetMapping("/Pokemon/{name}")
    public String getPokemonDetails(@PathVariable String name, @ModelAttribute PokemonAttribute pokemonAttribute, Model model){

        pokemonAttribute.setUserId(CurrentUser.getCurrentUser());

        List<String> typesList = service.getTypesList();

        List<PokemonAttribute> pokemonD = service.getPokemonDetails(name);
        model.addAttribute("pokemonD" , pokemonD);
        model.addAttribute("typelist", typesList);
        model.addAttribute("searchpokemon",  pokemonAttribute);
        return "pokemondetails";
    }

    @GetMapping("/Pokemon/search")
    public String searchPokemonDetails(@RequestParam String name, @ModelAttribute PokemonAttribute pokemonAttribute, Model model){

        pokemonAttribute.setUserId(CurrentUser.getCurrentUser());

        List<String> typesList = service.getTypesList();       
        List<PokemonAttribute> pokemonD = service.getPokemonDetails(name);
        model.addAttribute("pokemonD" , pokemonD);
        model.addAttribute("typelist", typesList);
        model.addAttribute("searchpokemon",  pokemonAttribute);

        return "pokemondetails";
    }

    @GetMapping("/Pokemon/Types/{type}")
    public String getPokemonDisplayPageFromType(@PathVariable String type, @ModelAttribute PokemonAttribute pokemonAttribute, Model model){
    
        pokemonAttribute.setUserId(CurrentUser.getCurrentUser());

        List<String> typesList = service.getTypesList();

        List<PokemonAttribute> pokemonByType = service.getPokemonDisplayFromType(type);
        model.addAttribute("pokemon", pokemonByType);
        model.addAttribute("typelist", typesList);
        model.addAttribute("searchpokemon",  pokemonAttribute);
        return "pokemon";
    }

    @PostMapping("/Pokemon/MyTeam/removed")
    public String removePokemonFromMyTeam(@ModelAttribute PokemonAttribute pokemonAttribute, Model model){

        pokemonAttribute.setUserId(CurrentUser.getCurrentUser());
        int index = pokemonAttribute.getIndex();
        logger.info(String.valueOf(pokemonAttribute.getIndex()));

        PokemonAttribute userPokeAtt = redisService.loginWithId(pokemonAttribute.getUserId());
        userPokeAtt.getSavedName().remove(index);
        redisService.save(userPokeAtt);

        List<PokemonAttribute> pokemon = new LinkedList<>();
        for(String allName : userPokeAtt.getSavedName()){
        PokemonAttribute poke = service.getMyPokemon(allName);
        pokemon.add(poke);
        }
        List<String> typesList = service.getTypesList();
        model.addAttribute("pokemon", pokemon);
        model.addAttribute("typelist", typesList);
        model.addAttribute("searchpokemon", pokemonAttribute);
        return "redirect:/Pokemon/MyTeam";
    }
}
