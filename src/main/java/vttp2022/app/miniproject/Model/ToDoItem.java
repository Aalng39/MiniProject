package vttp2022.app.miniproject.Model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class ToDoItem implements Serializable{
    private String userId;
    private String description;
    private String dateCreated;
    private List<ToDoItem> toDoList = new LinkedList<>();
    private ToDoItem itemToDelete;
    //Constructors

    public ToDoItem getItemToDelete() {
        return itemToDelete;
    }

    public void setItemToDelete(ToDoItem itemToDelete) {
        this.itemToDelete = itemToDelete;
    }

    public ToDoItem() {
    }

    public ToDoItem(String userId, String description, List<ToDoItem> toDoList){
        this.userId = userId;
        this.description = description;
        this.toDoList = toDoList;
    }

    // public List<String> addTask(String description){
    //     toDoTask.add(description);
    //     for(int i = 0; i < toDoTask.size(); i++){   
    //     System.out.println(toDoTask.get(i));}
    //     return toDoTask;
    // }

    //GETTERS SETTERS

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setToDoList(List<ToDoItem> toDoList) {
        this.toDoList = toDoList;
    }

    public List<ToDoItem> getToDoList() {
        return toDoList;
    }
    
}
