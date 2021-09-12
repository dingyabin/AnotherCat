package com.dingyabin.work.common.listeners;

/**
 * @author 丁亚宾
 * Date: 2021/9/12.
 * Time:21:42
 */
public interface CatSystemListener<T> {


    void process(T object);


    Class<T> getListenType();

}
