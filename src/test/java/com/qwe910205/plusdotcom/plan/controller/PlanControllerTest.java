package com.qwe910205.plusdotcom.plan.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource(properties = {"spring.jpa.hibernate.ddl-auto=none", "spring.datasource.url=jdbc:mysql://localhost:3306/uplusdotcom"})
@SpringBootTest
class PlanControllerTest {

    @Autowired PlanController planController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(planController).build();
    }

    @Test
    @DisplayName("요금제 리스트를 요청할 수 있는 API를 호출할 수 있다.")
    void getPlans() throws Exception {
        MockHttpServletRequestBuilder builder = get("/plans");

        mockMvc.perform(builder)
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    @DisplayName("요금제를 단건으로 요청할 수 있는 API를 호출할 수 있다.")
    void getPlan() throws Exception {
        String planId = "Z202205252";
        MockHttpServletRequestBuilder builder = get("/plans/" + planId);

        mockMvc.perform(builder)
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

}