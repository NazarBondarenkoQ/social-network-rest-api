package com.example.socialnetworkrestapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

//@JsonIgnoreProperties("password") - class level annotation. Used when enumeration of properties to ignore is large.
//@JsonFilter("UserFilter") - used for dynamic filtering
@Entity(name = "user_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "posts")
public class User {

    @Id
    @GeneratedValue
    private int id;

    @NotBlank(message = "Name can't be blank")
    @JsonProperty("user_name")
    private String name;

    @Past(message = "Date of birth must be in the past")
    @JsonProperty("birth_date")
    private LocalDate birthDate;

    @JsonIgnore
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts;

}
