package com.dingyabin.work;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


/**
 * @author 丁亚宾
 */
@SpringBootApplication
public class AnotherCatApplication {


    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(AnotherCatApplication.class);
        builder.headless(false).web(WebApplicationType.NONE).run(args);
    }

}
