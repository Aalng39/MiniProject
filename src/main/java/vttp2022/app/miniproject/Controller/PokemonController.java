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

import vttp2022.app.miniproject.Model.PokemonAttribute;
import vttp2022.app.miniproject.Model.UserCart;
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

        UserCart.setUsername(pokemonAttribute.getUserId());
        if(redisTemplate.hasKey(UserCart.getUsername())){
            logger.info("Welcome " + UserCart.getUsername());
        }else{
            UserCart userCart = new UserCart();
            List<String> newList = new LinkedList<>();
            userCart.setMyPokemonTeam(newList);
            redisService.save(userCart);
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

        pokemonAttribute.setUserId(UserCart.getUsername());

        List<String> typesList = service.getTypesList();
        List<PokemonAttribute> pokemon = service.getPokemonDisplay();
        model.addAttribute("pokemon", pokemon);
        model.addAttribute("typelist", typesList);
        model.addAttribute("searchpokemon",  pokemonAttribute);
        return "pokemon";
    }
    @GetMapping("/Pokemon/MyTeam")
        public String getMyPokemonTeam(@ModelAttribute PokemonAttribute pokemonAttribute, Model model){

            pokemonAttribute.setUserId(UserCart.getUsername());

            UserCart userCart = redisService.loginWithId(UserCart.getUsername());

            List<PokemonAttribute> pokemon = new LinkedList<>();
            for(String allName : userCart.getMyPokemonTeam()){
            PokemonAttribute poke = service.getMyPokemon(allName);
            pokemon.add(poke);
            }
            List<String> typesList = service.getTypesList();
            model.addAttribute("pokemon", pokemon);
            model.addAttribute("typelist", typesList);
            model.addAttribute("searchpokemon",  pokemonAttribute);
            return "pokemonmyteam";
        }

    @PostMapping("/Pokemon/MyTeam")
    public String addPokemonToMyTeam(@ModelAttribute PokemonAttribute pokemonAttribute, Model model){

        pokemonAttribute.setUserId(UserCart.getUsername());
        String pokeName = pokemonAttribute.getName();
        logger.info(pokemonAttribute.getName());

        UserCart userCart = redisService.loginWithId(pokemonAttribute.getUserId());
        userCart.getMyPokemonTeam().add(pokeName);
        
        for(String name : userCart.getMyPokemonTeam()){
            logger.info(name);}
        redisService.save(userCart);

        List<PokemonAttribute> pokemon = new LinkedList<>();
        for(String allName : userCart.getMyPokemonTeam()){
        PokemonAttribute poke = service.getMyPokemon(allName);
        pokemon.add(poke);
        }
        List<String> typesList = service.getTypesList();
        model.addAttribute("pokemon", pokemon);
        model.addAttribute("typelist", typesList);
        model.addAttribute("searchpokemon", pokemonAttribute);
        return "pokemonmyteam";
    }

    @GetMapping("/Pokemon/{name}")
    public String getPokemonDetails(@PathVariable String name, @ModelAttribute PokemonAttribute pokemonAttribute, Model model){

        pokemonAttribute.setUserId(UserCart.getUsername());

        List<String> typesList = service.getTypesList();

        List<PokemonAttribute> pokemonD = service.getPokemonDetails(name);
        model.addAttribute("pokemonD" , pokemonD);
        model.addAttribute("typelist", typesList);
        model.addAttribute("searchpokemon",  pokemonAttribute);
        return "pokemondetails";
    }

    @GetMapping("/Pokemon/search")
    public String searchPokemonDetails(@RequestParam String name, @ModelAttribute PokemonAttribute pokemonAttribute, Model model){

        pokemonAttribute.setUserId(UserCart.getUsername());

        List<String> typesList = service.getTypesList();       
        List<PokemonAttribute> pokemonD = service.getPokemonDetails(name);
        model.addAttribute("pokemonD" , pokemonD);
        model.addAttribute("typelist", typesList);
        model.addAttribute("searchpokemon",  pokemonAttribute);

        return "pokemondetails";
    }

    @GetMapping("/Pokemon/Types/{type}")
    public String getPokemonDisplayPageFromType(@PathVariable String type, @ModelAttribute PokemonAttribute pokemonAttribute, Model model){
    
        pokemonAttribute.setUserId(UserCart.getUsername());

        List<String> typesList = service.getTypesList();

        List<PokemonAttribute> pokemonByType = service.getPokemonDisplayFromType(type);
        model.addAttribute("pokemon", pokemonByType);
        model.addAttribute("typelist", typesList);
        model.addAttribute("searchpokemon",  pokemonAttribute);
        model.addAttribute("typex", type);
        return "pokemontype";
    }

    @PostMapping("/Pokemon/MyTeam/removed")
    public String removePokemonFromMyTeam(@ModelAttribute PokemonAttribute pokemonAttribute, Model model){

        pokemonAttribute.setUserId(UserCart.getUsername());
        
        int index = pokemonAttribute.getIndex();
        logger.info(String.valueOf(pokemonAttribute.getIndex()));

        UserCart userCart = redisService.loginWithId(pokemonAttribute.getUserId());
        userCart.getMyPokemonTeam().remove(index);
        redisService.save(userCart);

        List<PokemonAttribute> pokemon = new LinkedList<>();
        for(String allName : userCart.getMyPokemonTeam()){
        PokemonAttribute poke = service.getMyPokemon(allName);
        pokemon.add(poke);
        }
        List<String> typesList = service.getTypesList();
        model.addAttribute("pokemon", pokemon);
        model.addAttribute("typelist", typesList);
        model.addAttribute("searchpokemon", pokemonAttribute);
        return "redirect:/Pokemon/MyTeam";
    }


    @GetMapping("/Pokemon/Page/{pageNo}")
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
        return "pokemonpage2onward";

        }else{
        return "redirect:/Pokemon";
    }
    }

    @GetMapping("/Pokemon/Types/{typex}/Page/{pageNo}")
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
        return "pokemontypepagex";

        }else{
        return "redirect:/Pokemon/Types/{typex}";
    }
    }
}