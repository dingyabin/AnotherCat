package com.dingyabin.work;

import com.alee.laf.WebLookAndFeel;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


/**
 * @author 丁亚宾
 */
@SpringBootApplication
public class AnotherCatApplication {


    public static void main(String[] args) {

        //需要先加载主题
        WebLookAndFeel.install();

        //再启动Spring容器
        new SpringApplicationBuilder(AnotherCatApplication.class).headless(false).web(WebApplicationType.NONE).run(args);
    }

}
