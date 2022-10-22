package com.qwe910205.plusdotcom.datainit.service.initializer;

public interface DataInitializer extends Comparable<DataInitializer> {

    void init();

    int getPriority();
}
