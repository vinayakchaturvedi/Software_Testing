package com.example.customerservice.service.impl;

import com.example.customerservice.dao.impl.CustomerOperationDAOImpl;
import com.example.estockcore.bean.Customer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class CustomerOperationServiceImplTest {

    @Mock
    CustomerOperationDAOImpl customerOperationDAO;
    @InjectMocks
    CustomerOperationServiceImpl customerOperationService = new CustomerOperationServiceImpl();

    @Before
    public void initMocks() {
        CustomerOperationServiceImpl.latestTradingAccount = null;
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void registerCustomer() {
        Customer customer = new Customer();
        when(customerOperationDAO.registerCustomer(any(Customer.class))).thenReturn(true);
        customerOperationService.registerCustomer(customer);
        Assert.assertEquals(new Long(2L), customer.getTradingAccount());
    }


    /**
     * 4 prime paths
     * 0,1,2,3,4,5,7
     * 0,1,2,3,4,6,7
     * 0,1,3,4,5,7
     * 0,1,3,4,6,7
     */
    @Test
    public void testPath1() {
        //0,1,2,3,4,5,7
        Customer mehak = new Customer();
        mehak.setCustomerName("Mehak");

        when(customerOperationDAO.getLastTradingAccount()).thenReturn(0L);
        when(customerOperationDAO.registerCustomer(eq(mehak))).thenReturn(true);

        Customer customer = customerOperationService.registerCustomer(mehak);

        Assert.assertNotNull(customer);
        Assert.assertEquals(Long.valueOf(1), customer.getTradingAccount());
    }


    /**
     * Validate and Retrieve Customer
     * <p>
     * 3 Prime Paths
     * [0,1,3,4,6]
     * [0,1,3,5,6]
     * [0,1,2,6]
     */

    @Test
    public void validateAndRetrieveCustomerPath1() {
        //[0,1,3,4,6]

        Customer customer = new Customer();
        customer.setEmailId("abc@gmail.com");
        customer.setPassword("");

        Customer response = customerOperationService.validateAndRetrieveCustomer(customer, true);
        Assert.assertNull(response);
    }

    @Test
    public void validateAndRetrieveCustomerPath2() {
        //[0,1,3,5,6]

        Customer customer = new Customer();
        customer.setEmailId("abc@gmail.com");
        customer.setPassword("abcd");

        when(customerOperationDAO.validateAndRetrieveCustomer(any(Customer.class), eq(true))).thenReturn(customer);
        Customer response = customerOperationService.validateAndRetrieveCustomer(customer, true);
        Assert.assertNotNull(response);
    }

    @Test
    public void validateAndRetrieveCustomerPath3() {
        //[0,1,2,6]

        Customer customer = new Customer();
        customer.setEmailId(null);
        customer.setPassword("abcd");

        Customer response = customerOperationService.validateAndRetrieveCustomer(customer, true);
        Assert.assertNull(response);
    }

}