package com.dingyabin.work.gui;

import com.dingyabin.work.adapter.Adapter;
import com.dingyabin.work.ctrl.model.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * @author 丁亚宾
 * Date: 2021/8/1.
 * Time:23:47
 */

@Component
public class AnotherCatSwingLauncher {


    @Resource
    private Adapter adapter;


    private final JFrame jf = new JFrame("AnotherCat");


    private JList<ConnectConfig> connectSchema = new JList<>();

    private JList<DataBaseSchema> dataBaseSchema = new JList<>();

    private JList<TableSchema> tableSchema = new JList<>();


    public void init() {

        jf.setPreferredSize(new Dimension(1600,1000));


        Vector<ConnectConfig> connectConfigs = new Vector<>(ConnectConfigManager.getConnectConfigs());
        connectSchema.setListData(connectConfigs);


        connectSchema.addListSelectionListener(e -> {
            ConnectConfig connectConfig = connectSchema.getSelectedValue();
            //如果之前注册过了，内部不会重复注册的
            adapter.addDataSource(connectConfig);

            dataBaseSchema.setListData(new Vector<>(adapter.getDbsOnConnectChange(connectConfig)));
        });


        dataBaseSchema.addListSelectionListener(e -> {
            ConnectConfig connectConfig = connectSchema.getSelectedValue();
            DataBaseSchema dataBaseSchema = this.dataBaseSchema.getSelectedValue();

            //如果之前注册过了，内部不会重复注册的
            adapter.addDataSource(connectConfig.typeEnum(),connectConfig.getHost(),connectConfig.getPort(),connectConfig.getUserName(),connectConfig.getPwd(),dataBaseSchema.getSchemaName());

            tableSchema.setListData(new Vector<>(adapter.getTablesOnDbSchemaChange(connectConfig,dataBaseSchema)));
        });


        JSplitPane dbAndTable = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(dataBaseSchema), new JScrollPane(tableSchema));
        dbAndTable.setOneTouchExpandable(true);
        dbAndTable.setContinuousLayout(true);



        JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(connectSchema), dbAndTable);

        jSplitPane.setOneTouchExpandable(true);
        jSplitPane.setContinuousLayout(true);



        jf.add(jSplitPane);

        jf.pack();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);



    }



}
