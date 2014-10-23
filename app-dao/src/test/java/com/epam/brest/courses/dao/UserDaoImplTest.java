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

/**
 * Created by andrew on 22.10.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/testApplicationContextSpring.xml" })
public class UserDaoImplTest {
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
        user.setUserId(3);
        user.setUserName("name");
        user.setLogin("login");
        userDao.addUser(user);

        users = userDao.getUsers();

        assertEquals(sizeBefore, users.size() - 1);
    }

    @Test
    public void removeUser(){
        List<User> users = userDao.getUsers();
        int sizeBefore = users.size();
        userDao.removeUser(1);

        assertEquals(sizeBefore - 1, userDao.getUsers().size());
    }

    @Test
    public void getUserById(){
        User user = userDao.getUserById(2);
        assertNotNull(user);
        assertEquals(2, user.getUserId());
    }

    @Test
    public void getUserByLogin(){
        User user = userDao.getUserByLogin("userLogin2");
        assertNotNull(user);
        assertEquals("userLogin2", user.getLogin());
    }
}
