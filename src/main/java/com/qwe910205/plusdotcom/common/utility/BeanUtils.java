package com.qwe910205.plusdotcom.common.utility;

import com.qwe910205.plusdotcom.common.ApplicationContextProvider;
import org.springframework.context.ApplicationContext;

import java.util.Collection;

public class BeanUtils {

    public static <T> Collection<T> getBeansOfType(Class<T> clazz) {
        ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
        return applicationContext.getBeansOfType(clazz).values();
    }
}
