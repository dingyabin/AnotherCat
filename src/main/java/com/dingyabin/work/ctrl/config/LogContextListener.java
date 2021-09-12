package com.dingyabin.work.ctrl.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.LoggerContextListener;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.spi.LifeCycle;
import ch.qos.logback.core.Context;
import com.dingyabin.work.common.cons.Const;

/**
 * @author 丁亚宾
 * Date: 2021/8/10.
 * Time:23:29
 */
public class LogContextListener extends ContextAwareBase implements LoggerContextListener, LifeCycle {


    private boolean started = false;


    private static  final String CAT_LOG_PATH_KEY = "another_cat.log.path";


    @Override
    public boolean isResetResistant() {
        return false;
    }

    @Override
    public void onStart(LoggerContext context) {

    }

    @Override
    public void onReset(LoggerContext context) {

    }

    @Override
    public void onStop(LoggerContext context) {

    }

    @Override
    public void onLevelChange(Logger logger, Level level) {

    }

    @Override
    public void start() {
        if (started) {
            return;
        }
        System.setProperty(CAT_LOG_PATH_KEY, Const.CAT_LOG_PATH);
        Context context = getContext();
        context.putProperty(CAT_LOG_PATH_KEY, Const.CAT_LOG_PATH);
        started = true;
    }


    @Override
    public void stop() {

    }

    @Override
    public boolean isStarted() {
        return started;
    }
}
