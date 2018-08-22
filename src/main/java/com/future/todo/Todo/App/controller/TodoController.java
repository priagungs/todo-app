package com.future.todo.Todo.App.controller;

import com.future.todo.Todo.App.model.Todo;
import com.future.todo.Todo.App.repository.TodoRepository;
import com.future.todo.Todo.App.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.print.Pageable;
import java.util.List;

@RestController
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/todos/{user_id}")
    public List<Todo> getTodosByUserId(@PathVariable Long user_id) {
        return todoRepository.findByUserId(user_id);
    }

    @PostMapping("/users/{user_id}/todos")
    public Todo createTodoByUserId(@PathVariable Long user_id, @Valid @RequestBody Todo todo) throws Exception {
        return userRepository.findById(user_id)
                .map(user -> {
                    todo.setUser(user);
                    return todoRepository.save(todo);
                }).orElseThrow(() -> new Exception());
    }

    @PutMapping("/users/{user_id}/todos/{todo_id}")
    public Todo updateTodoByUserId(@PathVariable Long user_id, @PathVariable Long todo_id, @Valid @RequestBody Todo todo) throws Exception {
        if (!userRepository.existsById(user_id)) {
            throw new Exception();
        }

        return todoRepository.findById(todo_id)
                .map(el -> {
                    el.setDescription(todo.getDescription());
                    el.setTitle(todo.getTitle());
                    return todoRepository.save(el);
                }).orElseThrow(() -> new Exception());
    }

    @DeleteMapping("/users/{user_id}/todos/{todo_id}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long user_id,
                                          @PathVariable Long todo_id) throws Exception {
        if(!userRepository.existsById(user_id)) {
            throw new Exception();
        }

        return todoRepository.findById(todo_id)
                .map(el -> {
                    todoRepository.delete(el);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new Exception());

    }

}
