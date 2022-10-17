package com.qwe910205.plusdotcom.datainit.service;

import com.qwe910205.plusdotcom.datainit.service.initializer.DataInitializer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class InitService {

    private final List<DataInitializer> initializers;
    @PersistenceContext
    EntityManager em;

    @Transactional
    public void init() {
        Collections.sort(initializers);
        for (DataInitializer initializer : initializers) {
            initializer.init();
            em.flush();
            em.clear();
        }
    }
}
