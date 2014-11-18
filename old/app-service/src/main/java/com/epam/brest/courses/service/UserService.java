package com.epam.brest.courses.service;

import com.epam.brest.courses.domain.User;

import java.util.List;

public interface UserService {

    public Long addUser(User user);

    public User getUserByLogin(String login);

    public User getUserById(Long userId);

    //public List<User> getUsersByName(String name);

    public List<User> getUsers();

    public void removeUser(Long userId);

    public void updateUser(User user);
}
