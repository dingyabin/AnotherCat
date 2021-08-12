package com.dingyabin.work;

import com.alee.laf.WebLookAndFeel;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;


/**
 * @author 丁亚宾
 */
@SpringBootApplication(
        scanBasePackages = "com.dingyabin.work.ctrl",
        exclude = {
                JmxAutoConfiguration.class,
                DataSourceAutoConfiguration.class,
                TaskSchedulingAutoConfiguration.class,
                TaskExecutionAutoConfiguration.class,
                JdbcTemplateAutoConfiguration.class
        })
public class ServerStart {


    static {
        //需要先加载主题
        WebLookAndFeel.install();
    }


    public static void main(String[] args) {

        //启动Spring容器
        new SpringApplicationBuilder(ServerStart.class).headless(false).web(WebApplicationType.NONE).run(args);
    }

}
