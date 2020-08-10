package com.example.application.model.database;


import javax.persistence.*;

@Entity
@Table(name = "persons", schema = "table_uploader_vaadin_schema")
public class Person {

    @Column(name = "first_name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private PersonAddress address;

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "ID_GENERATOR", sequenceName = "person_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_GENERATOR")
    private long id;


    public Person() {
    }

    public Person(String name, String lastName, String phoneNumber, PersonAddress address, int id) {
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.id = id;
    }

    public Person(String name, String lastName, String phoneNumber, PersonAddress address) {
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public Person(String name, String lastName, String phoneNumber, long id) {
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.id = id;
    }

    public Person(String name, String lastName, String phoneNumber) {
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String photo) {
        this.phoneNumber = photo;
    }

    public PersonAddress getAddress() {
        return address;
    }

    public void setAddress(PersonAddress address) {
        this.address = address;
    }
}
