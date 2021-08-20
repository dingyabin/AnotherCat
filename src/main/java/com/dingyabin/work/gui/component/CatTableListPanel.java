package com.dingyabin.work.gui.component;

import com.alee.managers.style.StyleId;
import com.dingyabin.work.common.model.TableSchema;
import com.dingyabin.work.gui.utils.GuiUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * @author 丁亚宾
 * Date: 2021/8/20.
 * Time:21:30
 */
public class CatTableListPanel extends JPanel  implements ActionListener {

    private CatList<TableSchema> tableCatList;

    private JButton openTable = GuiUtils.createButton("打开表", CatIcons.open, StyleId.buttonIconHover);

    private JButton designTable = GuiUtils.createButton("设计表", CatIcons.design, StyleId.buttonIconHover);

    private JButton newTable = GuiUtils.createButton("新建表", CatIcons.newone, StyleId.buttonIconHover);

    private JButton deleteTable = GuiUtils.createButton("删除表", CatIcons.delete, StyleId.buttonIconHover);

    private JPanel topBarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));


    public CatTableListPanel() {
        super(new BorderLayout(0, 0));
        init();
    }


    public CatTableListPanel(CatList<TableSchema> tableSchemaList) {
        this();
        setTableCatList(tableSchemaList);
    }


    public CatTableListPanel setTableCatList(CatList<TableSchema> tableSchemaList) {
        tableCatList = tableSchemaList;
        add(GuiUtils.createJscrollPane(tableCatList), BorderLayout.CENTER);
        return this;
    }


    private void init() {
        //设置监听
        openTable.addActionListener(this);
        designTable.addActionListener(this);
        newTable.addActionListener(this);
        deleteTable.addActionListener(this);

        //组装按钮
        topBarPanel.add(openTable);
        topBarPanel.add(designTable);
        topBarPanel.add(newTable);
        topBarPanel.add(deleteTable);

        //安装按钮栏
        add(topBarPanel,BorderLayout.NORTH);
    }



    public CatList<TableSchema> getTableCatList() {
        return tableCatList;
    }






    @Override
    public void actionPerformed(ActionEvent e) {

    }






}
