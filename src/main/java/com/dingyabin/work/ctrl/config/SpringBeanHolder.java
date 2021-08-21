package com.dingyabin.work.ctrl.config;

import com.dingyabin.work.ctrl.adapter.CatAdapterService;
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
public class SpringBeanHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    private static CatAdapterService catAdapterService;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanHolder.applicationContext = applicationContext;
    }

    public static void registryCatAdapter(CatAdapterService catAdapterService){
        SpringBeanHolder.catAdapterService = catAdapterService;
    }


    public static CatAdapterService getCatAdapter(){
        return SpringBeanHolder.catAdapterService;
    }


    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }



}
