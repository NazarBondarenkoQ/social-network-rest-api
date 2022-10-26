package com.example.socialnetworkrestapi.service;

import com.example.socialnetworkrestapi.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostService extends JpaRepository<Post, Integer> {
}
