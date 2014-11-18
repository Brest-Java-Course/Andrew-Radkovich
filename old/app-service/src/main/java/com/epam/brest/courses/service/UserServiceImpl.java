package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.UserDao;
import com.epam.brest.courses.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.util.Assert;

import java.util.List;

public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger();

    public static final String USER_ID_IS_NULL = "User Id is null";
    public static final String USER_LOGIN_IS_EMPTY = "User login is empty";
    public static final String NAME_IS_EMPTY_OR_NULL = "Name is empty or null";
    public static final String USER_IS_NULL = "User is null";
    public static final String SOME_USER_S_FIELD_IS_EMPTY_OR_NULL = "Some user's field is empty or null";
    public static final String SUCH_USER_EXISTS = "Such user exists";

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {

        this.userDao = userDao;
    }

    @Override
    public Long addUser(User user) {
        Assert.notNull(user);
        Assert.isNull(user.getUserId());
        Assert.notNull(user.getLogin(), "User login should be specified.");
        Assert.notNull(user.getName(), "User name should be specified.");
        User existingUser = getUserByLogin(user.getLogin());
        if (existingUser != null) {
            throw new IllegalArgumentException("User is present in DB");
        }
        return userDao.addUser(user);
    }


    @Override
    public User getUserByLogin(String login) {

        if ("".equals(login) || null == login) {
            LOGGER.debug(USER_LOGIN_IS_EMPTY);
            throw new IllegalArgumentException(USER_LOGIN_IS_EMPTY);
        }
        User user = null;
        try {
            user = userDao.getUserByLogin(login);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("getUserByLogin({}) ", login);
        }
        return user;
    }

    @Override
    public User getUserById(Long userId) {

        User user = null;
        if ( null == userId ) {
            LOGGER.debug(USER_ID_IS_NULL);
            throw new IllegalArgumentException(USER_ID_IS_NULL);
        }
        try {
            user = userDao.getUserById(userId);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("getUserById({})", userId);
        }
        return user;
    }

    @Override
    public List<User> getUsers() {
        return userDao.getUsers();
    }

    @Override
    public void removeUser(Long userId) {

        if(null == userId) {
            LOGGER.debug(USER_ID_IS_NULL);
            throw new IllegalArgumentException(USER_ID_IS_NULL);
        }
        userDao.removeUser(userId);
    }

    @Override
    public void updateUser(User user) {

        if (null == user) {
            LOGGER.debug(USER_IS_NULL);
            throw new IllegalArgumentException(USER_IS_NULL);
        } else if (null == user.getUserId() || null == user.getLogin() || null == user.getName() ||
                 "".equals(user.getLogin()) || "".equals(user.getName())) {
            LOGGER.debug(SOME_USER_S_FIELD_IS_EMPTY_OR_NULL);
            throw new IllegalArgumentException(SOME_USER_S_FIELD_IS_EMPTY_OR_NULL);
        } else if ("Admin".equals(user.getLogin())) {
            throw new IllegalArgumentException("You can't change admin fields");
        }

        User existingUser = userDao.getUserByLogin(user.getLogin());

        if(null == existingUser) {
            userDao.updateUser(user);
        } else {
            LOGGER.debug(SUCH_USER_EXISTS);
            throw new IllegalArgumentException(SUCH_USER_EXISTS);
        }
    }

/*    @Override
    public List<User> getUsersByName(String name) {

        if ( "".equals(name) || null == name ) {
            LOGGER.debug(NAME_IS_EMPTY_OR_NULL);
            throw new IllegalArgumentException(NAME_IS_EMPTY_OR_NULL);
        }
        return userDao.getUsersByName();
    }
*/
}
