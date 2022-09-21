package vttp2022.app.miniproject.Service;

import vttp2022.app.miniproject.Model.CurrentUser;
import vttp2022.app.miniproject.Model.ToDoItem;
import vttp2022.app.miniproject.Model.ToDoList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ToDoService implements ToDoRepo {
    private static final Logger logger = LoggerFactory.getLogger(ToDoService.class);
    
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
    String currentDateTime = LocalDateTime.now().format(formatter);

    public List<ToDoItem> createListOfTask(String userId, String description){
        ToDoItem items = new ToDoItem();
        items.setUserId(userId);
        items.setDescription(description);
        items.setDateCreated(currentDateTime);
    
        if(redisTemplate.hasKey(userId)){

            ToDoList toDoList = (ToDoList) redisTemplate.opsForValue().get(userId);
            toDoList.getToDoList().add(items);

            return toDoList.getToDoList();   

        }else{

            ToDoList toDoList = new ToDoList();
            toDoList.getToDoList().add(items);
            return toDoList.getToDoList();       
        }        
    }

    @Override
    public void save(final ToDoList toDoList) {
        String username = CurrentUser.getCurrentUser();
        if(redisTemplate.hasKey(CurrentUser.getCurrentUser())){

           redisTemplate.opsForValue().setIfPresent(username, toDoList);
            
        }else{
            redisTemplate.opsForValue().setIfAbsent(username, toDoList);
        }
    }

    @Override
    public ToDoList loginWithId(final String userId) {
        ToDoList toDoList = (ToDoList) redisTemplate.opsForValue().get(userId);
        return toDoList;
    }

}
