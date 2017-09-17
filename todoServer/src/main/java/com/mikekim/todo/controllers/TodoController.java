package com.mikekim.todo.controllers;

import com.mikekim.todo.models.TodoModel;
import com.mikekim.todo.models.User;
import com.mikekim.todo.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
public class TodoController {

    @Autowired
    TodoRepository todoRepository;

    @GetMapping("/list")
    public Iterable<TodoModel> findAll(@AuthenticationPrincipal User user) {
        System.out.println(user.getUsername());
        return todoRepository.findAll();
    }

    @GetMapping("/test")
    public Iterable<TodoModel> test(@AuthenticationPrincipal User user) {
        Iterable<TodoModel> allTodos =  todoRepository.findAll();
        List<TodoModel> todoList = new ArrayList<>();
        allTodos.forEach(todoList::add);
        List<TodoModel> myTodos = todoList.stream().filter(elem -> elem.getUser().getId() == user.getId()).collect(Collectors.toList());
        for(TodoModel t: allTodos) {
            System.out.println(t.getMessage() + t.getUser().getId());
        }
        return myTodos;
    }


    @PostMapping("/post")
    public ResponseEntity<TodoModel> postOne(@RequestBody TodoModel todoModel) {
        System.out.println("hello");
        ResponseEntity<TodoModel> responseEntity;
        if(todoRepository.findByMessage(todoModel.getMessage()) == null) {
            todoRepository.save(todoModel);
            responseEntity = new ResponseEntity<TodoModel>(todoModel, HttpStatus.CREATED);
            return  responseEntity;
        }

        return new ResponseEntity<TodoModel>(HttpStatus.CONFLICT);
    }

    @RequestMapping(value="/bye", method = RequestMethod.GET)
    public void logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
    }
}
