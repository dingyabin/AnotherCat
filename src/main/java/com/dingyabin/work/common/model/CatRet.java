package com.dingyabin.work.common.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author 丁亚宾
 * Date: 2021/8/15.
 * Time:15:25
 */
@Getter
@Setter
@Accessors(chain = true)
public class CatRet<T> {

    private boolean success = true;

    private T data;

    private String msg;


    public CatRet() {
    }

    public CatRet(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public CatRet(boolean success, T data, String msg) {
        this.success = success;
        this.data = data;
        this.msg = msg;
    }


    public CatRet(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }


    public static <T> CatRet<T> success(T data){
        return new CatRet<>(true, data);
    }


    public static <T> CatRet<T> fail(String msg){
        return new CatRet<T>(false, msg);
    }

}
