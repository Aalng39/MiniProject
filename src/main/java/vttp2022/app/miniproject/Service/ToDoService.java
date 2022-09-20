package vttp2022.app.miniproject.Service;

import vttp2022.app.miniproject.Model.ToDoItem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ToDoService implements ToDoRepo {
    // private static final Logger logger = LoggerFactory.getLogger(ToDoService.class);
    
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
    String currentDateTime = LocalDateTime.now().format(formatter);

    public List<ToDoItem> createListOfTask(String userId, String description){
        ToDoItem items = new ToDoItem();
        items.setDescription(description);
        items.setUserId(userId);
        items.setDateCreated(currentDateTime);
        for(int i = 0; i < 1 + (items.getToDoList().size()); i++){  
        items.setTaskCounter(i+1);}
    
        if(redisTemplate.hasKey(userId)){

            ToDoItem toDo = (ToDoItem) redisTemplate.opsForValue().get(userId);
            for(int i = 0; i < 1 + (toDo.getToDoList().size()); i++){  
                items.setTaskCounter(i+1);}
            toDo.getToDoList().add(items);

            return toDo.getToDoList();   

        }else{

            items.getToDoList().add(items);
            return items.getToDoList();       
        }        
    }



    @Override
    public void save(final ToDoItem toDoItem) {
        String username = toDoItem.getUserId().replace(" ", "");
        if(redisTemplate.hasKey(toDoItem.getUserId())){

            // ToDoItem items = (ToDoItem) redisTemplate.opsForValue().get(toDoItem.getUserId());
            // items.getToDoList().add(toDoItem);

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
    public ToDoItem loginWithId(final String userId) {
        ToDoItem items = (ToDoItem) redisTemplate.opsForValue().get(userId);
        return items;
    }

}
