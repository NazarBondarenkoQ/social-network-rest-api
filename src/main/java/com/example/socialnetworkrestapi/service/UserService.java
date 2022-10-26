package com.example.socialnetworkrestapi.service;

import com.example.socialnetworkrestapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends JpaRepository<User, Integer> {

}
