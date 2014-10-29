package com.epam.brest.courses.service;

import com.epam.brest.courses.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring-service-test.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    public static final String ADMIN = "admin";

    @Before
    public void setUp() throws Exception {
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullUser() throws Exception {
        userService.addUser(null);
    }


/*    @Test(expected = IllegalArgumentException.class)
    public void testAddUser() throws IllegalArgumentException {
        userService.addUser(new User(20L, "login20", "name20"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEmptyUser() throws IllegalArgumentException {
        userService.addUser(new User());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddUserNullId() throws IllegalArgumentException {
        userService.addUser(new User(null, "newLogin", "newName"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddUserNullLogin() throws IllegalArgumentException {
        userService.addUser(new User(21L, null, "newName"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddUserNullName() throws IllegalArgumentException {
        userService.addUser(new User(21L, "newLogin", null));
    }

    @Test
    public void testGetUsers() throws Exception {
        List<User> users = userService.getAllUsers();
        assertNotNull(users);
        assertFalse(users.isEmpty());
    }

    @Test
    public void testGetUserById() throws IllegalArgumentException {
        userService.getUserById(21L);
    }

    @Test
    public void testGetUserByNullId() throws IllegalArgumentException {
        userService.getUserById(null);
    }

    @Test
    public void testGetUserByZeroId() throws IllegalArgumentException {
        userService.getUserById(0L);
    }

*/
}
