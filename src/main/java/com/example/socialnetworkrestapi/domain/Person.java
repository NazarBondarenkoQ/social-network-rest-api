package com.example.socialnetworkrestapi.domain;

import com.fasterxml.jackson.annotation.JsonFilter;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonFilter("PersonFilter")
@Entity
@Data
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue
    private Integer id;

    private String firstName;
    private String lastName;
    private String SSID;
}
