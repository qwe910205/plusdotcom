package com.qwe910205.plusdotcom.datainit.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource(properties = {"spring.jpa.hibernate.ddl-auto=create-drop", "spring.datasource.url=jdbc:mysql://localhost:3306/plusdotcomdatainittest"})
@SpringBootTest
class InitServiceTest {

    @Autowired InitService initService;

    @Test
    @DisplayName("모든 데이터를 초기화할 수 있다.")
    void initAllData() {
        assertThatCode(() -> initService.init()).doesNotThrowAnyException();
    }

}