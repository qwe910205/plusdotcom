package com.qwe910205.plusdotcom.payment.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class PaymentControllerTest {

    @Autowired PaymentController paymentController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();
    }

    @Test
    @DisplayName("스마트폰 모델 아이디, 요금제 아이디, 할부기간, 할인 방식에 맞는 지불 금액 명세서를 요청할 수 있는 API를 호출할 수 있다.")
    void getPaymentSpecification() throws Exception {
        String modelId = "SM-F721N512";
        String planId = "LPZ0000433";
        String installmentPeriod = "24";
        String discountType = "공시지원금";

        MockHttpServletRequestBuilder builder = get("/payments")
                .param("modelId", modelId)
                .param("planId", planId)
                .param("installmentPeriod", installmentPeriod)
                .param("discountType", discountType);

        mockMvc.perform(builder)
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    @DisplayName("요금제 아이디, 할부기간, 할인 방식에 맞는 모든 지불 금액 명세서를 요청할 수 있는 API를 호출할 수 있다.")
    void getPaymentSpecifications() throws Exception {
        String planId = "LPZ0000433";
        String installmentPeriod = "24";
        String discountType = "공시지원금";

        MockHttpServletRequestBuilder builder = get("/payments")
                .param("planId", planId)
                .param("installmentPeriod", installmentPeriod)
                .param("discountType", discountType);

        mockMvc.perform(builder)
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }
}