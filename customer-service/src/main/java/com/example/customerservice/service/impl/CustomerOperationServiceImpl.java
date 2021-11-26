package com.example.customerservice.service.impl;

import com.example.customerservice.dao.impl.CustomerOperationDAOImpl;
import com.example.customerservice.service.Service;
import com.example.estockcore.bean.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerOperationServiceImpl implements Service {

    @Autowired
    CustomerOperationDAOImpl dao;
    public static Long latestTradingAccount = null;

    public CustomerOperationServiceImpl() {
    }

    public void loadLatestTradingAccount() {
        latestTradingAccount = dao.getLastTradingAccount();
        System.out.println("Inside Post construct: " + latestTradingAccount);
    }

    @Override
    public Customer registerCustomer(Customer customer) {
        //1
        if (latestTradingAccount == null || latestTradingAccount == 0) { //2
            loadLatestTradingAccount();
        }
        //3
        customer.setTradingAccount(++latestTradingAccount);
        //4
        if (!dao.registerCustomer(customer)) { //5
            latestTradingAccount--;
            return null;
        }
        //6
        return customer;
    }

    @Override
    public Customer validateAndRetrieveCustomer(Customer customer, boolean requiredPassword) {
        //1
        if (customer.getEmailId() == null || customer.getEmailId().isEmpty())    //2
            return null;

        //3
        if (requiredPassword && (customer.getPassword() == null || customer.getPassword().isEmpty()))  //4
            return null;
        //5
        return dao.validateAndRetrieveCustomer(customer, requiredPassword);
    }
}
