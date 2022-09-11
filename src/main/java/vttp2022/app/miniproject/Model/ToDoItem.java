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
    private List<String> toDoTask = new LinkedList<>(); 
    
    public ToDoItem() {
    }

    // public ToDoItem(String description) {
    //     this.description = description;
    // }

    public ToDoItem(int taskCounter, String userId, String description, List<String> toDoTask){
        this.taskCounter = taskCounter;
        this.userId = userId;
        this.description = description;
        this.toDoTask = toDoTask;
        // this.dateCreated = LocalDateTime.now();
    }

    public List<String> addTask(String description){
        toDoTask.add(description);

        return toDoTask;
    }

    //GETTERS SETTERS

    public int getTaskCounter() {
        return taskCounter;
    }

    // public void setTaskCounter(int taskCounter) {
    //     for(int i = 0; i < ToDoList.getToDoList().size(); i++){
    //      this.taskCounter = i;
    //     }
    // }
    

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
    
    public List<String> getToDoTask() {
        return toDoTask;
    }

    public void setToDoTask(List<String> toDoTask) {
        this.toDoTask = toDoTask;
    }
}
