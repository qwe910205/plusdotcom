package com.qwe910205.plusdotcom.phone.controller;

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
class PhoneControllerTest {

    @Autowired PhoneController phoneController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(phoneController).build();
    }

    @Test
    @DisplayName("스마트폰 모델 리스트를 요청할 수 있는 api를 호출할 수 있다.")
    void getPhones() throws Exception {
        MockHttpServletRequestBuilder builder = get("/phones");

        mockMvc.perform(builder).andExpect(status().is2xxSuccessful()).andDo(print());
    }

}