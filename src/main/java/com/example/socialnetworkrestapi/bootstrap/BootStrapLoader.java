package com.example.socialnetworkrestapi.bootstrap;

import com.example.socialnetworkrestapi.domain.Post;
import com.example.socialnetworkrestapi.domain.User;
import com.example.socialnetworkrestapi.service.PersonService;
import com.example.socialnetworkrestapi.service.PostService;
import com.example.socialnetworkrestapi.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@Component
public class BootStrapLoader implements CommandLineRunner {

    private final UserService userService;
    private final PersonService personService;
    private final PostService postService;

    public BootStrapLoader(UserService userService, PersonService personService, PostService postService) {
        this.userService = userService;
        this.personService = personService;
        this.postService = postService;
    }

    @Override
    public void run(String... args) throws Exception {
        User adam = new User(1, "Adam", LocalDate.now().minusYears(10), randomAlphabetic(10), new ArrayList<>());

        Post post = new Post();
        post.setContent("Test content");
        post.setUser(adam);
        adam.setPosts(List.of(post));

        userService.saveAll(List.of(adam, new User(2, "Eve", LocalDate.now().minusYears(20), randomAlphabetic(10), new ArrayList<>())));

    }
}
