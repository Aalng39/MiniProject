package vttp2022.app.miniproject.Service;

import java.util.Optional;
import vttp2022.app.miniproject.Model.UserCart;

public interface PokeRedisRepo {
      
    public void save(final UserCart myPokemonTeam);
        
    public UserCart loginWithId(final String username);

    public Optional<UserCart> findPokemonCartByUserId(String username);
      
}
