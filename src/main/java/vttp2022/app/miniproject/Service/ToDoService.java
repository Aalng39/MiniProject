package vttp2022.app.miniproject.Service;

import vttp2022.app.miniproject.Model.ToDoItem;

import java.util.List;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ToDoService implements ToDoRepo {
    // private static final Logger logger = LoggerFactory.getLogger(ToDoService.class);
    
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    // @Override
    // public void save(ToDoItem toDoItem) {
    //     redisTemplate.opsForValue().set(toDoItem.getId(), toDoItem);
    
    public List<ToDoItem> allUsersTasks(String description){
        ToDoItem items = new ToDoItem();
        items.setDescription(description);
        items.setUserId(items.getUserId());

        for(int i = 0; i < 1 + (items.getToDoList().size()); i++){  
        items.setTaskCounter(i+1);}
        
        items.getToDoList().add(items);

        return items.getToDoList();
    }


    @Override
    public void save(final ToDoItem toDoItem) {
        String username = toDoItem.getUserId().replace(" ", "");
        if(redisTemplate.hasKey(toDoItem.getUserId())){

           redisTemplate.opsForValue().setIfPresent(username, toDoItem);
            
        }else{
            redisTemplate.opsForValue().setIfAbsent(username, toDoItem);
        }
            
        // ToDoItem result = (ToDoItem) redisTemplate.opsForValue().get(username);

        // boolean saved = (result != null);
        // logger.info("Save mastermind > " + String.valueOf(saved));

        // if (result != null)
        //     return 1;

        // return 0;
    }

    @Override
    public ToDoItem loginWithId(String userId) {
        ToDoItem items = (ToDoItem) redisTemplate.opsForValue().get(userId);
        return items;
    }

}
