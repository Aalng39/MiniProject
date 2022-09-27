package vttp2022.app.miniproject.Model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class PokemonAttribute implements Serializable {
    private int index; 
    private String pokemonId;    
    private String userId;   
    private String name;
    private String imageUrl;
    private String height;
    private String weight; 
    private String description;
    private List<String> types = new LinkedList<>();
    private List<String> abilities = new LinkedList<>();
    private PokemonStats baseStats;
    private List<String> savedName = new LinkedList<>();
   

    public PokemonStats getBaseStats() {
        return baseStats;
    }

    public void setBaseStats(PokemonStats baseStats) {
        this.baseStats = baseStats;
    }

    public String getPokemonId() {
        return pokemonId;
    }
    public void setPokemonId(String pokemonId) {
        this.pokemonId = pokemonId;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
    public List<String> getSavedName() {
        return savedName;
    }
    public void setSavedName(List<String> savedName) {
        this.savedName = savedName;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public List<String> getAbilities() {
        return abilities;
    }
    public void setAbilities(List<String> abilities) {
        this.abilities = abilities;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public List<String> getTypes() {
        return types;
    }
    public void setTypes(List<String> types) {
        this.types = types;
    }
    public String getHeight() {
        return height;
    }
    public void setHeight(String height) {
        this.height = height;
    }
    public String getWeight() {
        return weight;
    }
    public void setWeight(String weight) {
        this.weight = weight;
    }
}
