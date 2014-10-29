package com.epam.brest.courses.service;

import com.epam.brest.courses.domain.User;
import java.util.List;

public interface UserService {
    public void addUser(User user);
    public List<User> getAllUsers();
    public List<User> getUsersByName(String name);
    public void removeUser(Long userId);
    public User getUserByLogin(String login);
    public User getUserById(Long userId);
    public void updateUser(User user);
}
