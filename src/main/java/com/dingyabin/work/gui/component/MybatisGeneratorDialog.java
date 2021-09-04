package com.dingyabin.work.gui.component;

import com.alee.extended.filechooser.WebDirectoryChooser;
import com.alee.managers.style.StyleId;
import com.dingyabin.work.common.cons.Const;
import com.dingyabin.work.common.generator.CatMybatisGenerator;
import com.dingyabin.work.common.generator.bean.ColumnNameCfg;
import com.dingyabin.work.common.generator.bean.TableNameCfg;
import com.dingyabin.work.common.generator.processor.*;
import com.dingyabin.work.common.model.ColumnSchema;
import com.dingyabin.work.common.model.ConnectConfig;
import com.dingyabin.work.common.model.DataBaseSchema;
import com.dingyabin.work.common.model.TableSchema;
import com.dingyabin.work.common.utils.CatUtils;
import com.dingyabin.work.ctrl.config.SpringBeanHolder;
import com.dingyabin.work.gui.component.model.IGeneratorTableModel;
import com.dingyabin.work.gui.component.model.ModelGeneratorTableModel;
import com.dingyabin.work.gui.component.xmledit.XMLEditorKit;
import com.dingyabin.work.gui.utils.GuiUtils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 丁亚宾
 * Date: 2021/8/29.
 * Time:22:15
 */
public class MybatisGeneratorDialog extends JDialog  implements ActionListener, ListSelectionListener {

    private java.util.List<TableNameCfg> tableNameCfgList;

    private Map<Object, List<ColumnNameCfg>> columnNameCfgMap;

    private ConnectConfig connectConfig;

    private DataBaseSchema dataBaseSchema;

    private LineBorder projectPanelLineBorder = new LineBorder(CatColors.COLOR_2, 3, true);

    private LineBorder modelPanelLineBorder = new LineBorder(CatColors.COLOR_4, 3, true);

    private LineBorder daoPanelLineBorder = new LineBorder(CatColors.COLOR_8, 3, true);

    private LineBorder xmlPanelLineBorder = new LineBorder(CatColors.COLOR_7, 3, true);

    private LineBorder optionPanelLineBorder = new LineBorder(CatColors.COLOR_5, 3, true);


    private JPanel projectInputPanel = new JPanel(new BorderLayout(10,5));

    private JLabel projectLabel = GuiUtils.createLabel("选择工程目录：", JLabel.LEFT, 15);

    private JTextField projectInputField = GuiUtils.createTextField(30, StyleId.textfieldNoFocus, CatFonts.MICRO_SOFT);

    private JButton projectInputBtn = GuiUtils.createButton("选择目录...", CatIcons.open, StyleId.button, this);


    /**
     * Model&Bean配置
     */
    private JPanel modelInputPanel = new JPanel(new BorderLayout(10,5));

    private JLabel modelPackageLabel = GuiUtils.createLabel("包名(Package)：", JLabel.LEFT, 14);

    private JTextField modelPackageInputField = GuiUtils.createTextField(30, StyleId.textfieldNoFocus, CatFonts.MICRO_SOFT);

    private JLabel modelPathLabel = GuiUtils.createLabel("Path：", JLabel.LEFT, 14);

    private JTextField modelPathInputField = GuiUtils.createTextField(10, StyleId.textfieldNoFocus, CatFonts.MICRO_SOFT);

    private JTable modelNameTable = new JTable();

    private JTable columnNameTable = new JTable();


    /**
     * DAO配置
     */
    private JPanel daoInputPanel = new JPanel(new BorderLayout(10,5));

    private JLabel daoPackageLabel = GuiUtils.createLabel("包名(Package)：", JLabel.LEFT, 14);

    private JTextField daoPackageInputField = GuiUtils.createTextField(30, StyleId.textfieldNoFocus, CatFonts.MICRO_SOFT);

    private JLabel daoPathLabel = GuiUtils.createLabel("Path：", JLabel.LEFT, 14);

    private JTextField daoPathInputField = GuiUtils.createTextField(10, StyleId.textfieldNoFocus, CatFonts.MICRO_SOFT);

    private JLabel daoReNameLabel = GuiUtils.createLabel("DAO名字后缀:", JLabel.RIGHT, 14);

    private JTextField daoReNameField = GuiUtils.createTextField(10, StyleId.textfieldNoFocus, CatFonts.MICRO_SOFT,"Mapper", Const.DAO_RENAME_DESC);



    /**
     * XML配置
     */
    private JPanel sqlXmlInputPanel = new JPanel(new BorderLayout(3,3));

    private JLabel xmlPackageLabel = GuiUtils.createLabel("包名(Package)：", JLabel.LEFT, 14);

    private JTextField xmlPackageInputField = GuiUtils.createTextField(30, StyleId.textfieldNoFocus, CatFonts.MICRO_SOFT);

    private JLabel xmlPathLabel = GuiUtils.createLabel("Path：", JLabel.LEFT, 14);

    private JTextField xmlPathInputField = GuiUtils.createTextField(10, StyleId.textfieldNoFocus, CatFonts.MICRO_SOFT);


    /**
     * option 配置
     */
    private JPanel optionInputPanel = new JPanel(new GridLayout(4,3,10,10));

    private JCheckBox overrideToString = GuiUtils.createCheckBox("重写toString", false);

    private JCheckBox imSerializable = GuiUtils.createCheckBox("实现Serializable接口", true);

    private JCheckBox ovEqualsHashCode = GuiUtils.createCheckBox("重写equals和hashCode", false);

    private JCheckBox addRemarkComments = GuiUtils.createCheckBox("使用表里的注释", true, Const.ADD_REMARK_COMMENTS);

    private JCheckBox forceBigDecimals = GuiUtils.createCheckBox("强制使用BigDecimal", false, Const.FORCE_BIG_DECIMALS);

    private JCheckBox useDateInModel = GuiUtils.createCheckBox("强制使用java.util.Date", true, Const.USE_DATE_IN_MODEL);

    private JCheckBox trimBeforeGet = GuiUtils.createCheckBox("getter方法返回前先trim", false);

    private JCheckBox useDateInModelInComment = GuiUtils.createCheckBox("注释里使用时间戳", false);

    private JCheckBox enableCountByExample = GuiUtils.createCheckBox("启用CountByExample", false);

    private JCheckBox enableDeleteByExample = GuiUtils.createCheckBox("启用DeleteByExample", false);

    private JCheckBox enableSelectByExample = GuiUtils.createCheckBox("启用SelectByExample", false);

    private JCheckBox enableUpdateByExample = GuiUtils.createCheckBox("启用UpdateByExample", false);



    /**
     * 按钮
     */
    private JPanel btnInputPanel = new JPanel();

    private JButton executeBtn = GuiUtils.createButton("直接执行", CatIcons.excute, StyleId.button, this, CatFonts.MICRO_SOFT_14);

    private JButton xmlBtn = GuiUtils.createButton("查看/修改XML后执行", CatIcons.xml, StyleId.button, this, CatFonts.MICRO_SOFT_14);



    public MybatisGeneratorDialog(java.util.List<TableNameCfg> tableNameCfgList, ConnectConfig connectConfig, DataBaseSchema dataBaseSchema) {
        super(ComContextManager.getMainFrame());
        this.tableNameCfgList = tableNameCfgList;
        this.connectConfig = connectConfig;
        this.dataBaseSchema = dataBaseSchema;
        init();
    }



    private void init() {
        //初始化table
        commonInitTable(modelNameTable, TableNameCfg.HEADER, TableNameCfg.EDIT_COLUMN_INDEX, tableNameCfgList,true);
        commonInitTable(columnNameTable, ColumnNameCfg.HEADER, ColumnNameCfg.EDIT_COLUMN_INDEX, Collections.emptyList(),false);
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
        projectInputPanel.setBorder(new TitledBorder(projectPanelLineBorder, "工程目录", TitledBorder.LEFT, TitledBorder.TOP, CatFonts.MICRO_SOFT_15));
        projectInputPanel.add(GuiUtils.createHorizontalBox(projectLabel, projectInputField, Box.createHorizontalStrut(20), projectInputBtn), BorderLayout.NORTH);


        //Model配置
        modelInputPanel.setBorder(new TitledBorder(modelPanelLineBorder, "Model&Bean配置", TitledBorder.LEFT, TitledBorder.TOP, CatFonts.MICRO_SOFT_15));
        modelPathInputField.setText(Const.GENERATOR_DEFAULT_CODE_PATH);
        modelInputPanel.add(GuiUtils.createHorizontalBox(modelPackageLabel, modelPackageInputField, Box.createHorizontalStrut(10), modelPathLabel, modelPathInputField), BorderLayout.NORTH);


        modelInputPanel.add(GuiUtils.createJscrollPane(modelNameTable), BorderLayout.WEST);
        modelInputPanel.add(GuiUtils.createLabel("双击可编辑", JLabel.TOP, JLabel.CENTER, JLabel.CENTER, CatIcons.doublelink), BorderLayout.CENTER);
        modelInputPanel.add(GuiUtils.createJscrollPane(columnNameTable), BorderLayout.EAST);

        //DAO配置
        daoInputPanel.setBorder(new TitledBorder(daoPanelLineBorder, "DAO配置", TitledBorder.LEFT, TitledBorder.TOP, CatFonts.MICRO_SOFT_15));
        daoPathInputField.setText(Const.GENERATOR_DEFAULT_CODE_PATH);
        daoInputPanel.add(GuiUtils.createHorizontalBox(daoPackageLabel, daoPackageInputField, Box.createHorizontalStrut(10), daoPathLabel, daoPathInputField, Box.createHorizontalStrut(10), daoReNameLabel, daoReNameField), BorderLayout.NORTH);

        //SQL XML配置
        sqlXmlInputPanel.setBorder(new TitledBorder(xmlPanelLineBorder, "XMLMapper配置", TitledBorder.LEFT, TitledBorder.TOP, CatFonts.MICRO_SOFT_15));
        xmlPathInputField.setText(Const.GENERATOR_DEFAULT_RESOURCES);
        sqlXmlInputPanel.add(GuiUtils.createHorizontalBox(xmlPackageLabel, xmlPackageInputField, Box.createHorizontalStrut(10), xmlPathLabel, xmlPathInputField), BorderLayout.NORTH);

        //选项
        optionInputPanel.setBorder(new TitledBorder(optionPanelLineBorder, "高级选项", TitledBorder.LEFT, TitledBorder.TOP, CatFonts.MICRO_SOFT_15));
        optionInputPanel.add(overrideToString);
        optionInputPanel.add(imSerializable);
        optionInputPanel.add(ovEqualsHashCode);
        optionInputPanel.add(addRemarkComments);
        optionInputPanel.add(forceBigDecimals);
        optionInputPanel.add(useDateInModel);
        optionInputPanel.add(trimBeforeGet);
        optionInputPanel.add(useDateInModelInComment);
        optionInputPanel.add(enableCountByExample);
        optionInputPanel.add(enableDeleteByExample);
        optionInputPanel.add(enableSelectByExample);
        optionInputPanel.add(enableUpdateByExample);

        //按钮
        btnInputPanel.add(executeBtn);
        btnInputPanel.add(xmlBtn);

        contentPane.add(projectInputPanel);
        contentPane.add(Box.createVerticalStrut(10));
        contentPane.add(modelInputPanel);
        contentPane.add(Box.createVerticalStrut(10));
        contentPane.add(daoInputPanel);
        contentPane.add(Box.createVerticalStrut(10));
        contentPane.add(sqlXmlInputPanel);
        contentPane.add(Box.createVerticalStrut(10));
        contentPane.add(optionInputPanel);
        contentPane.add(Box.createVerticalStrut(10));
        contentPane.add(btnInputPanel);
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
            return;
        }
        if (source == executeBtn) {
            execute();
            return;
        }
        if (source == xmlBtn) {
            previewXml();
        }
    }



    @Override
    public void valueChanged(ListSelectionEvent e) {
        Object source = e.getSource();
        if (source == modelNameTable.getSelectionModel() && e.getValueIsAdjusting()) {
            onSelectTableName();
        }
    }



    /**
     * 选中某一行表名时
     */
    private void onSelectTableName() {
        int selectedRow = modelNameTable.getSelectedRow();
        Object tableName = modelNameTable.getValueAt(selectedRow, TableNameCfg.UN_EDIT_COLUMN_INDEX);
        //查询这个表下面的列
        if (columnNameCfgMap == null) {
            columnNameCfgMap = new HashMap<>(tableNameCfgList.size());
        }
        List<ColumnNameCfg> columnNameCfgs = columnNameCfgMap.computeIfAbsent(tableName, o -> {
            List<ColumnSchema> columns = SpringBeanHolder.getCatAdapter().getColumnOnTableChange(connectConfig, new TableSchema(tableName.toString()));
            return columns.stream().map(column -> new ColumnNameCfg(column.getColumnName())).collect(Collectors.toList());
        });
        commonInitTable(columnNameTable, ColumnNameCfg.HEADER, ColumnNameCfg.EDIT_COLUMN_INDEX, columnNameCfgs, false);
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



    /**
     * 预览XML
     */
    private void previewXml() {
        JDialog jDialog = new JDialog();
        JEditorPane jEditorPane = new JEditorPane();
        //初始化
        initPreviewXmlWindow(jDialog, jEditorPane);
        //设置XML文件
        jEditorPane.setText(createXmlString());
        //添加组件
        jDialog.add(GuiUtils.createJscrollPane(jEditorPane), BorderLayout.CENTER);

        JPanel xmlBtnInputPanel = new JPanel();
        ActionListener actionListener = e -> execute(jEditorPane.getText());
        xmlBtnInputPanel.add(GuiUtils.createButton("执行", CatIcons.excute, StyleId.button, actionListener, CatFonts.MICRO_SOFT_14));
        jDialog.add(xmlBtnInputPanel, BorderLayout.SOUTH);

        //显示
        jDialog.setVisible(true);
    }



    /**
     * 初始化预览组件
     */
    private void initPreviewXmlWindow(JDialog jDialog, JEditorPane jEditorPane) {
        jEditorPane.setEditorKit(new XMLEditorKit());
        jEditorPane.setFont(CatFonts.MICRO_SOFT_15);
        jEditorPane.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.editorpane);
        //在最上面
        jDialog.setAlwaysOnTop(true);
        //窗口logo
        jDialog.setIconImage(CatIcons.cat.getImage());
        //title
        jDialog.setTitle("预览/修改 配置文件并执行");
        //大小
        jDialog.setSize(new Dimension(1200,800));
        //居中显示
        jDialog.setLocationRelativeTo(null);
        //关闭
        jDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }



    private String createXmlString() {
        String projectPath = projectInputField.getText();

        String modelPath = modelPathInputField.getText();
        String modelPackage = CatUtils.pathToPackage(modelPackageInputField.getText());

        String daoPath = daoPathInputField.getText();
        String daoPackage = CatUtils.pathToPackage(daoPackageInputField.getText());

        String xmlPath = xmlPathInputField.getText();
        String xmlPackage = CatUtils.pathToPackage(xmlPackageInputField.getText());

        List<ConfigXmlProcessor> configXmlProcessors = new ArrayList<>();

        PluginGeneratorProcessor pluginGenerator = new PluginGeneratorProcessor(overrideToString.isSelected(), imSerializable.isSelected(), ovEqualsHashCode.isSelected());

        CommentGeneratorProcessor commentGenerator = new CommentGeneratorProcessor(!useDateInModelInComment.isSelected(), false, addRemarkComments.isSelected());
        JdbcConnectionProcessor jdbcConnection = new JdbcConnectionProcessor(connectConfig, dataBaseSchema.getSchemaName());
        JavaTypeResolverProcessor javaTypeResolver = new JavaTypeResolverProcessor(forceBigDecimals.isSelected(), !useDateInModel.isSelected());

        JavaModelGeneratorProcessor javaModelGenerator = new JavaModelGeneratorProcessor(true,trimBeforeGet.isSelected());
        javaModelGenerator.setTargetProject(CatUtils.joinSystemPath(projectPath, modelPath));
        javaModelGenerator.setTargetPackage(modelPackage);

        SqlMapGeneratorProcessor sqlMapGenerator = new SqlMapGeneratorProcessor();
        sqlMapGenerator.setTargetProject(CatUtils.joinSystemPath(projectPath, xmlPath));
        sqlMapGenerator.setTargetPackage(xmlPackage);


        JavaClientGeneratorProcessor javaClientGenerator = new JavaClientGeneratorProcessor();
        javaClientGenerator.setTargetProject(CatUtils.joinSystemPath(projectPath, daoPath));
        javaClientGenerator.setTargetPackage(daoPackage);


        TableCfgProcessor tableCfgProcessor = new TableCfgProcessor(tableNameCfgList, columnNameCfgMap, daoReNameField.getText());
        tableCfgProcessor.setEnableCountByExample(enableCountByExample.isSelected());
        tableCfgProcessor.setEnableDeleteByExample(enableDeleteByExample.isSelected());
        tableCfgProcessor.setEnableSelectByExample(enableSelectByExample.isSelected());
        tableCfgProcessor.setEnableUpdateByExample(enableUpdateByExample.isSelected());

        configXmlProcessors.add(commentGenerator);
        configXmlProcessors.add(javaClientGenerator);
        configXmlProcessors.add(javaModelGenerator);
        configXmlProcessors.add(javaTypeResolver);
        configXmlProcessors.add(jdbcConnection);
        configXmlProcessors.add(pluginGenerator);
        configXmlProcessors.add(sqlMapGenerator);
        configXmlProcessors.add(tableCfgProcessor);

        return CatMybatisGenerator.getInstance().makeCfgXml(configXmlProcessors);
    }


    /**
     * 执行
     */
    private void execute() {
        execute(createXmlString());
    }



    /**
     * 执行xml
     */
    private void execute(String xml) {
        String message = CatMybatisGenerator.getInstance().generate(xml) ? "代码生成完毕！" : "生成错误，请检查配置！";
        GuiUtils.createOptionPane(this, message, JOptionPane.DEFAULT_OPTION);
    }



    private void commonInitTable(JTable table, String[] header, int editColumn, List<? extends IGeneratorTableModel> models , boolean needListener) {
        table.setModel(new ModelGeneratorTableModel(models, header));

        //设置渲染器
        if (editColumn >= 0) {
            JTextField jTextField = new JTextField();
            jTextField.setFont(CatFonts.CONSOLAS_15);
            table.getColumnModel().getColumn(editColumn).setCellEditor(new DefaultCellEditor(jTextField));
        }

        //设置监听器
        if (needListener) {
            ListSelectionModel selectionModel = table.getSelectionModel();
            selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            selectionModel.addListSelectionListener(this);
        }

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
        int height = Math.max(Math.min((int)table.getPreferredSize().getHeight(), 300), 45);
        table.setPreferredScrollableViewportSize(new Dimension(380, height));
    }


}
