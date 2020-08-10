package com.example.application.controller;

import com.example.application.model.database.Person;
import com.example.application.services.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    @Autowired
    TableService service;

    @PostMapping("/savePerson")
    public String savePerson(@RequestBody Person person) {
        service.save(person);
        return "Person saved";
    }

    @GetMapping("/getAllPersons")
    public List<Person> getAll() {
        return service.retrieve();
    }

    @GetMapping("/getPerson/{name}")
    public List<Person> getByName(@PathVariable String name) {
        return service.retrieve(name);
    }

    @DeleteMapping("/deletePerson/{id}")
    public String deletePersonById(List<Person> personList) {
        for (int i = 0; i < personList.size(); i++) {
            service.delete(personList.get(i));
        }
        return "Person deleted";
    }
}
