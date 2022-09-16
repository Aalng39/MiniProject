package vttp2022.app.miniproject.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp2022.app.miniproject.Model.ToDoItem;
import vttp2022.app.miniproject.Service.ToDoService;

@Controller
public class ToDoController {

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

        List<ToDoItem> allItems = service.allUsersTasks(toDoItem.getUserId(), toDoItem.getDescription());
                toDoItem.setToDoList(allItems);
                toDoItem.getTaskCounter();
                toDoItem.getUserId();
                toDoItem.getDescription();
                toDoItem.getToDoList();
            

        service.save(toDoItem);

        model.addAttribute("itemlist", toDoItem);
        model.addAttribute("alluseritems", allItems);

        return "displaypage";
    }

    @PostMapping("/List")
    public String returnResultPage(@RequestParam String userId, 
                                    Model model){
        ToDoItem toDoItem = service.loginWithId(userId);
        List<ToDoItem> allItems = toDoItem.getToDoList();
        model.addAttribute("alluseritems", allItems);
        return "displaypage";
    }
}