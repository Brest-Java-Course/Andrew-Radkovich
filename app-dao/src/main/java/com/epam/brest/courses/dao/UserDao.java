package com.epam.brest.courses.dao;

import com.epam.brest.courses.domain.User;

import java.util.List;

public interface UserDao {
    public List<User> getUsers();
    public void removeUser(Long UserId);
    public void addUser(User user);
    public User getUserById(Long userId);
    public User getUserByLogin(String login);
    public void updateUser(User user);
    public List<User> getUsersByName(String name);
}
