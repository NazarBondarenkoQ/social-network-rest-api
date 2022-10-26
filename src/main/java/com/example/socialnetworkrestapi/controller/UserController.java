package com.example.socialnetworkrestapi.controller;

import com.example.socialnetworkrestapi.domain.Post;
import com.example.socialnetworkrestapi.domain.User;
import com.example.socialnetworkrestapi.exception.UserNotFoundException;
import com.example.socialnetworkrestapi.service.PostService;
import com.example.socialnetworkrestapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {

    private final UserService userService;
    private final PostService postService;

    public UserController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping(path = {"/users", "/user"})
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping(path = {"user/{id}", "users/{id}"})
    public EntityModel<User> getUser(@PathVariable int id) {
        User user = userService.findById(id).orElse(null);

        if (user == null) {
            throw new UserNotFoundException(String.format("User with id: %s doesn't exist", id));
        }

        EntityModel<User> userEntityModel = EntityModel.of(user);
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getAllUsers());

        userEntityModel.add(link.withRel("all-users"));

        return userEntityModel;
    }

    @GetMapping(path = {"user/{id}/posts", "users/{id}/posts"})
    public CollectionModel<Post> getUsersPosts(@PathVariable int id) {
        List<Post> posts = userService.findById(id).orElse(new User()).getPosts();

        if (posts.isEmpty()) {
            throw new UserNotFoundException(String.format("User with id: %s has no posts", id));
        }

        CollectionModel<Post> postsCollectionModel = CollectionModel.of(posts);
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getAllUsers());

        postsCollectionModel.add(link.withRel("all-users"));

        return postsCollectionModel;
    }

    @GetMapping(path = "user/{id}/post/{postId}")
    public EntityModel<Post> getSpecificPost(@PathVariable int id, @PathVariable int postId) {

        User user = userService.findById(id).orElse(null);

        if (user == null) {
            throw new UserNotFoundException(String.format("User with id: %s doesn't exist", id));
        }

        Post post = user.getPosts().stream().filter(p -> p.getId() == postId).findAny().orElse(new Post());
        EntityModel<Post> postEntityModel = EntityModel.of(post);

        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getUsersPosts(id));

        postEntityModel.add(link.withRel("all-posts-by-user"));

        return postEntityModel;
    }

    @DeleteMapping(path = {"user/{id}", "users/{id}"})
    public void deleteUser(@PathVariable int id) {
        userService.deleteById(id);
    }

    @PostMapping(path = {"/users", "/user"})
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        User savedUser = userService.save(user);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @PostMapping(path = {"users/{id}/post", "user/{id}/post"})
    public ResponseEntity<Object> addPostToUser(@PathVariable int id, @RequestBody Post post) {
        Optional<User> user = userService.findById(id);

        user.ifPresent(post::setUser);

        Post savedPost = postService.save(post);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }
}
