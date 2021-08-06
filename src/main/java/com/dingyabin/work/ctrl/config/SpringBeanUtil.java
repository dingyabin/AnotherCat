package com.dingyabin.work.ctrl.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author 丁亚宾
 * Date: 2021/8/7.
 * Time:1:00
 */
@Component
public class SpringBeanUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanUtil.applicationContext = applicationContext;
    }


    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }



}
