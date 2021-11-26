package com.example.tradebooking.controller;

import com.example.estockcore.bean.Trade;
import com.example.tradebooking.service.impl.TradeBookingServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("trade")
public class TradeBookingController {

    @Autowired
    private TradeBookingServiceImpl service;

    /**
     * Expected and Accepted Format:
     * {
     * "customerId": 1,
     * "stockName": "Apple",
     * "price": 200.5,
     * "side": "BUY",
     * "quantity": 100
     * }
     *
     * @param request
     * @return
     */
    @PostMapping(path = "/book",
            produces = {"application/json"},
            consumes = {"application/json"})
    public ResponseEntity<Trade> book(@RequestBody JsonNode request) throws CloneNotSupportedException {
        //1
        System.out.println("Receiving booking request: " + request);
        Trade response = null;
        response = service.tradeCapture(request);
        //2
        if (response == null)   //3
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        //4
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
