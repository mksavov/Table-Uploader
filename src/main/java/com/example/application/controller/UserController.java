package com.example.application.controller;

import com.example.application.model.database.User;
import com.example.application.services.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    TableService service;

    @PostMapping("/saveUser")
    public String saveUser(@RequestBody User user) {
        service.saveUser(user);
        return "User saved";
    }

    @GetMapping("/getAllUsers")
    public List<User> getAll() {
        return service.retrieveUsers();
    }

    @GetMapping("/checkUserCredentials")
    public boolean checkUserCredentials(@RequestBody User user) {
        List<User> users = service.retrieveUsers();
        for (User value : users) {
            if (user.getUsername().equals(value.getUsername()) && user.getPassword().equals(value.getPassword())) {
                return true;
            }
        }
        return false;
    }
}
