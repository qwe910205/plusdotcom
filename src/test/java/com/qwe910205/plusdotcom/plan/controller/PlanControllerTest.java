package com.qwe910205.plusdotcom.plan.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class PlanControllerTest {

    @Autowired
    PlanController planController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(planController).build();
    }

    @Nested
    @DisplayName("getPlans 메소드는")
    class Describe_getPlans {
        @Nested
        @DisplayName("쿼리 파라미터가 없다면")
        class Context_without_query_params {
            @Test
            @DisplayName("잘 호출 된다.")
            void it_returns_statusCode_OK() throws Exception {
                MockHttpServletRequestBuilder builder = get("/plans");

                mockMvc.perform(builder)
                        .andExpect(status().is2xxSuccessful())
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("쿼리 파라미터로 비용과 한 달간 최소 데이터 사용량, 속도 제한을 신경 쓰는지 여부가 주어지면")
        class Context_with_query_params {
            @Test
            @DisplayName("잘 호출 된다.")
            void it_returns_statusCode_OK() throws Exception {
                MockHttpServletRequestBuilder builder = get("/plans")
                        .param("cost", "50000")
                        .param("minimumMonthlyDataUsage", "5000")
                        .param("careAboutSpeedLimit", "true");

                mockMvc.perform(builder)
                        .andExpect(status().is2xxSuccessful())
                        .andDo(print());
            }
        }

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

    @Test
    @DisplayName("특정 요금제의 한 달간 데이터 데이터 사용량에 대한 부과 비용을 요청할 수 있는 API를 호출할 수 있다.")
    void getChargeAboutMonthlyDataUsage() throws Exception {
        String planId = "LPZ0001204";
        String dataUsage = "1000";
        MockHttpServletRequestBuilder builder = get("/plans/" + planId + "/charge").param("monthlyDataUsage", dataUsage);

        mockMvc.perform(builder)
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

}