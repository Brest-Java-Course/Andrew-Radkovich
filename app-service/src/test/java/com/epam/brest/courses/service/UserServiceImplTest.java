package com.epam.brest.courses.service;

import com.epam.brest.courses.domain.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring-services-test.xml"})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
@Transactional
public class UserServiceImplTest {

    public static final String ADMIN = "Admin";

    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception {

    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullUser() throws Exception {
        userService.addUser(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEmptyUser() throws Exception {
        userService.addUser(new User());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNotNullIdUser() throws Exception {
        userService.addUser(new User(12L, "", ""));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddUserWithSameLogin() throws Exception {
        userService.addUser(new User(null, ADMIN, ADMIN));
        userService.addUser(new User(null, ADMIN, ADMIN));
    }

    @Test
    public void testAddUser() throws Exception {
        userService.addUser(new User(null, ADMIN, ADMIN));
        User user = userService.getUserByLogin(ADMIN);
        Assert.assertEquals(ADMIN, user.getLogin());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetUserByNullId() throws Exception {
        userService.getUserById(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetUserByEmptyLogin() throws Exception {
        userService.getUserByLogin("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetUserByNullLogin() throws Exception {
        userService.getUserByLogin(null);
    }

    @Test
    public void testGetUsers() throws Exception {
        List<User> userList = userService.getUsers();
        Assert.assertNotNull(userList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateUserNullUser() throws Exception {
        userService.updateUser(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateUserAdminLogin() throws Exception {
        userService.updateUser(new User(5L, ADMIN, ADMIN));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveUserNullId() throws Exception {
        userService.removeUser(null);
    }
}