package com.dingyabin.work.common.model;

import com.dingyabin.work.common.cons.Const;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;

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
public class ConnectConfigManager {

    private static Set<ConnectConfig> connectMetas = Sets.newLinkedHashSet();


    private static File getConfigFile() {
        return new File(Const.CONNECT_CONFIG_FILE);
    }



    private static boolean saveConnectConfigs(Object object) {
        try{
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
            log.error("saveConnectConfigs error",e);
        }
        return false;
    }




    @SuppressWarnings("unchecked")
    public static boolean loadConnectConfigs() {
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
            e.printStackTrace();
        }
        return false;
    }


    public static boolean addConnectConfig(String name, String type, String host, String port, String userName, String pwd) {
        return addConnectConfig(new ConnectConfig(name, type, host, port, userName, pwd));
    }


    public static boolean addConnectConfig(ConnectConfig connectConfig) {
        if (connectMetas.add(connectConfig)) {
            return saveConnectConfigs(connectMetas);
        }
        return true;
    }


    public static boolean removeConnectConfig(ConnectConfig connectConfig) {
        connectMetas.remove(connectConfig);
        return saveConnectConfigs(connectMetas);
    }



    public static boolean updateConnectConfig(ConnectConfig oldConnectConfig, ConnectConfig newConnectConfig){
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




    public static Set<ConnectConfig> getConnectConfigs() {
        return Collections.unmodifiableSet(connectMetas);
    }



}
