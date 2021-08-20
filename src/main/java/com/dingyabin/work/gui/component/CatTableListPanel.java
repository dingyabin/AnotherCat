package com.dingyabin.work.gui.component;

import com.alee.managers.style.StyleId;
import com.dingyabin.work.common.model.TableSchema;
import com.dingyabin.work.gui.utils.GuiUtils;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * @author 丁亚宾
 * Date: 2021/8/20.
 * Time:21:30
 */
public class CatTableListPanel extends JPanel  implements ActionListener {

    private CatList<TableSchema> tableCatList;

    private JMenuItem copy = new JMenuItem("复制", CatIcons.copy);

    private JMenuItem open = new JMenuItem("打开", CatIcons.open);

    private JMenuItem delete = new JMenuItem("删除", CatIcons.delete);

    private JMenuItem modify = new JMenuItem("修改", CatIcons.design);

    private JPopupMenu jPopupMenu = new JPopupMenu();


    private JButton openTable = GuiUtils.createButton("打开表", CatIcons.open, StyleId.buttonIconHover);

    private JButton designTable = GuiUtils.createButton("设计表", CatIcons.design, StyleId.buttonIconHover);

    private JButton newTable = GuiUtils.createButton("新建表", CatIcons.newone, StyleId.buttonIconHover);

    private JButton deleteTable = GuiUtils.createButton("删除表", CatIcons.delete, StyleId.buttonIconHover);

    private JButton searchBtn = GuiUtils.createButton(StringUtils.EMPTY, CatIcons.search, StyleId.buttonIconHover);

    private JTextField searchInput = GuiUtils.createTextField(25, StyleId.textfieldNoFocus);

    private JPanel topBarPanel = new JPanel(new GridLayout(1,2));

    private KeyListener searchTriggerListener = new KeyAdapter(){
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                searchTable();
            }
        }
    };


    public CatTableListPanel() {
        super(new BorderLayout(0, 0));
        init();
    }


    public CatTableListPanel(CatList<TableSchema> tableSchemaList) {
        this();
        setTableCatList(tableSchemaList);
    }


    public void setTableCatList(CatList<TableSchema> tableSchemaList) {
        tableCatList = tableSchemaList;
        tableSchemaList.addPopMenuToList(jPopupMenu);
        add(GuiUtils.createJscrollPane(tableCatList), BorderLayout.CENTER);
    }



    private void init() {
        //安装右键菜单
        jPopupMenu.add(copy);
        jPopupMenu.add(open);
        jPopupMenu.add(modify);
        jPopupMenu.addSeparator();
        jPopupMenu.add(delete);

        //左侧的按钮区
        JPanel leftToolBarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));

        //设置监听
        openTable.addActionListener(this);
        designTable.addActionListener(this);
        newTable.addActionListener(this);
        deleteTable.addActionListener(this);
        searchBtn.addActionListener(this);

        //组装按钮
        leftToolBarPanel.add(openTable);
        leftToolBarPanel.add(designTable);
        leftToolBarPanel.add(newTable);
        leftToolBarPanel.add(deleteTable);

        //安装左侧的按钮区
        topBarPanel.add(leftToolBarPanel);

        //组装右侧的搜索栏
        JPanel rightToolBarPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        //按键监听器
        searchInput.addKeyListener(searchTriggerListener);
        //组装输入框
        rightToolBarPanel.add(searchInput);
        //组装搜索按钮
        rightToolBarPanel.add(searchBtn);

        //安装右侧的搜索栏
        topBarPanel.add(rightToolBarPanel);

        //安装工具栏
        add(topBarPanel,BorderLayout.NORTH);
    }



    public CatList<TableSchema> getTableCatList() {
        return tableCatList;
    }






    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == searchBtn) {
            searchTable();
        }

    }



    /**
     * 查找表
     */
    private void searchTable() {
        if (tableCatList == null) {
            return;
        }
        //清空选择
        tableCatList.clearSelection();
        //关键字
        String text = searchInput.getText();
        if (StringUtils.isBlank(text)) {
            return;
        }
        tableCatList.searchEveryElement((tableSchema, index) -> {
            if (tableSchema.getTableName().toUpperCase().contains(text.toUpperCase())) {
                tableCatList.addSelectionInterval(index,index);
            }
        });

    }


}
