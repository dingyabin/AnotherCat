package com.dingyabin.work.swingtest;

import com.alee.managers.style.StyleId;
import com.dingyabin.work.ctrl.adapter.Adapter;
import com.dingyabin.work.common.model.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * @author 丁亚宾
 * Date: 2021/8/1.
 * Time:23:47
 */

@Component
public class AnotherCatSwingTest {


    @Resource
    private Adapter adapter;


    private final JFrame jf = new JFrame("AnotherCat");


    private JList<ConnectConfig> connectSchema = new JList<>();

    private JList<DataBaseSchema> dataBaseSchema = new JList<>();

    private JList<TableSchema> tableSchema = new JList<>();

    private JList<String> tableContentSchema = new JList<>();


    public void init() {

        jf.setPreferredSize(new Dimension(1600,1000));


        tableSchema.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


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



        tableSchema.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    TableSchema table = tableSchema.getSelectedValue();
                    ConnectConfig con = connectSchema.getSelectedValue();
                    DataBaseSchema db = dataBaseSchema.getSelectedValue();
                    List<Map<String, Object>> maps = adapter.queryTable(new DataSourceKey(con.getHost(), con.getPort(), db.getSchemaName()), table.getTableName());
                    DefaultListModel<String> listModel = new DefaultListModel<>();
                    maps.forEach(stringObjectMap -> listModel.addElement(stringObjectMap.toString()));
                    tableContentSchema.setModel(listModel);
                }
            }
        });



        JScrollPane tableContentScrollPane = new JScrollPane(tableContentSchema);
        tableContentScrollPane.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.scrollpaneTransparentButtonless );

        JScrollPane tableSchemaScrollPane = new JScrollPane(tableSchema);
        tableSchemaScrollPane.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.scrollpaneTransparentButtonless );

        JSplitPane tableSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tableSchemaScrollPane, tableContentScrollPane);
        tableSplit.setOneTouchExpandable(true);
        tableSplit.setContinuousLayout(true);



        JScrollPane connectSchemaScrollPane = new JScrollPane(connectSchema);
        connectSchemaScrollPane.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.scrollpaneTransparentButtonless );

        JScrollPane dataBaseSchemaScrollPane = new JScrollPane(dataBaseSchema);
        dataBaseSchemaScrollPane.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.scrollpaneTransparentButtonless );


        JSplitPane dbSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, connectSchemaScrollPane, dataBaseSchemaScrollPane);
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
