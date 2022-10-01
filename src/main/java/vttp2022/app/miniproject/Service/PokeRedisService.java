package vttp2022.app.miniproject.Service;

import java.util.List;
import java.util.Optional;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import vttp2022.app.miniproject.Model.PokemonAttribute;
import vttp2022.app.miniproject.Model.UserCart;

@Service
public class PokeRedisService implements PokeRedisRepo {
    // private static final Logger logger = LoggerFactory.getLogger(PokeRedisService.class);
    
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public List<String> createListOfPokeName(String userId, String name){
        
        if(redisTemplate.hasKey(userId)){
            
            UserCart userCart = (UserCart) redisTemplate.opsForValue().get(userId);
            userCart.getMyPokemonTeam().add(name);
            PokemonAttribute pokemonAtt = new PokemonAttribute();
            pokemonAtt.setSavedName(userCart.getMyPokemonTeam());
            

            return pokemonAtt.getSavedName();   

        }else{

            PokemonAttribute pokemonAtt = new PokemonAttribute();
            pokemonAtt.getSavedName().add(name);
            return pokemonAtt.getSavedName();      
        }        
    }

    @Override
    public void save(final UserCart myPokemonTeam) {
        String username = UserCart.getUsername();
        if(redisTemplate.hasKey(UserCart.getUsername())){

           redisTemplate.opsForValue().setIfPresent(username, myPokemonTeam);
            
        }else{
            redisTemplate.opsForValue().setIfAbsent(username, myPokemonTeam);
        }
    }

    @Override
    public UserCart loginWithId(final String username) {
        UserCart userCart = (UserCart) redisTemplate.opsForValue().get(username);
        return userCart;
    }

    @Override
    public Optional<UserCart> findPokemonCartByUserId(String username){
        UserCart userCart = (UserCart) redisTemplate.opsForValue().get(username);
        if (null!=userCart){
            return Optional.of(userCart);
        }
        return Optional.empty();
    }

}

