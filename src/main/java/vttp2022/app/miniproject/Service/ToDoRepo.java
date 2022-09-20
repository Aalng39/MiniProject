package vttp2022.app.miniproject.Service;

import vttp2022.app.miniproject.Model.ToDoItem;

public interface ToDoRepo {
    
        public void save(final ToDoItem toDoItem);
    
        public ToDoItem loginWithId(final String userId);

}
