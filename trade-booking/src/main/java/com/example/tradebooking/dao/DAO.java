package com.example.tradebooking.dao;

import com.example.estockcore.bean.Stock;
import com.example.estockcore.bean.Trade;
import org.hibernate.SessionFactory;

public interface DAO {

    void setSessionFactory(SessionFactory sessionFactory);
}
