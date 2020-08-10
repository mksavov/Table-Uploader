package com.example.application.dao;

import com.example.application.model.database.PersonAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressesRepository extends JpaRepository<PersonAddress, Integer> {
}
