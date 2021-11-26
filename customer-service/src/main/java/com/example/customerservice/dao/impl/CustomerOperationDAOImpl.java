package com.example.customerservice.dao.impl;

import com.example.customerservice.dao.DAO;
import com.example.estockcore.bean.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class CustomerOperationDAOImpl implements DAO {

    @Autowired
    private SessionFactory sessionFactory;

    public CustomerOperationDAOImpl() {
    }

    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    @Override
    public Customer validateAndRetrieveCustomer(final Customer customer, boolean requirePassword) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
            Root<Customer> customerRoot = criteriaQuery.from(Customer.class);
            criteriaQuery.select(customerRoot);
            Predicate userName = criteriaBuilder.like(customerRoot.get("emailId"), customer.getEmailId());
            Predicate password = criteriaBuilder.like(customerRoot.get("password"), customer.getPassword());

            if (requirePassword)
                criteriaQuery.where(criteriaBuilder.and(userName, password));
            else
                criteriaQuery.where(userName);

            Query<Customer> query = session.createQuery(criteriaQuery);
            List<Customer> customers = query.getResultList();

            Customer response = customers.isEmpty() ? null : customers.get(0).shallowCopy();
            return response;

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public boolean registerCustomer(Customer customer) {
        Session session = this.sessionFactory.getCurrentSession();
        Transaction transaction;
        try {
            transaction = session.beginTransaction();
            session.save(customer);
            transaction.commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        } finally {
        }
        return true;
    }

    @Override
    public Long getLastTradingAccount() {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
            Root<Customer> customerRoot = criteriaQuery.from(Customer.class);
            criteriaQuery.select(criteriaBuilder.max(customerRoot.get("tradingAccount")));

            Query<Long> query = session.createQuery(criteriaQuery);
            List<Long> ids = query.getResultList();

            return ids == null || ids.isEmpty() || ids.get(0) == null || ids.get(0).equals(0L) ? 10000L : ids.get(0);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return 10000L;
        }
    }
}
