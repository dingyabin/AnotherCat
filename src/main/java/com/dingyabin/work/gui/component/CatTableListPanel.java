package com.dingyabin.work.gui.component;

import com.alee.managers.style.StyleId;
import com.alee.utils.swing.extensions.FontMethodsImpl;
import com.dingyabin.work.common.cons.Const;
import com.dingyabin.work.common.model.ConnectConfig;
import com.dingyabin.work.common.model.DataBaseSchema;
import com.dingyabin.work.common.model.TableSchema;
import com.dingyabin.work.ctrl.adapter.CatAdapterService;
import com.dingyabin.work.ctrl.config.SpringBeanHolder;
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
import java.util.List;


/**
 * @author 丁亚宾
 * Date: 2021/8/20.
 * Time:21:30
 */
public class CatTableListPanel extends JPanel  implements ActionListener, ListSelectionListener {

    private ConnectConfig connectConfig;

    private DataBaseSchema dataBaseSchema;

    private CatList<TableSchema> tableCatList;

    private JLabel bottomBar =  FontMethodsImpl.setFontSize(new JLabel(CatIcons.dbcon),13);

    private JDialog renameDialog = new JDialog(ComContextManager.getMainFrame(), "重命名", true);

    private JTextField renameField = FontMethodsImpl.setFontName(new JTextField(35), CatFonts.DEFAULT_FONT_NAME);

    private JLabel renameLabel = FontMethodsImpl.setFontSize(new JLabel("新表名："),14);

    private JPanel reNameInputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,5,20));

    private JPanel reNameButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));

    private JButton okToRename = FontMethodsImpl.setFontSize(new JButton("确定", CatIcons.ok), 14);

    private JButton cancelToRename =FontMethodsImpl.setFontSize(new JButton("取消", CatIcons.cancel), 14);


    private JMenuItem copy = new JMenuItem("复制", CatIcons.copy);

    private JMenuItem open = new JMenuItem("打开", CatIcons.open);

    private JMenuItem delete = new JMenuItem("删除", CatIcons.delete);

    private JMenuItem modify = new JMenuItem("修改", CatIcons.design);

    private JMenuItem reName = new JMenuItem("重命名", CatIcons.edit);

    private JMenuItem reFresh = new JMenuItem("刷新", CatIcons.refresh);

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


    public CatTableListPanel(ConnectConfig connectConfig, DataBaseSchema dataBaseSchema) {
        super(new BorderLayout(0, 0));
        this.connectConfig = connectConfig;
        this.dataBaseSchema = dataBaseSchema;
        init();
    }


    public CatTableListPanel(CatList<TableSchema> tableSchemaList, ConnectConfig connectConfig, DataBaseSchema dataBaseSchema) {
        this(connectConfig, dataBaseSchema);
        setTableCatList(tableSchemaList);
    }


    public void setTableCatList(CatList<TableSchema> tableSchemaList) {
        tableCatList = tableSchemaList;
        tableCatList.addListSelectionListener(this);
        tableSchemaList.addPopMenuToList(jPopupMenu);
        add(GuiUtils.createJscrollPane(tableCatList), BorderLayout.CENTER);
        refreshBottomBar();
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
        reFresh.addActionListener(this);

        //安装右键菜单
        jPopupMenu.add(copy);
        jPopupMenu.add(open);
        jPopupMenu.add(modify);
        jPopupMenu.add(reName);
        jPopupMenu.addSeparator();
        jPopupMenu.add(delete);
        jPopupMenu.addSeparator();
        jPopupMenu.add(reFresh);

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

        //底部状态栏
        add(bottomBar, BorderLayout.SOUTH);

        //初始化对话框
        initDialog();

        //底部bar
        bottomBar.setHorizontalTextPosition(SwingConstants.RIGHT);
    }



    private void initDialog(){
        //对话框居中
        reNameInputPanel.add(renameLabel);
        reNameInputPanel.add(renameField);

        okToRename.addActionListener(this);
        cancelToRename.addActionListener(this);

        reNameButtonPanel.add(okToRename);
        reNameButtonPanel.add(cancelToRename);

        //大小设置
        renameDialog.setSize(new Dimension(350, 120));
        //不可调大小
        renameDialog.setResizable(false);
        //位置居中设置
        renameDialog.setLocationRelativeTo(null);
        //默认关闭动作
        renameDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        //添加组件
        renameDialog.add(reNameInputPanel);
        renameDialog.add(reNameButtonPanel, BorderLayout.SOUTH);
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
            return;
        }
        if (source == copy) {
            copyTableName();
            return;
        }
        if (source == reName || source == reNameTable) {
            showReNameTableDialog();
            return;
        }
        if (source == okToRename) {
            doReNameTable();
            return;
        }
        if (source == cancelToRename) {
            renameDialog.dispose();
        }
        if (source == reFresh) {
            refreshTables();
        }
    }


    /**
     * 重命名表
     */
    private void doReNameTable() {
        //参数校验
        String newName = renameField.getText();
        if (StringUtils.isBlank(newName)) {
            return;
        }
        String oldName = tableCatList.getSelectedValue().getTableName();
        CatAdapterService catAdapter = SpringBeanHolder.getCatAdapter();
        //如果新的等于老的，do nothing
        if (StringUtils.equals(oldName, newName)) {
            return;
        }
        //重命名
        if (catAdapter.reNameTable(connectConfig, dataBaseSchema, oldName, newName)) {
            renameDialog.dispose();
            refreshTables();
        } else {
            GuiUtils.createOptionPane(this,  "修改失败....", JOptionPane.DEFAULT_OPTION);
        }
    }


    /**
     * 打开重命名表对话框
     */
    private void showReNameTableDialog() {
        renameField.setText(tableCatList.getSelectedValue().getTableName());
        renameDialog.setVisible(true);
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


    /**
     * 刷新列表
     */
    private void refreshTables() {
        if (connectConfig == null || dataBaseSchema == null) {
            return;
        }
        CatAdapterService catAdapter = SpringBeanHolder.getCatAdapter();
        //查询这个库下面的表
        List<TableSchema> tables = catAdapter.getTablesWithDb(connectConfig, dataBaseSchema);
        tableCatList.clearAndResetModel(tables);
        refreshBottomBar();
    }


    private void refreshBottomBar(){
        StringBuilder builder  = new StringBuilder();
        if (connectConfig != null) {
            builder.append("连接:").append(connectConfig.getName());
            builder.append("    类型:").append(connectConfig.getType());
            builder.append("    Host:").append(connectConfig.getHost());
            builder.append("    数据库:").append(dataBaseSchema.getSchemaName());
            builder.append("    用户:").append(connectConfig.getUserName());
        }
        if (tableCatList != null) {
            builder.append("    共").append(tableCatList.getModel().getSize()).append("张表");
        }
        bottomBar.setText(builder.toString());
    }


    private void onSelectOccur(int selectSize) {
        openTable.setEnabled(selectSize > 0);
        designTable.setEnabled(selectSize == 1);
        deleteTable.setEnabled(selectSize > 0);
        reNameTable.setEnabled(selectSize == 1);
    }


}
