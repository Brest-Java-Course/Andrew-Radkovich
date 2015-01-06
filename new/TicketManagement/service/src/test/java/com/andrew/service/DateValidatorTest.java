package com.andrew.service;

import org.junit.Test;

import java.sql.Date;

import static com.andrew.service.DateValidator.isValidDate;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by andrew on 19.12.14.
 */
public class DateValidatorTest {

    private static DateValidator dateValidator;

    @Test
    public void validDateTest() {

        assertTrue(isValidDate("2014-03-03"));
    }

    @Test
    public void validDateTest2() {

        assertTrue(isValidDate("2014-1-13"));
    }

    @Test
    public void invalidDateTest() {

        assertFalse(isValidDate("2014-13-1"));
    }
}
