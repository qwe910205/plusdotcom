package com.qwe910205.plusdotcom.datainit.controller;

import com.qwe910205.plusdotcom.phonemodel.domain.ConvenienceFunction;
import com.qwe910205.plusdotcom.phonemodel.domain.Manufacturer;
import com.qwe910205.plusdotcom.phonemodel.domain.NetworkTech;
import com.qwe910205.plusdotcom.phonemodel.domain.PhoneModel;
import com.qwe910205.plusdotcom.phonemodel.domain.vo.PhoneDescription;
import com.qwe910205.plusdotcom.phonemodel.repository.ConvenienceFunctionRepository;
import com.qwe910205.plusdotcom.phonemodel.repository.ManufacturerRepository;
import com.qwe910205.plusdotcom.phonemodel.repository.NetworkTechRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InitControllerTest {



}