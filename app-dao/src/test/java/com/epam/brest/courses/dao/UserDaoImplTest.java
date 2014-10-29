package com.epam.brest.courses.dao;

import com.epam.brest.courses.dao.UserDao;
import com.epam.brest.courses.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.core.io.ClassPathResource;

import javax.swing.*;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring-dao-test.xml" })
public class UserDaoImplTest {

    public static final String USER_NAME_3 = "userName3";
    public static final String USER_LOGIN_3 = "userLogin3";
    public static final String USER_LOGIN_2 = "userLogin2";
    public static final String USER_NEW_LOGIN_2 = "newLogin2";
    public static final String USER_NEW_NAME_2 = "newName2";
    public static final String USER_NEW_NAME_10 = "newName10";
    public static final String USER_NEW_LOGIN_10 = "newName10";
    public static final String USER_NEW_LOGIN_11 = "newName11";

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
        user.setUserId(3L);
        user.setUserName(USER_NAME_3);
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
        User user = userDao.getUserById(2L);
        assertNotNull(user);
    }

    @Test
    public void getUserByLogin(){
        User user = userDao.getUserByLogin(USER_LOGIN_2);
        assertNotNull(user);
        assertEquals(USER_LOGIN_2, user.getLogin());
    }

    @Test
    public void updateUser(){
        User userNew = new User(2L, USER_NEW_NAME_2, USER_NEW_LOGIN_2);
        userDao.updateUser(userNew);
        User user = userDao.getUserById(2L);
        assertEquals(user.getLogin(), userNew.getLogin());
        assertEquals(user.getUserName(), userNew.getUserName());
    }

    @Test
    public void getUsersByName(){
        User user10 = new User(10L, USER_NEW_LOGIN_10, USER_NEW_NAME_10);
        User user11 = new User(11L, USER_NEW_LOGIN_11, USER_NEW_NAME_10);
        userDao.addUser(user10);
        userDao.addUser(user11);
        List<User> userList = userDao.getUsersByName(USER_NEW_NAME_10);
        assertNotNull(userList);
        assertEquals(2, userList.size());
    }
}
