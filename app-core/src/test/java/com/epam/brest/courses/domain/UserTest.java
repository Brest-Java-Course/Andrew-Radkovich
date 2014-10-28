package com.epam.brest.courses.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    public static final String USER_NAME = "username";
    public static final Long USER_ID = 1L;
    private User user;

    @Before
    public void setUp() throws Exception {
        user = new User();
    }

    @Test
    public void testGetUserName() throws Exception {
        user.setUserName(USER_NAME);
        assertEquals(USER_NAME, user.getUserName());
    }

    @Test
    public void testGetUserId() throws Exception {
        user.setUserId(USER_ID);
        assertEquals(USER_ID , user.getUserId());
    }

    @Test
    public void testGetULogin() throws Exception {
        user.setLogin("Tom Riddle");
        assertEquals("Tom Riddle", user.getLogin());
    }
}