package vttp2022.app.miniproject.Model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class UserCart implements Serializable{
    private static String username;
    private List<String> myPokemonTeam = new LinkedList<>();
     
    public UserCart() {

    }
    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        UserCart.username = username;
    }
    public List<String> getMyPokemonTeam() {
        return myPokemonTeam;
    }
    public void setMyPokemonTeam(List<String> myPokemonTeam) {
        this.myPokemonTeam = myPokemonTeam;
    }
}
