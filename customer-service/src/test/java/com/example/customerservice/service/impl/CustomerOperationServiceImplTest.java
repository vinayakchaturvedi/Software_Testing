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

    @Test
    public void validateAndRetrieveCustomerValid() {
        Customer customer = new Customer();
        customer.setEmailId("abc@gmail.com");
        customer.setPassword("root");
        when(customerOperationDAO.validateAndRetrieveCustomer(any(Customer.class), eq(true))).thenReturn(customer);
        Customer response = customerOperationService.validateAndRetrieveCustomer(customer, true);
        Assert.assertNotNull(response);
    }

    @Test
    public void validateAndRetrieveCustomerInValid() {
        Customer customer = new Customer();
        customer.setEmailId("abc@gmail.com");
        customer.setPassword("");
        when(customerOperationDAO.validateAndRetrieveCustomer(any(Customer.class), eq(true))).thenReturn(customer);
        Customer response = customerOperationService.validateAndRetrieveCustomer(customer, true);
        Assert.assertNull(response);
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
}