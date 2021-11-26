package com.example.tradebooking.service.impl;

import com.example.estockcore.bean.Customer;
import com.example.estockcore.bean.Stock;
import com.example.estockcore.bean.Trade;
import com.example.tradebooking.dao.impl.RetrieveCustomerDAOImpl;
import com.example.tradebooking.dao.impl.TradeBookingDAOImpl;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class TradeBookingServiceImplTest {

    @Mock
    private RetrieveCustomerDAOImpl retrieveCustomerDAO;
    @Mock
    private TradeBookingDAOImpl tradeBookingDAO;
    @Mock
    JsonNode jsonNode, jsonNode2;
    @InjectMocks
    TradeBookingServiceImpl tradeBookingService = new TradeBookingServiceImpl();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * 6 Primes Paths
     * [1,2,3,4,6,7,8,10,14,15,16,17]
     * [1,2,3,4,6,7,8,9,11,13,17]
     * [1,2,3,4,6,7,8,9,11,12,17]
     * [1,2,3,4,6,7,8,10,14,16,17]
     * [1,2,3,5,17]
     * [1,2,3,4,6,7,6,7,8,10,14,16,17]
     * <p>
     * <p>
     * <p>
     * 5 test paths are needed for Edge Coverage
     * [1,2,3,5,17]
     * [1,2,3,4,6,7,8,9,11,13,17]
     * [1,2,3,4,6,7,8,9,11,12,17]
     * [1,2,3,4,6,7,6,7,8,10,14,16,17]
     * [1,2,3,4,6,7,8,10,14,15,16,17]
     */

    @Test
    public void testPath1() throws CloneNotSupportedException {

        //[1,2,3,4,6,7,8,10,14,15,16,17]

        Customer customer = new Customer();
        customer.setCustomerName("Mehak");
        Stock stock = new Stock();
        stock.setStockName("MSFT");
        stock.setTotalAvailableQuantity(200L);
        stock.setSoldQuantity(10L);
        customer.getStockList().add(stock);
        stock.setCustomer(customer);

        when(jsonNode.get(eq("customerId"))).thenReturn(jsonNode);
        when(jsonNode.asInt()).thenReturn(1);
        when(jsonNode.get(eq("stockName"))).thenReturn(jsonNode);
        when(jsonNode.textValue()).thenReturn("TSLA");
        when(jsonNode.get(eq("side"))).thenReturn(jsonNode2);
        when(jsonNode2.textValue()).thenReturn("BUY");
        when(jsonNode.get(eq("quantity"))).thenReturn(jsonNode);
        when(jsonNode.get(eq("price"))).thenReturn(jsonNode);
        when(jsonNode.asLong()).thenReturn(100L);
        when(jsonNode.asDouble()).thenReturn(10D);

        when(retrieveCustomerDAO.validateAndRetrieveCustomer(eq(1))).thenReturn(customer);
        when(tradeBookingDAO.tradeCapture(any(Stock.class), eq(true), any(Trade.class))).thenReturn(true);

        Assert.assertNotNull(tradeBookingService.tradeCapture(jsonNode));
    }

    @Test
    public void testPath2() throws CloneNotSupportedException {

        //[1,2,3,4,6,7,8,9,11,13,17]

        Customer customer = new Customer();
        Stock stock = new Stock();
        stock.setStockName("MSFT");
        stock.setTotalAvailableQuantity(200L);
        stock.setSoldQuantity(10L);
        customer.getStockList().add(stock);
        stock.setCustomer(customer);
        stock.setBoughtQuantity(100L);

        when(jsonNode.get(eq("customerId"))).thenReturn(jsonNode);
        when(jsonNode.asInt()).thenReturn(1);
        when(jsonNode.get(eq("stockName"))).thenReturn(jsonNode);
        when(jsonNode.textValue()).thenReturn("MSFT");
        when(jsonNode.get(eq("side"))).thenReturn(jsonNode2);
        when(jsonNode2.textValue()).thenReturn("SELL");
        when(jsonNode.get(eq("quantity"))).thenReturn(jsonNode);
        when(jsonNode.get(eq("price"))).thenReturn(jsonNode);
        when(jsonNode.asLong()).thenReturn(100L);
        when(jsonNode.asDouble()).thenReturn(10D);
        when(retrieveCustomerDAO.validateAndRetrieveCustomer(eq(1))).thenReturn(customer);
        when(tradeBookingDAO.tradeCapture(any(Stock.class), eq(false), any(Trade.class))).thenReturn(true);

        Assert.assertNotNull(tradeBookingService.tradeCapture(jsonNode));
    }

    @Test
    public void testPath3() throws CloneNotSupportedException {
        //[1,2,3,4,6,7,8,9,11,12,17]

        Customer customer = new Customer();
        Stock stock = new Stock();
        stock.setStockName("MSFT");
        stock.setTotalAvailableQuantity(200L);
        stock.setSoldQuantity(10L);
        customer.getStockList().add(stock);
        stock.setCustomer(customer);
        stock.setBoughtQuantity(100L);

        when(jsonNode.get(eq("customerId"))).thenReturn(jsonNode);
        when(jsonNode.asInt()).thenReturn(1);
        when(jsonNode.get(eq("stockName"))).thenReturn(jsonNode);
        when(jsonNode.textValue()).thenReturn("TSLA");
        when(jsonNode.get(eq("side"))).thenReturn(jsonNode2);
        when(jsonNode2.textValue()).thenReturn("SELL");
        when(jsonNode.get(eq("quantity"))).thenReturn(jsonNode);
        when(jsonNode.get(eq("price"))).thenReturn(jsonNode);
        when(jsonNode.asLong()).thenReturn(100L);
        when(jsonNode.asDouble()).thenReturn(10D);
        when(retrieveCustomerDAO.validateAndRetrieveCustomer(eq(1))).thenReturn(customer);
        when(tradeBookingDAO.tradeCapture(any(Stock.class), eq(false), any(Trade.class))).thenReturn(true);

        Assert.assertNull(tradeBookingService.tradeCapture(jsonNode));
    }

    @Test
    public void testPath4() throws CloneNotSupportedException {
        //[1,2,3,4,6,7,8,10,14,16,17]

        Customer customer = new Customer();
        Stock stock = new Stock();
        stock.setStockName("MSFT");
        stock.setTotalAvailableQuantity(200L);
        stock.setSoldQuantity(10L);
        customer.getStockList().add(stock);
        stock.setCustomer(customer);
        stock.setBoughtQuantity(100L);

        when(jsonNode.get(eq("customerId"))).thenReturn(jsonNode);
        when(jsonNode.asInt()).thenReturn(1);
        when(jsonNode.get(eq("stockName"))).thenReturn(jsonNode);
        when(jsonNode.textValue()).thenReturn("MSFT");
        when(jsonNode.get(eq("side"))).thenReturn(jsonNode2);
        when(jsonNode2.textValue()).thenReturn("BUY");
        when(jsonNode.get(eq("quantity"))).thenReturn(jsonNode);
        when(jsonNode.get(eq("price"))).thenReturn(jsonNode);
        when(jsonNode.asLong()).thenReturn(100L);
        when(jsonNode.asDouble()).thenReturn(10D);
        when(retrieveCustomerDAO.validateAndRetrieveCustomer(eq(1))).thenReturn(customer);
        when(tradeBookingDAO.tradeCapture(any(Stock.class), eq(false), any(Trade.class))).thenReturn(true);

        Assert.assertNotNull(tradeBookingService.tradeCapture(jsonNode));
    }

    @Test
    public void testPath5() throws CloneNotSupportedException {
        //[1,2,3,5,17]

        when(jsonNode.get(eq("customerId"))).thenReturn(jsonNode);
        when(jsonNode.asInt()).thenReturn(1);
        when(jsonNode.get(eq("stockName"))).thenReturn(jsonNode);
        when(jsonNode.textValue()).thenReturn("MSFT");
        when(jsonNode.get(eq("side"))).thenReturn(jsonNode2);
        when(jsonNode2.textValue()).thenReturn("BUY");
        when(jsonNode.get(eq("quantity"))).thenReturn(jsonNode);
        when(jsonNode.get(eq("price"))).thenReturn(jsonNode);
        when(jsonNode.asLong()).thenReturn(100L);
        when(jsonNode.asDouble()).thenReturn(10D);
        when(retrieveCustomerDAO.validateAndRetrieveCustomer(eq(1))).thenReturn(null);
        when(tradeBookingDAO.tradeCapture(any(Stock.class), eq(false), any(Trade.class))).thenReturn(true);

        Assert.assertNull(tradeBookingService.tradeCapture(jsonNode));
    }

    @Test
    public void testPath6() throws CloneNotSupportedException {
        //[1,2,3,4,6,7,6,7,8,10,14,16,17]

        Customer customer = new Customer();
        Stock stock = new Stock();
        stock.setStockName("MSFT");
        stock.setTotalAvailableQuantity(200L);
        stock.setSoldQuantity(10L);
        customer.getStockList().add(stock);
        stock.setCustomer(customer);
        stock.setBoughtQuantity(100L);

        Stock stock2 = new Stock();
        stock2.setStockName("TSLA");
        stock2.setTotalAvailableQuantity(300L);
        stock2.setSoldQuantity(10L);
        customer.getStockList().add(stock2);
        stock2.setCustomer(customer);
        stock2.setBoughtQuantity(150L);


        when(jsonNode.get(eq("customerId"))).thenReturn(jsonNode);
        when(jsonNode.asInt()).thenReturn(1);
        when(jsonNode.get(eq("stockName"))).thenReturn(jsonNode);
        when(jsonNode.textValue()).thenReturn("MSFT");
        when(jsonNode.get(eq("side"))).thenReturn(jsonNode2);
        when(jsonNode2.textValue()).thenReturn("BUY");
        when(jsonNode.get(eq("quantity"))).thenReturn(jsonNode);
        when(jsonNode.get(eq("price"))).thenReturn(jsonNode);
        when(jsonNode.asLong()).thenReturn(100L);
        when(jsonNode.asDouble()).thenReturn(10D);
        when(retrieveCustomerDAO.validateAndRetrieveCustomer(eq(1))).thenReturn(customer);
        when(tradeBookingDAO.tradeCapture(any(Stock.class), eq(false), any(Trade.class))).thenReturn(true);

        Assert.assertNotNull(tradeBookingService.tradeCapture(jsonNode));
    }


}