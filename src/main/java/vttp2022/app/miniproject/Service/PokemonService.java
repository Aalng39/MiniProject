package vttp2022.app.miniproject.Service;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2022.app.miniproject.Model.PokemonAttribute;
import vttp2022.app.miniproject.Model.PokemonList;

@Service
public class PokemonService { 
    private static final Logger logger = LoggerFactory.getLogger(ToDoService.class);
    
    @Value("${pokemonTypes}")
    private String pokemonTypeURL;

    @Value("${pokemonName}")
    private String pokemonNameURL;

    RestTemplate restTemplate = new RestTemplate();
    
    public void getTypes(){
        ResponseEntity<String> response = restTemplate.getForEntity(pokemonTypeURL, String.class);
        String payload = response.getBody();
        StringReader stringReader = new StringReader(payload);
        JsonReader jsonReader = Json.createReader(stringReader);
        JsonObject jsonObject = jsonReader.readObject();
        JsonArray jsonArray = jsonObject.getJsonArray("results");

        for (int i = 0; i < jsonArray.size(); i++){
            JsonObject jsonOb = jsonArray.get(i).asJsonObject();

            String type = jsonOb.getString("name");
            // NewsFields newsFields = new NewsFields();
            // newsFields.setId(jsonOb.getString("id"));
            // newsFields.setPublishedOn(jsonOb.getJsonNumber("published_on").toString());
            // newsFields.setTitle(jsonOb.getString("title"));
            // newsFields.setUrl(jsonOb.getString("url"));
            // newsFields.setImageurl(jsonOb.getString("imageurl"));
            // newsFields.setBody(jsonOb.getString("body"));
            // newsFields.setTags(jsonOb.getString("tags"));
            // newsFields.setCategories(jsonOb.getString("categories"));   
            // NewsList.fieldsList.add(newsFields);       
        } 
    } 
    public List<PokemonAttribute> getPokemonDisplay(){
        //Get Pokemon Name limit to 20 for now
        ResponseEntity<String> response = restTemplate.getForEntity((pokemonNameURL + "/?offset=0&limit=8"), String.class);
        String payload = response.getBody();
        StringReader stringR = new StringReader(payload);
        JsonReader jsonR = Json.createReader(stringR);
        JsonObject jsonObj = jsonR.readObject();
        JsonArray jsonArr = jsonObj.getJsonArray("results");

        List<PokemonAttribute> pokeAttList = new LinkedList<>();

        for (int i = 0; i < jsonArr.size(); i++){
            JsonObject jsonOb = jsonArr.get(i).asJsonObject();

            PokemonAttribute pokeAt = new PokemonAttribute();
            String pokeName = jsonOb.getString("name");

            // Change Name to First Letter Caps
            String capPokeName = pokeName.substring(0, 1).toUpperCase() + pokeName.substring(1);
            pokeAt.setName(capPokeName);
            
            //Use Pokemon Name to get new URL 
            ResponseEntity<String> respN = restTemplate.getForEntity((pokemonNameURL + "/" + pokeName), String.class);
            String payloadN = respN.getBody();
            StringReader stringRN = new StringReader(payloadN);
            JsonReader jsonRN = Json.createReader(stringRN);
            JsonObject jsonObjN = jsonRN.readObject();

            //Getting Pokemon Img
            JsonObject sprites = jsonObjN.getJsonObject("sprites");
            JsonObject other = sprites.getJsonObject("other");
            JsonObject offArt = other.getJsonObject("official-artwork");
            
            pokeAt.setImageUrl(offArt.getString("front_default"));
            // logger.info(pokeAt.getImageUrl());

            //Getting Pokemon Types
            JsonArray jsonArrayN = jsonObjN.getJsonArray("types");
            List<String> pokeTypes = new LinkedList<>();
            for (int y = 0; y < jsonArrayN.size(); y++){
                JsonObject types = jsonArrayN.get(y).asJsonObject();
                
                JsonObject type = types.getJsonObject("type");
                pokeTypes.add(type.getString("name"));                
            }
            pokeAt.setTypes(pokeTypes);
            pokeAttList.add(pokeAt);

        }
        PokemonList pokemonList = new PokemonList();
        pokemonList.setDisplayList(pokeAttList);

        return pokemonList.getDisplayList() ;  
    }

    public List<String> getPokemonDetails(String name){
        String pokeSearchName = name.toLowerCase();
        ResponseEntity<String> respD = restTemplate.getForEntity((pokemonNameURL + "/" + pokeSearchName), String.class);
        String payloadD = respD.getBody();
        StringReader stringRD = new StringReader(payloadD);
        JsonReader jsonRD = Json.createReader(stringRD);
        JsonObject jsonObjD = jsonRD.readObject();

        List<String> pokeDetails = new LinkedList<>();
        String height = (jsonObjD.getJsonNumber("height")).toString();
        logger.info(height);
        String weight = jsonObjD.getJsonNumber("weight").toString();
        logger.info(weight);
        

        //Getting Pokemon Img
        JsonObject sprites = jsonObjD.getJsonObject("sprites");
        JsonObject other = sprites.getJsonObject("other");
        JsonObject offArt = other.getJsonObject("official-artwork");
        logger.info(offArt.getString("front_default"));

        //Getting Pokemon Types
        JsonArray jsonArrayN = jsonObjD.getJsonArray("types");
        List<String> pokeTypes = new LinkedList<>();
        for (int y = 0; y < jsonArrayN.size(); y++){
            JsonObject types = jsonArrayN.get(y).asJsonObject();
            
            JsonObject type = types.getJsonObject("type");
            pokeTypes.add(type.getString("name"));                
        }

        pokeDetails.add(height);
        pokeDetails.add(weight);
        pokeDetails.add(offArt.getString("front_default"));
        
        return null;
    }
}
