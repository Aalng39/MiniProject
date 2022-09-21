package vttp2022.app.miniproject.Service;

import vttp2022.app.miniproject.Model.ToDoList;

public interface ToDoRepo {
    
        public void save(final ToDoList toDoList);
    
        public ToDoList loginWithId(final String userId);

}
