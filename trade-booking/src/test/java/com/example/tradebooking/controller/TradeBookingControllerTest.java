package com.example.tradebooking.controller;

import com.example.estockcore.bean.Trade;
import com.example.tradebooking.service.impl.TradeBookingServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class TradeBookingControllerTest {

    @Autowired
    private MockMvc mvc;
    @Mock
    private TradeBookingServiceImpl tradeBookingService;
    @InjectMocks
    private TradeBookingController customerOperationController = new TradeBookingController();

    @Before
    public void initMocks() {
        mvc = MockMvcBuilders.standaloneSetup(customerOperationController).build();
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test booking controller functionality:
     * 2 Prime paths:
     * [1,2,3,5]
     * [1,2,4,5]
     * <p>
     * 2 Edge Coverage paths:
     * [1,2,3,5]
     * [1,2,4,5]
     */

    @Test
    public void testBookPath1() throws Exception {
        //[1,2,3,5]

        Trade trade = new Trade();
        trade.setTradeId(1);
        when(tradeBookingService.tradeCapture(any(JsonNode.class))).thenReturn(trade);

        final ResultActions result =
                mvc.perform(
                        post("/trade/book")
                                .content("{ \"customerId\": 1 }")
                                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.tradeId", is(1)));
    }

    @Test
    public void testBookPath2() throws Exception {
        //[1,2,4,5]

        when(tradeBookingService.tradeCapture(any(JsonNode.class))).thenReturn(null);

        final ResultActions result =
                mvc.perform(
                        post("/trade/book")
                                .content("{ \"customerId\": 1 }")
                                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isBadRequest());


    }
}