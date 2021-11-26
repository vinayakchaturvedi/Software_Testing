package com.example.estockcore.bean;

import javax.persistence.*;

/**
 * 1 Customer can own multiple stocks. And 1 stockEntry for each type of stock.
 * Eg: Customer 'X' can own Stock 'Apple', 'Microsoft', 'Google', 'Tesla', etc.
 * On every trade we'll update the corresponding stockEntry for respective customer.
 */
@Entity
public class Stock implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer stockId;              //Primary Key
    @Column(nullable = false)
    private String stockName;
    @Column(nullable = false)
    private Long totalAvailableQuantity;
    @Column(nullable = false)
    private Long soldQuantity;
    @Column(nullable = false)
    private Long boughtQuantity;
    @Column(nullable = false)
    private double amountSpent;
    @Column(nullable = false)
    private double amountEarned;
    @Column(nullable = false)
    private double profit;
    @Column(nullable = false)
    private double loss;

    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    private Customer customer;

    public Stock() {
    }

    public Stock(String stockName, Customer customer) {
        this.stockName = stockName;
        this.customer = customer;
        totalAvailableQuantity = 0L;
        soldQuantity = 0L;
        boughtQuantity = 0L;
        amountEarned = 0;
        amountSpent = 0;
        profit = 0;
        loss = 0;
    }

    public Stock(String stockName,
                 Long totalAvailableQuantity,
                 Long soldQuantity,
                 Long boughtQuantity,
                 double amountSpent,
                 double amountEarned,
                 double profit,
                 double loss,
                 Customer customer) {
        this.stockName = stockName;
        this.totalAvailableQuantity = totalAvailableQuantity;
        this.soldQuantity = soldQuantity;
        this.boughtQuantity = boughtQuantity;
        this.amountSpent = amountSpent;
        this.amountEarned = amountEarned;
        this.profit = profit;
        this.loss = loss;
        this.customer = customer;
    }

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public Long getTotalAvailableQuantity() {
        return totalAvailableQuantity;
    }

    public void setTotalAvailableQuantity(Long totalAvailableQuantity) {
        this.totalAvailableQuantity = totalAvailableQuantity;
    }

    public Long getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(Long soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public Long getBoughtQuantity() {
        return boughtQuantity;
    }

    public void setBoughtQuantity(Long boughtQuantity) {
        this.boughtQuantity = boughtQuantity;
    }

    public double getAmountSpent() {
        return amountSpent;
    }

    public void setAmountSpent(double amountSpent) {
        this.amountSpent = amountSpent;
    }

    public double getAmountEarned() {
        return amountEarned;
    }

    public void setAmountEarned(double amountEarned) {
        this.amountEarned = amountEarned;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double getLoss() {
        return loss;
    }

    public void setLoss(double loss) {
        this.loss = loss;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "stockId=" + stockId +
                ", stockName='" + stockName + '\'' +
                ", totalAvailableQuantity=" + totalAvailableQuantity +
                ", soldQuantity=" + soldQuantity +
                ", boughtQuantity=" + boughtQuantity +
                ", amountSpent=" + amountSpent +
                ", amountEarned=" + amountEarned +
                ", profit=" + profit +
                ", loss=" + loss +
                ", customer=" + customer.getCustomerId() + "-" + customer.getCustomerName() +
                '}';
    }

    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Stock shallowCopy() throws CloneNotSupportedException {
        Stock clonedStock = (Stock) this.clone();
        clonedStock.setCustomer(null);

        return clonedStock;
    }

}
