package com.example.tradebooking.dao.impl;

import com.example.estockcore.bean.Customer;
import com.example.tradebooking.dao.DAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class RetrieveCustomerDAOImpl implements DAO {

    @Autowired
    private SessionFactory sessionFactory;

    public RetrieveCustomerDAOImpl() {
    }

    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Customer validateAndRetrieveCustomer(Integer customerId) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
            Root<Customer> customerRoot = criteriaQuery.from(Customer.class);
            criteriaQuery.select(customerRoot);
            Predicate customerIdPredicate = criteriaBuilder.equal(customerRoot.get("customerId"), customerId);

            criteriaQuery.where(customerIdPredicate);

            Query<Customer> query = session.createQuery(criteriaQuery);
            List<Customer> customers = query.getResultList();

            Customer response = customers.isEmpty() ? null : customers.get(0);
            return response;

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
