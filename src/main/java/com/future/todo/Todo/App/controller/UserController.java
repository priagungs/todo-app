package com.future.todo.Todo.App.controller;

import com.future.todo.Todo.App.model.*;
import com.future.todo.Todo.App.repository.TodoRepository;
import com.future.todo.Todo.App.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public Page<User> getAllUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @GetMapping("/users/{user_id}")
    public User getUserById(@PathVariable Long user_id) throws Exception {
        return userRepository.findById(user_id).orElseThrow(() -> new Exception());
    }

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("/users/{user_id}")
    public User updateUser(@PathVariable Long user_id, @Valid @RequestBody User user) throws Exception {
        return userRepository.findById(user_id)
                .map(element -> {
                    element.setName(user.getName());
                    element.setAddress(user.getAddress());
                    return userRepository.save(element);
                }).orElseThrow(() -> new Exception());
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long userId) throws Exception {
        return userRepository.findById(userId)
                .map(question -> {
                    userRepository.delete(question);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new Exception());
    }

}