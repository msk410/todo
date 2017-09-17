package com.mikekim.todo.repositories;

import com.mikekim.todo.models.TodoModel;
import org.springframework.data.repository.CrudRepository;

public interface TodoRepository  extends CrudRepository<TodoModel, Long> {
    TodoModel findByMessage(String text);
}
