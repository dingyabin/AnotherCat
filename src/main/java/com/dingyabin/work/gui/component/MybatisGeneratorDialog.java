package com.dingyabin.work.gui.component;

import com.alee.extended.filechooser.WebDirectoryChooser;
import com.alee.managers.style.StyleId;
import com.dingyabin.work.common.cons.Const;
import com.dingyabin.work.common.generator.bean.TableNameCfg;
import com.dingyabin.work.common.model.ConnectConfig;
import com.dingyabin.work.common.model.DataBaseSchema;
import com.dingyabin.work.gui.component.model.IGeneratorTableModel;
import com.dingyabin.work.gui.component.model.ModelGeneratorTableModel;
import com.dingyabin.work.gui.utils.GuiUtils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

/**
 * @author 丁亚宾
 * Date: 2021/8/29.
 * Time:22:15
 */
public class MybatisGeneratorDialog extends JDialog  implements ActionListener {

    private java.util.List<TableNameCfg> tableNameCfgList;

    private ConnectConfig connectConfig;

    private DataBaseSchema dataBaseSchema;

    private LineBorder lineBorder = new LineBorder(CatColors.GENERATOR_WINDOW_BORDER, 2, true);

    private JPanel projectInputPanel = new JPanel(new BorderLayout(10,5));

     private JLabel projectLabel = GuiUtils.createLabel("选择工程目录：", JLabel.LEFT, 15);

    private JTextField projectInputField = GuiUtils.createTextField(30, StyleId.textfieldNoFocus);

    private JButton projectInputBtn = GuiUtils.createButton("选择目录...", CatIcons.open, StyleId.button, this);


    /**
     * Model&Bean配置
     */
    private JPanel modelInputPanel = new JPanel(new BorderLayout(10,5));

    private JLabel modelPackageLabel = GuiUtils.createLabel("包名(Package)：", JLabel.LEFT, 14);

    private JTextField modelPackageInputField = GuiUtils.createTextField(30, StyleId.textfieldNoFocus);

    private JLabel modelPathLabel = GuiUtils.createLabel("Path：", JLabel.LEFT, 14);

    private JTextField modelPathInputField = GuiUtils.createTextField(10, StyleId.textfieldNoFocus);

    private JTable modelNameTable = new JTable();

    private JTable columnNameTable = new JTable();


    /**
     * DAO配置
     */
    private JPanel daoInputPanel = new JPanel(new BorderLayout(10,5));

    private JLabel daoPackageLabel = GuiUtils.createLabel("包名(Package)：", JLabel.LEFT, 14);

    private JTextField daoPackageInputField = GuiUtils.createTextField(30, StyleId.textfieldNoFocus);

    private JLabel daoPathLabel = GuiUtils.createLabel("Path：", JLabel.LEFT, 14);

    private JTextField daoPathInputField = GuiUtils.createTextField(10, StyleId.textfieldNoFocus);


    /**
     * XML配置
     */
    private JPanel sqlXmlInputPanel = new JPanel(new BorderLayout(10,5));

    private JLabel xmlPackageLabel = GuiUtils.createLabel("包名(Package)：", JLabel.LEFT, 14);

    private JTextField xmlPackageInputField = GuiUtils.createTextField(30, StyleId.textfieldNoFocus);

    private JLabel xmlPathLabel = GuiUtils.createLabel("Path：", JLabel.LEFT, 14);

    private JTextField xmlPathInputField = GuiUtils.createTextField(10, StyleId.textfieldNoFocus);


    public MybatisGeneratorDialog(java.util.List<TableNameCfg> tableNameCfgList) {
        super(ComContextManager.getMainFrame());
        this.tableNameCfgList = tableNameCfgList;
        init();
    }



    private void init() {
        //初始化table
        commonInitTable(modelNameTable, TableNameCfg.HEADER, tableNameCfgList);
        commonInitTable(columnNameTable, TableNameCfg.HEADER, tableNameCfgList);
        //组装组件
        generateComponent();
        //自适应大小
        pack();
        //设置可调大小
        setResizable(false);
        //位置居中设置
        setLocationRelativeTo(null);
        //默认关闭动作
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }


    private void generateComponent(){
        Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        //工程路径选择
        projectInputPanel.setBorder(new TitledBorder(lineBorder, "工程目录", TitledBorder.LEFT, TitledBorder.TOP, CatFonts.MICRO_SOFT_15));
        projectInputPanel.add(GuiUtils.createHorizontalBox(projectLabel, projectInputField, Box.createHorizontalStrut(20), projectInputBtn), BorderLayout.NORTH);


        //Model配置
        modelInputPanel.setBorder(new TitledBorder(lineBorder, "Model&Bean配置", TitledBorder.LEFT, TitledBorder.TOP, CatFonts.MICRO_SOFT_15));
        modelPathInputField.setText(Const.GENERATOR_DEFAULT_CODE_PATH);
        modelInputPanel.add(GuiUtils.createHorizontalBox(modelPackageLabel, modelPackageInputField, Box.createHorizontalStrut(10), modelPathLabel, modelPathInputField), BorderLayout.NORTH);


        modelInputPanel.add(GuiUtils.createJscrollPane(modelNameTable), BorderLayout.WEST);
        modelInputPanel.add(GuiUtils.createLabel("双击可编辑", JLabel.TOP, JLabel.CENTER, JLabel.CENTER, CatIcons.doublelink), BorderLayout.CENTER);
        modelInputPanel.add(GuiUtils.createJscrollPane(columnNameTable), BorderLayout.EAST);

        //DAO配置
        daoInputPanel.setBorder(new TitledBorder(lineBorder, "DAO配置", TitledBorder.LEFT, TitledBorder.TOP, CatFonts.MICRO_SOFT_15));
        daoPathInputField.setText(Const.GENERATOR_DEFAULT_CODE_PATH);
        daoInputPanel.add(GuiUtils.createHorizontalBox(daoPackageLabel, daoPackageInputField, Box.createHorizontalStrut(10), daoPathLabel, daoPathInputField), BorderLayout.NORTH);

        //SQL XML配置
        sqlXmlInputPanel.setBorder(new TitledBorder(lineBorder, "XMLMapper配置", TitledBorder.LEFT, TitledBorder.TOP, CatFonts.MICRO_SOFT_15));
        xmlPathInputField.setText(Const.GENERATOR_DEFAULT_CODE_PATH);
        sqlXmlInputPanel.add(GuiUtils.createHorizontalBox(xmlPackageLabel, xmlPackageInputField, Box.createHorizontalStrut(10), xmlPathLabel, xmlPathInputField), BorderLayout.NORTH);



        contentPane.add(projectInputPanel);
        contentPane.add(Box.createVerticalStrut(10));
        contentPane.add(modelInputPanel);
        contentPane.add(Box.createVerticalStrut(10));
        contentPane.add(daoInputPanel);
        contentPane.add(Box.createVerticalStrut(10));
        contentPane.add(sqlXmlInputPanel);
        setContentPane(GuiUtils.createJscrollPane(contentPane));
    }




    public void showSelf(){
        //弹出
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == projectInputBtn) {
            selectProjectPath();
        }

    }



    /**
     * 选择工程路径
     */
    private void selectProjectPath() {
        File file = WebDirectoryChooser.showDialog(MybatisGeneratorDialog.this, "选择工程目录");
        if (file != null) {
            projectInputField.setText(file.getAbsolutePath());
        }
    }




    private void commonInitTable(JTable table, String[] header, List<? extends IGeneratorTableModel> models ) {
        table.setModel(new ModelGeneratorTableModel(models, header));
        //表格字体设置
        table.setFont(CatFonts.CONSOLAS_15);
        table.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.table);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        //表头设置
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setReorderingAllowed(false);
        tableHeader.setFont(CatFonts.MICRO_SOFT_14);

        //行高
        table.setRowHeight(25);
        //大小设置
        table.setPreferredScrollableViewportSize(new Dimension(400, (int) Math.min(table.getPreferredSize().getHeight(), 400)));
    }


}
