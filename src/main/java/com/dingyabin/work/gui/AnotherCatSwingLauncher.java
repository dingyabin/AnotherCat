package com.dingyabin.work.gui;

import com.dingyabin.work.adapter.Adapter;
import com.dingyabin.work.ctrl.model.ConnectConfig;
import com.dingyabin.work.ctrl.model.ConnectConfigManager;
import com.dingyabin.work.ctrl.model.DataBaseSchema;
import com.dingyabin.work.ctrl.model.TableSchema;
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

    private JList<String> tableContentSchema = new JList<>();


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


        JSplitPane tableSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(tableSchema), new JScrollPane(tableContentSchema));
        tableSplit.setOneTouchExpandable(true);
        tableSplit.setContinuousLayout(true);



        JSplitPane dbSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(connectSchema), new JScrollPane(dataBaseSchema));
        dbSplit.setOneTouchExpandable(true);
        dbSplit.setContinuousLayout(true);



        JSplitPane total = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, dbSplit, tableSplit);
        total.setOneTouchExpandable(true);
        total.setContinuousLayout(true);




        jf.add(total);

        jf.pack();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);



    }



}
