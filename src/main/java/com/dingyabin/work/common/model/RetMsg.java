package com.dingyabin.work.common.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 丁亚宾
 * Date: 2021/9/5.
 * Time:20:01
 */
@Getter
@Setter
public class RetMsg<T> {

    private boolean success = true;

    private String msg;

    private T data;

    public RetMsg() {
    }


    public RetMsg(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public RetMsg(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public static <T> RetMsg<T> success() {
        return new RetMsg<>();
    }

    public static <T> RetMsg<T> success(T data) {
        return new RetMsg<>(true, data);
    }

    public static <T> RetMsg<T> fail(String msg) {
        return new RetMsg<>(false, msg);
    }

    public boolean isFail(){
        return !isSuccess();
    }

}
