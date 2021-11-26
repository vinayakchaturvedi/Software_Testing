package com.example.customerservice.service;

import com.example.estockcore.bean.Customer;

public interface Service {

    public Customer registerCustomer(final Customer customer);

    public Customer validateAndRetrieveCustomer(final Customer customer, boolean requiredPassword);
}
