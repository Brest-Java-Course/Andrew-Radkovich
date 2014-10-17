package com.epam.brest.courses.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    User user;

    @Before
    public void setUp() throws Exception {
        user = new User();
    }

    @Test
    public void testGetUserName() throws Exception {
        user.setUserName("Lord Voldemort");
        assertEquals("Lord Voldemort", user.getUserName());
    }

    @Test
    public void testGetUserId() throws Exception {
        user.setUserId(1);
        assertEquals(1, user.getUserId());
    }

    @Test
    public void testGetULogin() throws Exception {
        user.setLogin("Tom Riddle");
        assertEquals("Tom Riddle", user.getLogin());
    }
}