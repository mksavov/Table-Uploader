package com.example.application.model.database;

import javax.persistence.*;

@Entity
@Table(name = "users", schema = "table_uploader_vaadin_schema")
public class User {

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Id
    @Column(name = "user_id")
    @SequenceGenerator(name = "ID_GENERATOR", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_GENERATOR")
    private int id;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
