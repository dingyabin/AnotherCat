package com.dingyabin.work.common.model;

/**
 * @author 丁亚宾
 * Date: 2021/8/17.
 * Time:0:23
 */
public class CatNewConModel {
    /**
     * 新建-保存模式
     */
    public static final int SAVE_MODE = 0;

    /**
     * 编辑模式
     */
    public static final int EDIT_MODE = 1;


    private int mode;


    private ConnectConfig oldConnectConfig;



    private CatNewConModel(int mode) {
        this.mode = mode;
    }

    private CatNewConModel(int mode, ConnectConfig oldConnectConfig) {
        this.mode = mode;
        this.oldConnectConfig = oldConnectConfig;
    }


    /**
     * 新建模式
     * @return 新建模式的model
     */
    public static CatNewConModel saveMode() {
        return new CatNewConModel(SAVE_MODE);
    }


    /**
     * 编辑模式
     * @param oldConnectConfig 旧的配置信息
     * @return 编辑模式的model
     */
    public static CatNewConModel editMode(ConnectConfig oldConnectConfig) {
        return new CatNewConModel(EDIT_MODE, oldConnectConfig);
    }


    public boolean isSaveMode(){
        return mode == SAVE_MODE;
    }


    public boolean isEditMode(){
        return mode == EDIT_MODE;
    }

    public ConnectConfig getOldConnectConfig() {
        return oldConnectConfig;
    }
}
