package com.example.estockcore.bean;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;              //Primary Key
    @Column(nullable = false)
    private String customerName;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String emailId;
    @Column(nullable = false)
    private String contactNumber;
    @Column(nullable = false, unique = true)
    private Long tradingAccount;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Trade> tradeList;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Stock> stockList;

    public Customer() {
        this.tradeList = new ArrayList<>();
        this.stockList = new ArrayList<>();
    }

    public Customer(Integer customerId,
                    String customerName,
                    String password,
                    String emailId,
                    String contactNumber,
                    Long tradingAccount,
                    List<Trade> tradeList,
                    List<Stock> stockList) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.password = password;
        this.emailId = emailId;
        this.contactNumber = contactNumber;
        this.tradingAccount = tradingAccount;
        this.tradeList = new ArrayList<>();
        this.stockList = new ArrayList<>();
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Long getTradingAccount() {
        return tradingAccount;
    }

    public void setTradingAccount(Long tradingAccount) {
        this.tradingAccount = tradingAccount;
    }

    public List<Trade> getTradeList() {
        return tradeList;
    }

    public void setTradeList(List<Trade> tradeList) {
        this.tradeList = tradeList;
    }

    public List<Stock> getStockList() {
        return stockList;
    }

    public void setStockList(List<Stock> stockList) {
        this.stockList = stockList;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", password='" + password + '\'' +
                ", emailId='" + emailId + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", tradingAccount=" + tradingAccount +
                ", number of trades done=" + tradeList.size() +
                ", number of stock owned=" + stockList.size() +
                '}';
    }

    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Customer shallowCopy() throws CloneNotSupportedException {
        Customer clonedCustomer = (Customer) this.clone();
        clonedCustomer.setTradeList(new ArrayList<>());
        for (Trade trade : this.getTradeList()) {
            clonedCustomer.getTradeList().add(trade.shallowCopy());
        }

        clonedCustomer.setStockList(new ArrayList<>());
        for (Stock stock : this.getStockList()) {
            clonedCustomer.getStockList().add(stock.shallowCopy());
        }

        return clonedCustomer;
    }
}
