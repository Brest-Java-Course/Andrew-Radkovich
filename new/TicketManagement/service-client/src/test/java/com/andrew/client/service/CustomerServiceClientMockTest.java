package com.andrew.client.service;

import com.andrew.client.rest.CustomerRestClient;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by andrew on 5.1.15.
 */
public class CustomerServiceClientMockTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRestClient customerRestClient;

    @Before
    public void initMocks() {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void checkExistingCustomerByNumberMockTest() {

        when(customerRestClient.checkExistingCustomer(anyString())).thenReturn(Boolean.TRUE);

        Boolean exists = customerService.checkExistingCustomerByNumber(anyString());
        verify(customerRestClient).checkExistingCustomer(anyString());
        assertEquals(Boolean.TRUE, exists);
    }
}
