package com.example.tradebooking.dao.impl;

import com.example.estockcore.bean.Stock;
import com.example.estockcore.bean.Trade;
import com.example.tradebooking.dao.DAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TradeBookingDAOImpl implements DAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public boolean tradeCapture(Stock stock, boolean isFirstStock, Trade trade) {
        Session session = this.sessionFactory.getCurrentSession();
        Transaction transaction;
        try {
            transaction = session.beginTransaction();
            session.save(trade);
            session.save(stock);
            transaction.commit();
        } catch (Exception ex) {
            System.out.println("Error while storing stock and trade in db: " + ex.getMessage());
            return false;
        } finally {
        }
        return true;
    }

}
