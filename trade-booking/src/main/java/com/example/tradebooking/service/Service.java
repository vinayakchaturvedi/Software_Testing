package com.example.tradebooking.service;

import com.example.estockcore.bean.Trade;
import com.fasterxml.jackson.databind.JsonNode;

public interface Service {

    Trade tradeCapture(JsonNode request) throws CloneNotSupportedException;
}
