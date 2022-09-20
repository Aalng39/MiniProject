package vttp2022.app.miniproject.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestParam;

import vttp2022.app.miniproject.Model.CurrentUser;
import vttp2022.app.miniproject.Model.ToDoItem;
import vttp2022.app.miniproject.Service.ToDoService;

@Controller
public class ToDoController {
    private static final Logger logger = LoggerFactory.getLogger(ToDoController.class);
    

    @Autowired
    ToDoService service;

    @GetMapping("/")
        public String login(Model model) {
        model.addAttribute("todolist", new ToDoItem());
        return "userpage";
        }

    @GetMapping("/Add")
        public String addTaskToList(Model model) {
        model.addAttribute("todolist", new ToDoItem());
        return "addpage";
        }
    
    @PostMapping("/ToDoList")
    public String showToDoList(@ModelAttribute ToDoItem toDoItem, Model model) {
        logger.info(CurrentUser.getCurrentUser());
        toDoItem.setUserId(CurrentUser.getCurrentUser());

        List<ToDoItem> allItems = service.allUsersTasks(toDoItem.getUserId(), toDoItem.getDescription());
                toDoItem.setToDoList(allItems);
                toDoItem.getTaskCounter();
                toDoItem.getUserId();
                toDoItem.getDescription();
                toDoItem.getToDoList();
            

        service.save(toDoItem);

        model.addAttribute("todolist", toDoItem);
        model.addAttribute("itemlist", toDoItem);
        model.addAttribute("alluseritems", allItems);

        return "displaypage";
    }

    @GetMapping("/List")
    public String loginUsingUsername(@RequestParam String userId, Model model){

        CurrentUser.setCurrentUser(userId);

        ToDoItem userDetails = new ToDoItem();
        userDetails.setUserId(userId);
        model.addAttribute("todolist", userDetails);
        
        ToDoItem userItem = service.loginWithId(userId);
        List<ToDoItem> allItems = userItem.getToDoList();
        
        model.addAttribute("alluseritems", allItems);
        return "displaypage";
    }

    // @PostMapping("/List")
    // public String deleteTasks(@RequestParam String userId, 
    //                                 Model model){                                
        
    //     ToDoItem toDoItem = service.loginWithId(userId);
    //     List<ToDoItem> allItems = toDoItem.getToDoList();
    //     model.addAttribute("alluseritems", allItems);
    //     return "displaypage";
    // }
}