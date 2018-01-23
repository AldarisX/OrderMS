package com.everygamer.orm.dao;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class DaoPostProcessor implements BeanPostProcessor {
    //初始化前的处理
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        return bean;
    }

    //初始化后
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        try {
            BaseDao dao = (BaseDao) bean;
            dao.init();
        } catch (ClassCastException e) {
        }
        return bean;
    }
}
