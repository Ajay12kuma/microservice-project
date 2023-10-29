package com.user.service.service;

import com.user.service.entities.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    List<User> getAllUser();

    User getUser(String userId);

}