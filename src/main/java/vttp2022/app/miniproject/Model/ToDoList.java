package vttp2022.app.miniproject.Model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class ToDoList implements Serializable{
    // private String userId;
    public static List<ToDoItem> toDoList = new LinkedList<>();

    // public ToDoList(){}

    // public ToDoList(String userId, List<ToDoItem> toDoList){
    //     this.userId = userId;
    //     this.toDoList = toDoList;
    // }
    
    // public String getUserId() {
    //     return userId;
    // }

    // public void setUserId(String userId) {
    //     ToDoItem items = new ToDoItem();
    //     userId = items.getUserId();
    // }

    public static List<ToDoItem> getToDoList() {
        return toDoList;
    }

    public static void setToDoList(List<ToDoItem> toDoList) {
        ToDoList.toDoList = toDoList;
    }

}
