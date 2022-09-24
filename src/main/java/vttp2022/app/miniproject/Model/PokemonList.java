package vttp2022.app.miniproject.Model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class PokemonList implements Serializable {
    private List<PokemonAttribute> displayList = new LinkedList<>();

    public List<PokemonAttribute> getDisplayList() {
        return displayList;
    }

    public void setDisplayList(List<PokemonAttribute> displayList) {
        this.displayList = displayList;
    }

}
