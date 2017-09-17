package com.mikekim.todo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "todo")
public class TodoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String message;
    private boolean finished;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    public TodoModel(Long id, String message, boolean finished, User user) {
        this.id = id;
        this.message = message;
        this.finished = finished;
        this.user = user;
    }

    public TodoModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
