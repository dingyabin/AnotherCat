package com.dingyabin.work.swingtest;

import com.alee.laf.WebLookAndFeel;
import com.dingyabin.work.ctrl.model.ConnectConfigManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author 丁亚宾
 * Date: 2021/8/9.
 * Time:22:45
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SwingTest {


    @Resource
    private  AnotherCatSwingTest anotherCatSwingTest;



    @Test
    public void swingTest(){
        //加载配置文件
        ConnectConfigManager.loadConnectConfigs();
        //加载主题
        WebLookAndFeel.install();
        anotherCatSwingTest.init();
        while (true) {

        }
    }



}
