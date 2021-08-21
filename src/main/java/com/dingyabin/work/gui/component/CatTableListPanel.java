package com.dingyabin.work.gui.component;

import com.alee.managers.style.StyleId;
import com.dingyabin.work.common.cons.Const;
import com.dingyabin.work.common.model.TableSchema;
import com.dingyabin.work.gui.utils.GuiUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;


/**
 * @author 丁亚宾
 * Date: 2021/8/20.
 * Time:21:30
 */
public class CatTableListPanel extends JPanel  implements ActionListener, ListSelectionListener {

    private CatList<TableSchema> tableCatList;

    private JMenuItem copy = new JMenuItem("复制", CatIcons.copy);

    private JMenuItem open = new JMenuItem("打开", CatIcons.open);

    private JMenuItem delete = new JMenuItem("删除", CatIcons.delete);

    private JMenuItem modify = new JMenuItem("修改", CatIcons.design);

    private JMenuItem reName = new JMenuItem("重命名", CatIcons.edit);

    private JPopupMenu jPopupMenu = new JPopupMenu();


    private JButton openTable = GuiUtils.createButton("打开表", CatIcons.open, StyleId.buttonIconHover);

    private JButton designTable = GuiUtils.createButton("设计表", CatIcons.design, StyleId.buttonIconHover);

    private JButton newTable = GuiUtils.createButton("新建表", CatIcons.newone, StyleId.buttonIconHover);

    private JButton reNameTable = GuiUtils.createButton("重命名", CatIcons.edit, StyleId.buttonIconHover);

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
        tableCatList.addListSelectionListener(this);
        tableSchemaList.addPopMenuToList(jPopupMenu);
        add(GuiUtils.createJscrollPane(tableCatList), BorderLayout.CENTER);
    }



    private void init() {
        //按钮先置灰
        onSelectOccur(0);

        //设置监听
        copy.addActionListener(this);
        open.addActionListener(this);
        modify.addActionListener(this);
        reName.addActionListener(this);
        delete.addActionListener(this);

        //安装右键菜单
        jPopupMenu.add(copy);
        jPopupMenu.add(open);
        jPopupMenu.add(modify);
        jPopupMenu.add(reName);
        jPopupMenu.addSeparator();
        jPopupMenu.add(delete);

        //左侧的按钮区
        JPanel leftToolBarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));

        //设置监听
        openTable.addActionListener(this);
        designTable.addActionListener(this);
        newTable.addActionListener(this);
        reNameTable.addActionListener(this);
        deleteTable.addActionListener(this);
        searchBtn.addActionListener(this);

        //组装按钮
        leftToolBarPanel.add(openTable);
        leftToolBarPanel.add(designTable);
        leftToolBarPanel.add(reNameTable);
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
        add(topBarPanel, BorderLayout.NORTH);
    }



    public CatList<TableSchema> getTableCatList() {
        return tableCatList;
    }




    @Override
    public void valueChanged(ListSelectionEvent e) {
        //如果选中了，就让按钮可用，否则就继续置灰按钮
        int[] selectedIndices = tableCatList.getSelectedIndices();
        onSelectOccur(ArrayUtils.getLength(selectedIndices));
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        //查找
        if (source == searchBtn) {
            searchTable();
        }
        if (source == copy) {
            copyTableName();
        }

    }


    /**
     * 复制表名字
     */
    private void copyTableName() {
        try {
            Object property = jPopupMenu.getClientProperty(Const.JLIST_CURRENT_SELECTED_INDEX);
            if (!(property instanceof Integer)) {
                return;
            }
            TableSchema modelByIndex = tableCatList.getModelByIndex((Integer) property);
            if (modelByIndex == null) {
                return;
            }
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(new StringSelection(modelByIndex.getTableName()), null);
        } catch (Exception e) {
            //ignore
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


    private void onSelectOccur(int selectSize) {
        openTable.setEnabled(selectSize > 0);
        designTable.setEnabled(selectSize == 1);
        deleteTable.setEnabled(selectSize > 0);
        reNameTable.setEnabled(selectSize == 1);
    }


}
