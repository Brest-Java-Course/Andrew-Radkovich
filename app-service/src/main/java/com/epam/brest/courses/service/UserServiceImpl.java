package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.UserDao;
import com.epam.brest.courses.domain.User;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.util.Assert;

public class UserServiceImpl implements UserService{
    private static final Logger LOGGER = LogManager.getLogger(UserService.class);
    public static final String USER_IS_NULL = "User is null";
    public static final String USER_ID_IS_NULL = "User ID is null";
    public static final String USER_LOGIN_IS_NULL = "User login is null";
    public static final String USER_NAME_IS_NULL = "User name is null";
    public static final String USER_ALREADY_EXISTS = "User with same login already exists";
    public static final String USERNAME_IS_EMPTY = "Username is empty";
    public static final String USER_DOES_NOT_EXIST = "User does not exist";

    private UserDao userDao;

    public void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }

    @Override
    public void addUser(User user) {
        Assert.notNull(user);
        Assert.isNull(user.getUserId());
        Assert.notNull(user.getLogin(), "User login should be specified.");
        Assert.notNull(user.getName(), "User name should be specified.");
        if ( null != userDao.getUserByLogin(user.getLogin())){
            LOGGER.debug(USER_ALREADY_EXISTS);
            throw new IllegalArgumentException(USER_ALREADY_EXISTS);
        }
        //if we are here user is OK, so we can add him
        userDao.addUser( user );
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getUsers();
    }

    @Override
    public List<User> getUsersByName(String name) {
        if ( "".equals( name ) ){
            throw new IllegalArgumentException(USERNAME_IS_EMPTY);
        }
        //return userDao.getUsersByName(name);
        return userDao.getUsers();
    }

    @Override
    public void removeUser(Long userId) {
        if (null == userId ){
            LOGGER.debug(USER_ID_IS_NULL);
            throw new IllegalArgumentException(USER_ID_IS_NULL);
        }
        userDao.removeUser(userId);
    }

    @Override
    public User getUserByLogin(String login) {
        if (null == login ){
            LOGGER.debug(USER_LOGIN_IS_NULL);
            throw new IllegalArgumentException(USER_LOGIN_IS_NULL);
        }
        return userDao.getUserByLogin(login);
    }

    @Override
    public User getUserById(Long userId) {
        if (null == userId ){
            LOGGER.debug(USER_ID_IS_NULL);
            throw new IllegalArgumentException(USER_ID_IS_NULL);
        }
        return userDao.getUserById(userId);
    }

    @Override
    public void updateUser(User user) {
        if ( null == userDao.getUserById(user.getUserId())){
            throw new IllegalArgumentException(USER_DOES_NOT_EXIST);
        }
        userDao.updateUser(user);
    }
}
