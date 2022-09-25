package vttp2022.app.miniproject.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import vttp2022.app.miniproject.Model.CurrentUser;
import vttp2022.app.miniproject.Model.PokemonAttribute;

@Service
public class PokeRedisService implements PokeRedisRepo {
    private static final Logger logger = LoggerFactory.getLogger(ToDoService.class);
    
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public List<String> createListOfPokeName(String userId, String name){
        
        if(redisTemplate.hasKey(userId)){

            PokemonAttribute pokemonAtt =  (PokemonAttribute) redisTemplate.opsForValue().get(userId);
            pokemonAtt.getSavedName().add(name);

            return pokemonAtt.getSavedName();   

        }else{

            PokemonAttribute pokemonAtt = new PokemonAttribute();
            pokemonAtt.getSavedName().add(name);
            return pokemonAtt.getSavedName();      
        }        
    }

    // public List<ToDoItem> createCompletedList(String userId, ToDoItem selectedItem){  

    //     ToDoItem completedItem = new ToDoItem();
    //     completedItem.setDescription(selectedItem.getDescription());
    //     completedItem.setDateCreated(currentDateTime);
    //     logger.info(completedItem.getDateCreated());

    //     ToDoList userItem = (ToDoList) redisTemplate.opsForValue().get(userId);

    //     if(userItem.getCompletedList() != null){
    //         userItem.getCompletedList().add(completedItem);
            
    //         return userItem.getCompletedList();

    //     }else{
    //         ToDoList toDoList = new ToDoList();
    //         toDoList.getToDoList().add(completedItem);
    //         return toDoList.getToDoList();
    //         }       
    // }

    @Override
    public void save(PokemonAttribute savedPokemon) {
        String username = CurrentUser.getCurrentUser();
        if(redisTemplate.hasKey(CurrentUser.getCurrentUser())){

           redisTemplate.opsForValue().setIfPresent(username, savedPokemon);
            
        }else{
            redisTemplate.opsForValue().setIfAbsent(username, savedPokemon);
        }
    }

    @Override
    public PokemonAttribute loginWithId(final String userId) {
        PokemonAttribute pokemonAtt = (PokemonAttribute) redisTemplate.opsForValue().get(userId);
        return pokemonAtt;
    }

}

