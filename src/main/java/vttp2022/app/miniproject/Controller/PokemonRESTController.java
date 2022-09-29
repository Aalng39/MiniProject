package vttp2022.app.miniproject.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp2022.app.miniproject.Model.UserCart;
import vttp2022.app.miniproject.Service.PokeRedisService;

@RestController
public class PokemonRESTController {

    @Autowired
    PokeRedisService service;

    @GetMapping(path="/MyPokemon/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> returnArticle(@PathVariable String username){
        Optional<UserCart> optPokemonC = service.findPokemonCartByUserId(username);
        ObjectMapper mapper = new ObjectMapper();
        if (optPokemonC.isPresent()){
            UserCart pokemonList = optPokemonC.get();
            String pokemonListInStringFormat = "";
            try {
                pokemonListInStringFormat = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(pokemonList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            ResponseEntity<String> respEntity = 
                        ResponseEntity.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(pokemonListInStringFormat);
            return respEntity;
        }

        JsonObject jsonErrorObject = Json.createObjectBuilder().add("error", "Cannot find User " + username).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(jsonErrorObject.toString());
    
    }
    
}