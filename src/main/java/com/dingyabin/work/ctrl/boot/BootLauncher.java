package com.dingyabin.work.ctrl.boot;

import com.dingyabin.work.common.model.ConnectConfigManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author 丁亚宾
 * Date: 2021/8/7.
 * Time:2:06
 */
@Component
public class BootLauncher implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {
        //加载配置文件
        ConnectConfigManager.loadConnectConfigs();
        //启动窗口
        AnotherCatLauncher.getInstance().assembleComponent();
    }



}
