package com.example.tradebooking.service.impl;

import com.example.estockcore.bean.Customer;
import com.example.estockcore.bean.Side;
import com.example.estockcore.bean.Stock;
import com.example.estockcore.bean.Trade;
import com.example.tradebooking.dao.impl.RetrieveCustomerDAOImpl;
import com.example.tradebooking.dao.impl.TradeBookingDAOImpl;
import com.example.tradebooking.service.Service;
import com.example.tradebooking.utils.ConstantsAndMessages;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class TradeBookingServiceImpl implements Service {

    @Autowired
    private RetrieveCustomerDAOImpl retrieveCustomerDAO;
    @Autowired
    private TradeBookingDAOImpl tradeBookingDAO;

    @Value("${trade.commission}")
    private int commission;

    @Override
    public Trade tradeCapture(JsonNode request) throws CloneNotSupportedException {
        //2
        Integer customerId = request.get("customerId").asInt();
        String stockName = request.get("stockName").textValue();
        double price = request.get("price").asDouble();
        Side side = request.get("side").textValue().equals("BUY") ? Side.BUY : Side.SELL;
        Long quantity = request.get("quantity").asLong();
        double netAmount = quantity * price + commission;
        boolean isSettled = false;

        Customer customer = retrieveCustomerDAO.validateAndRetrieveCustomer(customerId);

        //3
        if (customer == null) {
            //5
            System.out.println("No customer found for the given customer id: " + customerId);
            return null;
        }

        //4
        Trade trade = new Trade(LocalDateTime.now(),
                LocalDateTime.now().plusDays(2),
                side,
                quantity,
                price,
                commission,
                netAmount,
                isSettled,
                customer);

        Map<String, Stock> availableStocks = new HashMap<>();

        //6
        for (Stock stock : customer.getStockList()) {
            //7
            availableStocks.put(stock.getStockName(), stock);
        }

        //8
        if (Side.SELL.equals(side)) {    //9
            return handleSell(availableStocks, stockName, quantity, customerId, netAmount, trade);
        } else {                        //10
            return handleBuy(availableStocks, stockName, customer, quantity, netAmount, trade);
        }
    }

    //Sell Stock Logic
    public Trade handleSell(Map<String, Stock> availableStocks, String stockName, Long quantity, Integer customerId, double netAmount, Trade trade) throws CloneNotSupportedException {
        Stock currTradedStock = availableStocks.get(stockName);
        //11
        if (currTradedStock == null || currTradedStock.getTotalAvailableQuantity() < quantity) {
            //12
            System.out.println(customerId + ConstantsAndMessages.NOT_ENOUGH_STOCKS);
            return null;
        }
        //13
        currTradedStock.setTotalAvailableQuantity(
                currTradedStock.getTotalAvailableQuantity() - quantity);
        currTradedStock.setSoldQuantity(
                currTradedStock.getSoldQuantity() + quantity);
        currTradedStock.setAmountEarned(currTradedStock.getAmountEarned() + netAmount);

        return (tradeBookingDAO.tradeCapture(currTradedStock, false, trade)) ? trade.shallowCopy() : null;
    }

    //Buying Stock Logic
    public Trade handleBuy(Map<String, Stock> availableStocks, String stockName, Customer customer, Long quantity,
                           double netAmount, Trade trade) throws CloneNotSupportedException {
        Stock currTradedStock = availableStocks.get(stockName);
        boolean isFirstStock = false;
        //Create a new Stock
        //14
        if (currTradedStock == null) {     //15
            isFirstStock = true;
            currTradedStock = new Stock(stockName, customer);
        }
        //16
        currTradedStock.setTotalAvailableQuantity(
                currTradedStock.getTotalAvailableQuantity() + quantity);
        currTradedStock.setBoughtQuantity(
                currTradedStock.getBoughtQuantity() + quantity);
        currTradedStock.setAmountSpent(currTradedStock.getAmountSpent() + netAmount);
        return (tradeBookingDAO.tradeCapture(currTradedStock, isFirstStock, trade)) ? trade.shallowCopy() : null;
    }


}
