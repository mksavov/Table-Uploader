package com.example.application.model.database;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "personaddresses", schema = "table_uploader_vaadin_schema")
public class PersonAddress {

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "number")
    private int number;

    @Id
    @Column(name = "address_id")
    @SequenceGenerator(name = "ID_GENERATOR", sequenceName = "address_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_GENERATOR")
    private int address_id;
    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<Person> personCollection;

    public PersonAddress(String city, String street, int number, int id) {
        this.city = city;
        this.street = street;
        this.number = number;
        this.address_id = id;
    }

    public PersonAddress() {
    }

    public PersonAddress(String city, String street, int number) {
        this.city = city;
        this.street = street;
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String address) {
        this.street = address;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getId() {
        return address_id;
    }

}
