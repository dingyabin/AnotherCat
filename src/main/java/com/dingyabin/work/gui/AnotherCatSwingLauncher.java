package com.dingyabin.work.gui;

import com.dingyabin.work.adapter.Adapter;
import com.dingyabin.work.ctrl.model.ColumnSchema;
import com.dingyabin.work.ctrl.model.ConnectConfig;
import com.dingyabin.work.ctrl.model.DataBaseSchema;
import com.dingyabin.work.ctrl.model.TableSchema;
import com.dingyabin.work.gui.componet.CatComBox;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.plaf.synth.SynthLookAndFeel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.List;

/**
 * @author 丁亚宾
 * Date: 2021/8/1.
 * Time:23:47
 */

public class AnotherCatSwingLauncher {

    @Resource
    private Adapter adapter;

    //窗口
    private final JFrame jf = new JFrame("这是一个swing窗口");


    //JMenuBar
    private JMenuBar jMenuBar = new JMenuBar();

    //菜单
    private JMenu fileMenu = new JMenu("文件");
    private JMenu editMenu = new JMenu("编辑");


    private JMenuItem auto = new JMenuItem("自动换行");
    private JMenuItem copy = new JMenuItem("复制");
    private JMenuItem paste = new JMenuItem("粘贴");
    private JMenu format = new JMenu("格式");


    private JMenuItem comment = new JMenuItem("注释");
    private JMenuItem cancelComment = new JMenuItem("取消注释");



    private JPopupMenu jPopupMenu = new JPopupMenu();

    private JMenuItem theam1 = new JMenuItem("主题1");
    private JMenuItem theam2 = new JMenuItem("主题2");
    private JMenuItem theam3 = new JMenuItem("主题3");



    private CatComBox<DataBaseSchema> colorSelect = new CatComBox<>();
    private CatComBox<TableSchema> tableSelect = new CatComBox<>();



    private JTextArea ta = new JTextArea(10,70);

    private JTextField jt = new JTextField(60);
    private JButton ok = new JButton("确定");


    public void init() {

        jMenuBar.add(fileMenu);


        editMenu.add(auto);
        editMenu.addSeparator();
        editMenu.add(copy);
        editMenu.add(paste);
        editMenu.addSeparator();


        comment.setToolTipText("将程序代码注释起来");
        format.add(comment);
        format.add(cancelComment);
        editMenu.add(format);

        jMenuBar.add(editMenu);


        jf.setJMenuBar(jMenuBar);



        JPanel leftTop = new JPanel();

        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(ta);


        JPanel select = new JPanel();


        colorSelect.addItemListener(e -> {
            int stateChange = e.getStateChange();
            if (stateChange == ItemEvent.SELECTED) {
                ConnectConfig connectConfig = getColorSelect().getConnectConfig();

                getTableSelect().setConnectConfig(connectConfig);
                List<TableSchema> tables = adapter.getTablesOnDbSchemaChange(connectConfig, (DataBaseSchema) e.getItem());
                getTableSelect().removeAllItems();
                tables.forEach(this::addTableSelectItem);
            }
        });

        tableSelect.addItemListener(e -> {
            int stateChange = e.getStateChange();
            if (stateChange == ItemEvent.SELECTED) {
                ConnectConfig connectConfig = getTableSelect().getConnectConfig();
                List<ColumnSchema> columns = adapter.getColumnOnTableChange(connectConfig, (TableSchema) e.getItem());
                ta.setText(StringUtils.EMPTY);
                columns.forEach(columnSchema -> ta.append(columnSchema.toString()+"\n"));
            }
        });

        select.add(colorSelect);
        select.add(tableSelect);

        verticalBox.add(select);


        leftTop.add(verticalBox);


        Box horizontalBox2 = Box.createHorizontalBox();
        horizontalBox2.add(jt);
        horizontalBox2.add(ok);


        jf.add(leftTop);

        jf.add(horizontalBox2 , BorderLayout.SOUTH);


        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                change(e.getActionCommand());
            }
        };

        theam1.addActionListener(actionListener);
        theam2.addActionListener(actionListener);
        theam3.addActionListener(actionListener);

        jPopupMenu.add(theam1);
        jPopupMenu.addSeparator();
        jPopupMenu.add(theam2);
        jPopupMenu.addSeparator();
        jPopupMenu.add(theam3);

        ta.setComponentPopupMenu(jPopupMenu);


        jf.pack();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
    }



    public void addDbSelectItem(DataBaseSchema connectConfig){
        colorSelect.addItem(connectConfig);
    }


    public void addTableSelectItem(TableSchema connectConfig){
        tableSelect.addItem(connectConfig);
    }


    public CatComBox<DataBaseSchema> getColorSelect() {
        return colorSelect;
    }

    public CatComBox<TableSchema> getTableSelect() {
        return tableSelect;
    }


    private void change(String context){
        try {
            if (context.equals(theam1.getText())) {
                UIManager.setLookAndFeel(MetalLookAndFeel.class.getName());
            }
            if (context.equals(theam2.getText())) {
                UIManager.setLookAndFeel(SynthLookAndFeel.class.getName());
            }
            if (context.equals(theam3.getText())) {
                UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
            }

            SwingUtilities.updateComponentTreeUI(jf.getContentPane());
            SwingUtilities.updateComponentTreeUI(jMenuBar);
            SwingUtilities.updateComponentTreeUI(jPopupMenu);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    public static void main(String[] args) {
//        new SwingCompentDemo().init();
//    }


}
