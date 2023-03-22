package com.appteam.template.controller;

import com.appteam.template.services.DHLService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import static org.mockito.Mockito.when;

import static org.hamcrest.Matchers.containsString;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class SampleControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DHLService DHLservice;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Greetings from Application!")));
    }

    @Test
    public void DHLIntegrationTest() throws Exception {
        when(DHLservice.getShipmentInfo(Mockito.anyString())).thenReturn("ShipmentInfo");
        this.mockMvc.perform(get("/my-shipment-status").param("id", "ShipmentId"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("ShipmentInfo")));
    }
}