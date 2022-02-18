package com.dingyabin.work.ctrl.boot;

import com.dingyabin.work.common.model.ConnectConfigManager;
import com.dingyabin.work.gui.component.ComContextManager;
import com.dingyabin.work.gui.utils.GuiUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.swing.*;

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
        if (!ConnectConfigManager.getInstance().loadConnectConfigs()) {
            GuiUtils.createOptionPane(ComContextManager.getMainFrame(), "加载配置文件失败....", JOptionPane.DEFAULT_OPTION);
        }
        //启动窗口
        AnotherCatLauncher.getInstance().assembleComponent();
    }



}
