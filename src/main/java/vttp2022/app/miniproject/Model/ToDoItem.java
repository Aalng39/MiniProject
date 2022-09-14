package vttp2022.app.miniproject.Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class ToDoItem implements Serializable{
    private int taskCounter;
    private String userId;
    private String description;
    private boolean complete;
    private LocalDateTime dateCreated;
    private List<ToDoItem> toDoList = new LinkedList<>();
    
    

    public ToDoItem() {
    }

    // public ToDoItem(String description) {
    //     this.description = description;
    // }

    public ToDoItem(int taskCounter, String userId, String description, List<ToDoItem> toDoList){
        this.taskCounter = taskCounter;
        this.userId = userId;
        this.description = description;
        this.toDoList = toDoList;
        // this.dateCreated = LocalDateTime.now();
    }

    // public List<String> addTask(String description){
    //     toDoTask.add(description);
    //     for(int i = 0; i < toDoTask.size(); i++){   
    //     System.out.println(toDoTask.get(i));}
    //     return toDoTask;
    // }

    //GETTERS SETTERS

    public int getTaskCounter() {
        return taskCounter;
    }

    public void setTaskCounter(int taskCounter) {
        this.taskCounter = taskCounter;
    }
    

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

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }
    
    public void setToDoList(List<ToDoItem> toDoList) {
        this.toDoList = toDoList;
    }

    public List<ToDoItem> getToDoList() {
        return toDoList;
    }
    
}
