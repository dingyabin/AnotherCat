package com.dingyabin.work.common.model;

import com.dingyabin.work.common.cons.Const;
import com.dingyabin.work.common.listeners.CatActionListener;
import com.dingyabin.work.ctrl.event.SystemEventDispatcher;
import com.dingyabin.work.gui.utils.GuiUtils;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.io.*;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

/**
 * @author 丁亚宾
 * Date: 2021/8/7.
 * Time:10:42
 */
@Slf4j
public class ConnectConfigManager implements CatActionListener<SaveConnectEvent> {

    private Set<ConnectConfig> connectMetas = Sets.newLinkedHashSet();


    private static final ConnectConfigManager INSTANCE = new ConnectConfigManager();


    private ConnectConfigManager() {
        SystemEventDispatcher.register(this);
    }


    public static ConnectConfigManager getInstance() {
        return INSTANCE;
    }



    private File getConfigFile() {
        return new File(Const.CONNECT_CONFIG_FILE);
    }


    private boolean saveConnectConfigs(Object object) {
        try {
            File configFile = getConfigFile();
            File parentFile = configFile.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            if (!configFile.exists()) {
                configFile.createNewFile();
            }
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(configFile));
            outputStream.writeObject(object);
            outputStream.close();
            return true;
        } catch (Exception e) {
            log.error("saveConnectConfigs error", e);
        }
        return false;
    }


    @SuppressWarnings("unchecked")
    public boolean loadConnectConfigs() {
        //如果配置文件存在则读取出来
        File configFile = getConfigFile();
        if (!configFile.exists()) {
            return true;
        }
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(configFile))) {
            Object object = inputStream.readObject();
            if (object instanceof Set) {
                connectMetas = (Set<ConnectConfig>) object;
            }
            return true;
        } catch (Exception e) {
            log.error("loadConnectConfigs error", e);
        }
        return false;
    }


    public boolean addConnectConfig(ConnectConfig connectConfig) {
        if (connectMetas.add(connectConfig)) {
            return saveConnectConfigs(connectMetas);
        }
        return true;
    }


    public boolean removeConnectConfig(ConnectConfig connectConfig) {
        connectMetas.remove(connectConfig);
        return saveConnectConfigs(connectMetas);
    }


    public boolean updateConnectConfig(ConnectConfig oldConnectConfig, ConnectConfig newConnectConfig) {
        Optional<ConnectConfig> first = connectMetas.stream().filter(oldConnectConfig::equals).findFirst();
        if (first.isPresent()) {
            ConnectConfig connectConfig = first.get();
            connectConfig.setName(newConnectConfig.getName());
            connectConfig.setType(newConnectConfig.getType());
            connectConfig.setHost(newConnectConfig.getHost());
            connectConfig.setPort(newConnectConfig.getPort());
            connectConfig.setUserName(newConnectConfig.getUserName());
            connectConfig.setPwd(newConnectConfig.getPwd());
            return saveConnectConfigs(connectMetas);
        }
        return true;
    }


    public Set<ConnectConfig> getConnectConfigs() {
        return Collections.unmodifiableSet(connectMetas);
    }



    @Override
    public void process(SaveConnectEvent saveConnectEvent) {
        boolean saveRet = true;
        try {
            CatNewConModel catNewConModel = saveConnectEvent.getCatNewConModel();
            ConnectConfig savedConfig = saveConnectEvent.getSavedConnectConfig();
            //新增模式
            if (catNewConModel.isSaveMode()) {
                saveRet = addConnectConfig(savedConfig);
                return;
            }
            //修改模式
            ConnectConfig oldConFig = catNewConModel.getOldConnectConfig();
            //如果相等，不做操作, 否则修改
            if (!oldConFig.equals(savedConfig)) {
                saveRet = updateConnectConfig(oldConFig, savedConfig);
            }
        } catch (Exception e) {
            log.error("process error", e);
        } finally {
            //弹框提示
            GuiUtils.createOptionPane(saveConnectEvent.getSource(), saveRet ? "保存成功！" : "TMD，失败了！", JOptionPane.DEFAULT_OPTION);
        }
    }



    @Override
    public Class<SaveConnectEvent> getListenType() {
        return SaveConnectEvent.class;
    }
}
