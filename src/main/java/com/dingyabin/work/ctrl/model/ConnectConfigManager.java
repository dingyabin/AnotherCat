package com.dingyabin.work.ctrl.model;

import com.dingyabin.work.common.cons.Const;
import com.google.common.collect.Sets;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author 丁亚宾
 * Date: 2021/8/7.
 * Time:10:42
 */
public class ConnectConfigManager {

    private static Set<ConnectConfig> connectMetas = Sets.newLinkedHashSet();


    private static File getConfigFile() {
        String basePath = System.getProperty("user.dir");
        return new File(basePath + File.separator + Const.CONNECT_CONFIG_FILE_NAME);
    }



    private static boolean saveConnectConfigs(Object object){
        try {
            File configFile = getConfigFile();
            System.out.println(configFile.getAbsolutePath());
            if (!configFile.exists()) {
                configFile.createNewFile();
            }
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(configFile));
            outputStream.writeObject(object);
            outputStream.flush();
            outputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @SuppressWarnings("unchecked")
    public static boolean loadConnectConfigs() {
        try {
            //如果配置文件存在则读取出来
            File configFile = getConfigFile();
            if (!configFile.exists()) {
                return true;
            }
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(configFile));
            Object object = inputStream.readObject();
            if (object instanceof List) {
                connectMetas = (Set<ConnectConfig>) object;
            }
            inputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }



    public static boolean addConnectConfig(String type, String host, String port, String userName, String pwd) {
        connectMetas.add(new ConnectConfig(type, host, port, userName, pwd));
        return saveConnectConfigs(connectMetas);
    }



    public static Set<ConnectConfig> getConnectConfigs() {
        return Collections.unmodifiableSet(connectMetas);
    }



}
