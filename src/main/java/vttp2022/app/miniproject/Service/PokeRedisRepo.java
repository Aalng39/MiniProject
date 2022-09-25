package vttp2022.app.miniproject.Service;

import vttp2022.app.miniproject.Model.PokemonAttribute;

public interface PokeRedisRepo {
      
    public void save(final PokemonAttribute savedPokemon);
        
    public PokemonAttribute loginWithId(final String userId);
      
}
