package com.example.customerservice.dao;

import com.example.estockcore.bean.Customer;
import org.hibernate.SessionFactory;

public interface DAO {

    public void setSessionFactory(SessionFactory sf);

    public Customer validateAndRetrieveCustomer(final Customer customer, boolean requirePassword);

    public boolean registerCustomer(final Customer customer);

    public Long getLastTradingAccount();
}
