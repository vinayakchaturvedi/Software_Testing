package com.example.estockcore.bean;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * This will contain the details of a trade.
 * Each and every trade will going to create an entry in database.
 */

@Entity
public class Trade implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tradeId;
    @Column(nullable = false)
    private LocalDateTime tradeDate;
    @Column(nullable = false)
    private LocalDateTime settlementDate;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Side Side;
    @Column(nullable = false)
    private Long quantity;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false)
    private double commission;
    @Column(nullable = false)
    private double netAmount;
    @Column(columnDefinition = "boolean default false")
    private boolean isSettled;


    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    private Customer customer;

    public Trade() {
    }

    public Trade(LocalDateTime tradeDate,
                 LocalDateTime settlementDate,
                 com.example.estockcore.bean.Side side,
                 Long quantity,
                 double price,
                 double commission,
                 double netAmount,
                 boolean isSettled,
                 Customer customer) {
        this.tradeDate = tradeDate;
        this.settlementDate = settlementDate;
        Side = side;
        this.quantity = quantity;
        this.price = Math.round(price * 100.0) / 100.0;
        this.commission = commission;
        this.netAmount = Math.round(netAmount * 100.0) / 100.0;
        this.isSettled = isSettled;
        this.customer = customer;
    }

    public Integer getTradeId() {
        return tradeId;
    }

    public void setTradeId(Integer tradeId) {
        this.tradeId = tradeId;
    }

    public LocalDateTime getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(LocalDateTime tradeDate) {
        this.tradeDate = tradeDate;
    }

    public LocalDateTime getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(LocalDateTime settlementDate) {
        this.settlementDate = settlementDate;
    }

    public com.example.estockcore.bean.Side getSide() {
        return Side;
    }

    public void setSide(com.example.estockcore.bean.Side side) {
        Side = side;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = Math.round(price * 100.0) / 100.0;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(double netAmount) {
        this.netAmount = Math.round(netAmount * 100.0) / 100.0;
    }

    public boolean isSettled() {
        return isSettled;
    }

    public void setSettled(boolean settled) {
        isSettled = settled;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "tradeId=" + tradeId +
                ", tradeDate=" + tradeDate +
                ", settlementDate=" + settlementDate +
                ", Side=" + Side +
                ", quantity=" + quantity +
                ", price=" + price +
                ", commission=" + commission +
                ", netAmount=" + netAmount +
                ", isSettled=" + isSettled +
                ", customer=" + customer.getCustomerId() + "-" + customer.getCustomerName() +
                '}';
    }

    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Trade shallowCopy() throws CloneNotSupportedException {
        Trade clonedTrade = (Trade) this.clone();
        clonedTrade.setCustomer(null);

        return clonedTrade;
    }
}
