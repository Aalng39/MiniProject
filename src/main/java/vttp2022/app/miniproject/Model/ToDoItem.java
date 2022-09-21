package vttp2022.app.miniproject.Model;

import java.io.Serializable;

public class ToDoItem implements Serializable{
    private int index;
    private String userId;
    private String description;
    private String dateCreated;
    
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    //Constructors

    public ToDoItem() {
    }

    public ToDoItem(String userId, String description, String dateCreated){
        this.userId = userId;
        this.description = description;
        this.dateCreated = dateCreated;       
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
  
}
