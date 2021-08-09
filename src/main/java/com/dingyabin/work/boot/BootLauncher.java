package com.dingyabin.work.boot;

import com.alee.laf.WebLookAndFeel;
import com.dingyabin.work.ctrl.model.ConnectConfigManager;
import com.dingyabin.work.gui.AnotherCatSwingLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

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
        //加载主题
        WebLookAndFeel.install();
        //启动窗口
        anotherCatSwingLauncher.assembleComponent();
    }





    private void initGlobalFont(Font font) {
        FontUIResource fontRes = new FontUIResource(font);
        for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontRes);
            }
        }
    }


}
