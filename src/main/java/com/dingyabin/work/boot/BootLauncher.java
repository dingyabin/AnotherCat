package com.dingyabin.work.boot;

import com.dingyabin.work.ctrl.model.ConnectConfigManager;
import com.dingyabin.work.gui.AnotherCatSwingLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 丁亚宾
 * Date: 2021/8/7.
 * Time:2:06
 */
@Component
public class BootLauncher implements CommandLineRunner {


    @Resource
    private AnotherCatSwingLauncher anotherCatSwingLauncher;



    @Override
    public void run(String... args) throws Exception {
        //加载配置文件
        ConnectConfigManager.loadConnectConfigs();
        anotherCatSwingLauncher.init();
    }


}
