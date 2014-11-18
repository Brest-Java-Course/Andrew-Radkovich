package com.epam.brest.courses.dao;

import com.epam.brest.courses.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring-dao-test.xml" })
public class UserDaoImplTest {

    public static final String USER_NAME_3 = "userName3";
    public static final String USER_LOGIN_3 = "userLogin3";
    public static final String USER_LOGIN_2 = "userLogin2";
    public static final String USER_NAME_10 = "Name10";
    public static final String USER_LOGIN_10 = "Login10";
    public static final String USER_LOGIN_11 = "Name11";
    public static final String NEW_USER_LOGIN_11 = "NewName11";

    @Autowired
    private UserDao userDao;

    @Test
    public void getUsers(){
        List<User> userDaoList = userDao.getUsers();
        assertNotNull(userDaoList);
    }

    @Test
    public void addUser(){
        List<User> users = userDao.getUsers();
        int sizeBefore = users.size();
        User user = new User();
        user.setName(USER_NAME_3);
        user.setLogin(USER_LOGIN_3);
        userDao.addUser(user);

        users = userDao.getUsers();

        assertEquals(sizeBefore, users.size() - 1);
    }

    @Test
    public void removeUser(){
        List<User> users = userDao.getUsers();
        int sizeBefore = users.size();
        userDao.removeUser(1L);

        assertEquals(sizeBefore - 1, userDao.getUsers().size());
    }

    @Test
    public void getUserById(){
        User newUser = new User();
        newUser.setLogin(USER_LOGIN_2);
        newUser.setName(USER_LOGIN_2);
        userDao.addUser(newUser);
        User user = userDao.getUserById(2L);
        assertNotNull(user);
    }

    @Test
    public void getUserByLogin() {

        User newUser = new User();
        newUser.setName(USER_NAME_10);
        newUser.setLogin(USER_LOGIN_10);

        userDao.addUser(newUser);

        User user = userDao.getUserByLogin(USER_LOGIN_10);
        assertNotNull(user);
        assertEquals(USER_LOGIN_10, user.getLogin());
    }

    @Test
    public void updateUser() {

        User user = new User();
        user.setName(USER_LOGIN_11);
        user.setLogin(USER_LOGIN_11);
        userDao.addUser(user);

        Long id = userDao.getUserByLogin(USER_LOGIN_11).getUserId();

        User newUser = new User(id, NEW_USER_LOGIN_11, NEW_USER_LOGIN_11);

        userDao.updateUser(newUser);

        user = userDao.getUserById(newUser.getUserId());
        assertEquals(user.getUserId(), newUser.getUserId());
        assertEquals(user.getLogin(), newUser.getLogin());
        assertEquals(user.getName(), newUser.getName());
    }
}