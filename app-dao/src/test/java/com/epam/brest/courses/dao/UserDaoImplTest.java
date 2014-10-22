package com.epam.brest.courses.dao;

import com.epam.brest.courses.dao.UserDao;
import com.epam.brest.courses.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.swing.*;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by andrew on 22.10.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/home/andrew/SampleUserManagment/app-dao/src/test/resourses/testApplicationContextSpring.xml")
public class UserDaoImplTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void getUsers(){
        List<User> userDaoList = userDao.getUsers();
        assertNotNull(userDaoList);
    }
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
}