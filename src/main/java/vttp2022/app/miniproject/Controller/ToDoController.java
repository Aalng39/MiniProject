package vttp2022.app.miniproject.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;

import vttp2022.app.miniproject.Model.CurrentUser;
import vttp2022.app.miniproject.Model.ToDoItem;
import vttp2022.app.miniproject.Model.ToDoList;
import vttp2022.app.miniproject.Service.ToDoService;

@Controller
public class ToDoController {
    private static final Logger logger = LoggerFactory.getLogger(ToDoController.class);
    
    @Autowired
    ToDoService service;
    
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/Login")
    public String login(Model model) {
        model.addAttribute("todolist", new ToDoItem());
        return "userpage";
    }

    @PostMapping("/MyToDoList")
    public String loginUsingUsername(@ModelAttribute ToDoItem toDoItem, Model model){
    
        CurrentUser.setCurrentUser(toDoItem.getUserId());
        ToDoItem userDetails = new ToDoItem();
        userDetails.setUserId(CurrentUser.getCurrentUser());
        model.addAttribute("todolist", userDetails);
            
        if(redisTemplate.hasKey(userDetails.getUserId())){
    
        ToDoList toDoList = service.loginWithId(userDetails.getUserId());
        List<ToDoItem> allItems = toDoList.getToDoList();                 
        model.addAttribute("alluseritems", allItems);
        
        return "displaypage";
                  
        }else{
     
        return "newuserpage";           
        }                   
    }
  
    @PostMapping("/MyToDoList/AddedTask")
    public String showToDoList(@ModelAttribute ToDoItem toDoItem, Model model) {

        
        toDoItem.setUserId(CurrentUser.getCurrentUser());

        List<ToDoItem> allItems = service.createListOfTask(toDoItem.getUserId(), toDoItem.getDescription());
        ToDoList toDoList = new ToDoList();
        toDoList.setToDoList(allItems);               

        service.save(toDoList);

        model.addAttribute("todolist", toDoItem);
        model.addAttribute("itemlist", toDoList);
        model.addAttribute("alluseritems", allItems);

        return "displayPage";
    }

    @PostMapping("/MyToDoList/CompletedTask")
    public String deleteTasks(@ModelAttribute ToDoItem toDoItem, Model model){

        toDoItem.setUserId(CurrentUser.getCurrentUser());

        logger.info(Integer.toString(toDoItem.getIndex()));

        ToDoList userItem = service.loginWithId(toDoItem.getUserId());
        List<ToDoItem> allItems = userItem.getToDoList();

        ToDoItem completedTask = allItems.get(toDoItem.getIndex());
        List<ToDoItem> completedTasks = service.createCompletedList(toDoItem.getUserId(), completedTask);
        
        allItems.remove(toDoItem.getIndex());
        
        ToDoList toDoList = new ToDoList();
        toDoList.setToDoList(allItems);
        toDoList.setCompletedList(completedTasks);
        
        
        service.save(toDoList);

        model.addAttribute("todolist", toDoItem);
        model.addAttribute("itemlist", toDoList);
        model.addAttribute("alluseritems", allItems);

        return "displaypage";
    }

    @GetMapping("/CompletedTaskList")
    public String completedList(@ModelAttribute ToDoItem toDoItem, Model model){

        toDoItem.setUserId(CurrentUser.getCurrentUser());
        ToDoList userItem = service.loginWithId(toDoItem.getUserId());
        List<ToDoItem> allItems = userItem.getCompletedList();

        model.addAttribute("itemlist", userItem);
        model.addAttribute("alluseritems", allItems);

        return "completedpage";
    }
}