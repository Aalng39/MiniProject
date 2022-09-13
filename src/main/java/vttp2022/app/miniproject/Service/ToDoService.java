package vttp2022.app.miniproject.Service;

import vttp2022.app.miniproject.Model.ToDoItem;
import vttp2022.app.miniproject.Model.ToDoList;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;



 
@Service
public class ToDoService implements ToDoRepo {
    private static final Logger logger = LoggerFactory.getLogger(ToDoService.class);
    
    @Autowired
    RedisTemplate<String, ToDoItem> redisTemplate;

    // @Override
    // public void save(ToDoItem toDoItem) {
    //     redisTemplate.opsForValue().set(toDoItem.getId(), toDoItem);
    
    public List<ToDoItem> allUsersTasks(String description){
        ToDoItem items = new ToDoItem();
        items.setDescription(description);
        items.setUserId(items.getUserId());

        for(int i = 0; i < 1 + (ToDoList.toDoList.size()); i++){  
        items.setTaskCounter(i+1);}
        ToDoList.toDoList.add(items);

        return ToDoList.toDoList;
    }

    
    @Override
    public int save(final ToDoItem toDoItem) {
        String username = toDoItem.getUserId().replace(" ", "");
        if(redisTemplate.hasKey(toDoItem.getUserId())){

           redisTemplate.opsForValue().setIfPresent(username, toDoItem);
            
        }else{
            redisTemplate.opsForValue().setIfAbsent(username, toDoItem);
        }
            
        ToDoItem result = (ToDoItem) redisTemplate.opsForValue().get(username);

        boolean saved = (result != null);
        logger.info("Save mastermind > " + String.valueOf(saved));

        if (result != null)
            return 1;

        return 0;
    }

    @Override
    public ToDoItem findById(String historyId) {
        // TODO Auto-generated method stub
        return null;
    }

}
