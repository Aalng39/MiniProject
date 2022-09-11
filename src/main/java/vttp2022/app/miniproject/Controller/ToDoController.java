package vttp2022.app.miniproject.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import vttp2022.app.miniproject.Model.ToDoItem;
import vttp2022.app.miniproject.Model.ToDoList;
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
    public String showToDoList(@ModelAttribute ToDoItem toDoItem, ToDoList toDoList, Model model) {
        
        ToDoItem item = new ToDoItem(
            toDoItem.getTaskCounter(),
            toDoItem.getUserId(),
            toDoItem.getDescription(),
            toDoItem.getToDoTask()
            ); 

        service.save(item);

        List<ToDoItem> allItems = service.allUsersTasks(toDoItem.getDescription());
        ToDoList.setToDoList(allItems);

        model.addAttribute("itemlist", item);
        model.addAttribute("alluseritems", allItems);

       
        return "displaypage";
    }

}