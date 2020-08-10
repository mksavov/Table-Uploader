package com.example.application.services;

import com.example.application.dao.AddressesRepository;
import com.example.application.dao.PersonRepository;
import com.example.application.dao.TableUploadLogsRepository;
import com.example.application.dao.UserRepository;
import com.example.application.model.database.Person;
import com.example.application.model.database.UploadedFileLogs;
import com.example.application.model.database.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableService {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private AddressesRepository addressesRepository;

    @Autowired
    private TableUploadLogsRepository tableuploadlogsRepository;

    @Autowired
    private UserRepository userRepository;

    public void save(Person person) {
        if (person.getAddress() != null) {
            addressesRepository.save(person.getAddress());
        }
        repository.save(person);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public List<User> retrieveUsers() {return userRepository.findAll();}

    public void saveFile(UploadedFileLogs tableuploadlogs) {
        tableuploadlogsRepository.save(tableuploadlogs);
    }

    public void delete(Person person) {
        addressesRepository.delete(person.getAddress());
        repository.delete(person);
    }

    public List<Person> retrieve() {
        return repository.findAll();
    }

    public List<Person> retrieve(String name) {
        return repository.findByName(name);
    }
}
