package com.qwe910205.plusdotcom.datainit.service.initializer;

import javax.transaction.Transactional;

public interface DataInitializer extends Comparable<DataInitializer> {

    void init();

    int getPriority();
}
