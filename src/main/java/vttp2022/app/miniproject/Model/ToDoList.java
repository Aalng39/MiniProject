package vttp2022.app.miniproject.Model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class ToDoList implements Serializable{
    private List<ToDoItem> toDoList = new LinkedList<>();

    public List<ToDoItem> getToDoList() {
        return toDoList;
    }

    public void setToDoList(List<ToDoItem> toDoList) {
        this.toDoList = toDoList;
    }
}
