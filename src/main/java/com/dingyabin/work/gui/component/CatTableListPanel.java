package com.dingyabin.work.gui.component;

import com.alee.managers.style.StyleId;
import com.alee.utils.swing.extensions.FontMethodsImpl;
import com.dingyabin.work.common.generator.bean.TableNameCfg;
import com.dingyabin.work.common.model.ConnectConfig;
import com.dingyabin.work.common.model.DataBaseSchema;
import com.dingyabin.work.common.model.TableSchema;
import com.dingyabin.work.ctrl.adapter.CatAdapterService;
import com.dingyabin.work.ctrl.config.SpringBeanHolder;
import com.dingyabin.work.gui.utils.GuiUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author 丁亚宾
 * Date: 2021/8/20.
 * Time:21:30
 */
public class CatTableListPanel extends JPanel  implements ActionListener, ListSelectionListener, PopupMenuListener {

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


    private JMenuItem copyMenu = new JMenuItem("复制", CatIcons.copy);

    private JMenuItem openMenu = new JMenuItem("打开", CatIcons.open);

    private JMenuItem deleteMenu = new JMenuItem("删除", CatIcons.delete);

    private JMenuItem modifyMenu = new JMenuItem("设计", CatIcons.design);

    private JMenuItem reNameMenu = new JMenuItem("重命名", CatIcons.edit);

    private JMenuItem mybatisMenu = new JMenuItem("Mybatis代码生成", CatIcons.mybatis);

    private JMenuItem reFreshMenu = new JMenuItem("刷新", CatIcons.refresh);

    private JPopupMenu jPopupMenu = new JPopupMenu();


    private JButton openTableBtn = GuiUtils.createButton("打开表", CatIcons.open, StyleId.buttonIconHover);

    private JButton designTableBtn = GuiUtils.createButton("设计表", CatIcons.design, StyleId.buttonIconHover);

    private JButton newTableBtn = GuiUtils.createButton("新建表", CatIcons.newone, StyleId.buttonIconHover);

    private JButton reNameTableBtn = GuiUtils.createButton("重命名", CatIcons.edit, StyleId.buttonIconHover);

    private JButton deleteTableBtn = GuiUtils.createButton("删除表", CatIcons.delete, StyleId.buttonIconHover);

    private JButton searchBtnBtn = GuiUtils.createButton(StringUtils.EMPTY, CatIcons.search, StyleId.buttonIconHover);

    private JTextField searchInput = GuiUtils.createTextField(25, StyleId.textfieldNoFocus, CatFonts.DEFAULT_FONT_NAME);

    private JPanel topBarPanel = new JPanel(new GridLayout(1,2));

    private KeyListener searchTriggerListener = new KeyAdapter(){
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                searchTable();
            }
        }
    };


    public CatTableListPanel(CatList<TableSchema> tableSchemaList, ConnectConfig connectConfig, DataBaseSchema dataBaseSchema) {
        super(new BorderLayout(0, 0));
        this.connectConfig = connectConfig;
        this.dataBaseSchema = dataBaseSchema;
        this.tableCatList = tableSchemaList;
        initComponent();
    }


    public CatTableListPanel() {
        this(new CatList<TableSchema>(CatIcons.table).fontSize(15).fontName(CatFonts.DEFAULT_FONT_NAME).layoutVW().visCount(0).multi(), null, null);
    }




    private void initComponent() {
        //列表监听
        tableCatList.addListSelectionListener(this);

        //右键菜单弹框设置监听
        jPopupMenu.addPopupMenuListener(this);
        //列表右键菜单
        tableCatList.setComponentPopupMenu(jPopupMenu);

        //右键菜单项设置监听
        copyMenu.addActionListener(this);
        openMenu.addActionListener(this);
        modifyMenu.addActionListener(this);
        reNameMenu.addActionListener(this);
        deleteMenu.addActionListener(this);
        reFreshMenu.addActionListener(this);
        mybatisMenu.addActionListener(this);

        //安装右键菜单项
        jPopupMenu.add(copyMenu);
        jPopupMenu.add(openMenu);
        jPopupMenu.add(modifyMenu);
        jPopupMenu.add(reNameMenu);
        jPopupMenu.addSeparator();
        jPopupMenu.add(deleteMenu);
        jPopupMenu.addSeparator();
        jPopupMenu.add(mybatisMenu);
        jPopupMenu.addSeparator();
        jPopupMenu.add(reFreshMenu);

        //左侧的按钮区
        JPanel leftToolBarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));

        //设置监听
        openTableBtn.addActionListener(this);
        designTableBtn.addActionListener(this);
        newTableBtn.addActionListener(this);
        reNameTableBtn.addActionListener(this);
        deleteTableBtn.addActionListener(this);
        searchBtnBtn.addActionListener(this);

        //组装按钮
        leftToolBarPanel.add(openTableBtn);
        leftToolBarPanel.add(designTableBtn);
        leftToolBarPanel.add(reNameTableBtn);
        leftToolBarPanel.add(newTableBtn);
        leftToolBarPanel.add(deleteTableBtn);

        //安装左侧的按钮区
        topBarPanel.add(leftToolBarPanel);

        //组装右侧的搜索栏
        JPanel rightToolBarPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        //按键监听器
        searchInput.addKeyListener(searchTriggerListener);
        //组装输入框
        rightToolBarPanel.add(searchInput);
        //组装搜索按钮
        rightToolBarPanel.add(searchBtnBtn);

        //安装右侧的搜索栏
        topBarPanel.add(rightToolBarPanel);

        //安装工具栏
        add(topBarPanel, BorderLayout.NORTH);

        //底部状态栏
        add(bottomBar, BorderLayout.SOUTH);

        //初始化对话框
        initDialog();

        //添加列表
        add(GuiUtils.createJscrollPane(tableCatList), BorderLayout.CENTER);

        //底部bar
        bottomBar.setHorizontalTextPosition(SwingConstants.RIGHT);

        //按钮状态设置
        changeToolMenuWhenSelectOccur(0);
        //刷新底部bar
        refreshBottomBar();
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



    /**
     * 清空列表
     */
    public void clearTableList() {
        this.connectConfig = null;
        this.dataBaseSchema = null;
        if (tableCatList != null) {
            tableCatList.clear();
        }
        refreshBottomBar();
    }




    @Override
    public void valueChanged(ListSelectionEvent e) {
        //如果选中了，就让按钮可用，否则就继续置灰按钮
        int[] selectedIndices = tableCatList.getSelectedIndices();
        changeToolMenuWhenSelectOccur(ArrayUtils.getLength(selectedIndices));
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        //查找
        if (source == searchBtnBtn) {
            searchTable();
            return;
        }
        if (source == copyMenu) {
            copyTableName();
            return;
        }
        if (source == reNameMenu || source == reNameTableBtn) {
            showReNameTableDialog();
            return;
        }
        if (source == okToRename) {
            doReNameTable();
            return;
        }
        if (source == cancelToRename) {
            renameDialog.dispose();
            return;
        }
        if (source == reFreshMenu) {
            refreshTables();
            return;
        }
        if (source == mybatisMenu) {
            mybatisGenerator();
        }
    }


    @Override
    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        if (e.getSource() != jPopupMenu || tableCatList == null) {
            return;
        }
        //右键菜单弹出时选中的项目
        changePopupMenuWhenSelectOccur(ArrayUtils.getLength(tableCatList.getSelectedIndices()));
    }

    @Override
    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

    }

    @Override
    public void popupMenuCanceled(PopupMenuEvent e) {

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
            List<TableSchema> selectedValues = tableCatList.getSelectedValuesList();
            if (CollectionUtils.isEmpty(selectedValues)) {
                return;
            }
            String copyText;
            //只选中了一个,则直接get(0)
            if (selectedValues.size() == 1) {
                copyText = selectedValues.get(0).getTableName();
            } else {
                copyText = selectedValues.stream().map(TableSchema::getTableName).collect(Collectors.joining(","));
            }
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(new StringSelection(copyText), null);
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



    /**
     * 刷新底部bar
     */
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


    /**
     * 当选择改变时，改变toolbar的按钮状态
     * @param selectSize 选中的个数
     */
    private void changeToolMenuWhenSelectOccur(int selectSize) {
        openTableBtn.setEnabled(selectSize > 0);
        designTableBtn.setEnabled(selectSize == 1);
        deleteTableBtn.setEnabled(selectSize > 0);
        reNameTableBtn.setEnabled(selectSize == 1);
        newTableBtn.setEnabled(this.connectConfig != null && this.dataBaseSchema != null);
    }


    /**
     * 当右键时，改变右键菜单的按钮状态
     * @param selectSize 选中的个数
     */
    private void changePopupMenuWhenSelectOccur(int selectSize) {
        copyMenu.setEnabled(selectSize > 0);
        deleteMenu.setEnabled(selectSize > 0);
        openMenu.setEnabled(selectSize > 0);
        mybatisMenu.setEnabled(selectSize > 0);
        reNameMenu.setEnabled(selectSize == 1);
        modifyMenu.setEnabled(selectSize == 1);
        reFreshMenu.setEnabled(this.connectConfig != null && this.dataBaseSchema != null);
    }



    private void mybatisGenerator(){
        List<TableSchema> selectedValues = tableCatList.getSelectedValuesList();
        if (CollectionUtils.isEmpty(selectedValues)) {
            return;
        }
        java.util.List<TableNameCfg> tableNameCfg = new ArrayList<>();
        selectedValues.forEach(tableSchema -> tableNameCfg.add(new TableNameCfg(tableSchema.getTableName())));


        new MybatisGeneratorDialog(tableNameCfg, Collections.emptyList()).showSelf();
    }
}
