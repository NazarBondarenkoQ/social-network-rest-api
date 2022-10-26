package com.example.socialnetworkrestapi.controller;

import com.example.socialnetworkrestapi.domain.Person;
import com.example.socialnetworkrestapi.service.PersonService;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter.serializeAll;
import static com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter.serializeAllExcept;

@RestController
public class PersonController {
    final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping({"person", "persons"})
    public MappingJacksonValue getAllPersons() {
        List<Person> persons = personService.findAll();
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(persons);
        SimpleBeanPropertyFilter simpleBeanPropertyFilter = serializeAllExcept("ssid");

        FilterProvider filter = new SimpleFilterProvider().addFilter("PersonFilter", simpleBeanPropertyFilter);
        mappingJacksonValue.setFilters(filter);

        return mappingJacksonValue;
    }

    @GetMapping({"person/ssid", "persons/ssid"})
    public MappingJacksonValue getAllPersonsWithSsid() {
        List<Person> persons = personService.findAll();
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(persons);
        SimpleBeanPropertyFilter simpleBeanPropertyFilter = serializeAll();

        FilterProvider filter = new SimpleFilterProvider().addFilter("PersonFilter", simpleBeanPropertyFilter);
        mappingJacksonValue.setFilters(filter);

        return mappingJacksonValue;
    }
}
