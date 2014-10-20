package com.epam.brest.courses.dao;

import com.epam.brest.courses.domain.User;

import java.util.List;

public interface UserDao {
    public List<User> getUsers();
    public void removeUser(long UserId);
    public void addUser(User user);
}
