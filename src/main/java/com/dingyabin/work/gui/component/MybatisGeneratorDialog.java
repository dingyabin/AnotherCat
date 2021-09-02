package com.dingyabin.work.gui.component;

import com.alee.extended.filechooser.WebDirectoryChooser;
import com.alee.managers.style.StyleId;
import com.dingyabin.work.common.cons.Const;
import com.dingyabin.work.common.generator.bean.TableNameCfg;
import com.dingyabin.work.common.model.ConnectConfig;
import com.dingyabin.work.common.model.DataBaseSchema;
import com.dingyabin.work.gui.utils.GuiUtils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * @author 丁亚宾
 * Date: 2021/8/29.
 * Time:22:15
 */
public class MybatisGeneratorDialog extends JDialog  implements ActionListener {

    private java.util.List<TableNameCfg> tableNameCfgList;

    private ConnectConfig connectConfig;

    private DataBaseSchema dataBaseSchema;

    private LineBorder lineBorder = new LineBorder(CatColors.GENERATOR_WINDOW_BORDER, 3, true);

    private JPanel projectInputPanel = new JPanel();

     private JLabel projectLabel = GuiUtils.createLabel("选择工程目录：", SwingConstants.RIGHT, 15);

    private JTextField projectInputField = GuiUtils.createTextField(50, StyleId.textfieldNoFocus);

    private JButton projectInputBtn = GuiUtils.createButton("选择目录...", CatIcons.open, StyleId.button, this);


    //Model&Bean配置
    private JPanel modelInputPanel = new JPanel();

    private JLabel modelPackageLabel = GuiUtils.createLabel("包名(Package)：", SwingConstants.RIGHT, 14);

    private JTextField modelPackageInputField = GuiUtils.createTextField(40, StyleId.textfieldNoFocus);

    private JLabel modelPathLabel = GuiUtils.createLabel("Path：", SwingConstants.RIGHT, 14);

    private JTextField modelPathInputField = GuiUtils.createTextField(20, StyleId.textfieldNoFocus);



    private JPanel daoInputPanel = new JPanel();


    private JPanel sqlXmlInputPanel = new JPanel();


    public MybatisGeneratorDialog(java.util.List<TableNameCfg> tableNameCfgList) {
        super(ComContextManager.getMainFrame());
        this.tableNameCfgList = tableNameCfgList;
        init();
    }



    private void init(){
        generateComponent();
        //自适应大小
        pack();
        //可调大小
        setResizable(true);
        //位置居中设置
        setLocationRelativeTo(null);
        //默认关闭动作
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }


    private void generateComponent(){
        Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        //工程路径选择
        projectInputPanel.setBorder(new TitledBorder(lineBorder, "工程目录", TitledBorder.LEFT, TitledBorder.TOP, CatFonts.GENERATOR_WINDOW_BORDER));
        projectInputPanel.add(GuiUtils.createHorizontalBox(projectLabel, projectInputField, Box.createHorizontalStrut(20), projectInputBtn));


        //Model配置
        modelInputPanel.setBorder(new TitledBorder(lineBorder, "Model&Bean配置", TitledBorder.LEFT, TitledBorder.TOP, CatFonts.GENERATOR_WINDOW_BORDER));
        modelPathInputField.setText(Const.GENERATOR_DEFAULT_CODE_PATH);
        modelInputPanel.add(GuiUtils.createHorizontalBox(modelPackageLabel, modelPackageInputField, Box.createHorizontalStrut(10), modelPathLabel, modelPathInputField));


        //DAO配置
        daoInputPanel.setBorder(new TitledBorder(lineBorder, "DAO配置", TitledBorder.LEFT, TitledBorder.TOP, CatFonts.GENERATOR_WINDOW_BORDER));


        //SQL XML配置
        sqlXmlInputPanel.setBorder(new TitledBorder(lineBorder, "XMLMapper配置", TitledBorder.LEFT, TitledBorder.TOP, CatFonts.GENERATOR_WINDOW_BORDER));




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


}
