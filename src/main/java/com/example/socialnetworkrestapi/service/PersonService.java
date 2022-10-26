package com.example.socialnetworkrestapi.service;

import com.example.socialnetworkrestapi.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface PersonService extends JpaRepository<Person, Integer> {

}
