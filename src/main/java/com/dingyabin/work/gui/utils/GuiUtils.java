package com.dingyabin.work.gui.utils;

import com.alee.managers.notification.NotificationManager;
import com.alee.managers.style.StyleId;
import com.alee.utils.swing.extensions.FontMethodsImpl;
import com.dingyabin.work.gui.component.CatFonts;
import com.dingyabin.work.gui.component.CatIcons;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URI;

/**
 * @author 丁亚宾
 * Date: 2021/8/9.
 * Time:22:22
 */
@Slf4j
public class GuiUtils {


    /**
     * 使用默认浏览器打开链接
     * @param url url
     */
    public static void openBrowser(String url) {
        try {
            if (!Desktop.isDesktopSupported()) {
                NotificationManager.showNotification("设备暂不支持自动打开!", CatIcons.cry);
                return;
            }
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(URI.create(url));
        } catch (Exception ex) {
            log.error("使用默认浏览器打开链接异常,url={}", url, ex);
        }
    }


    /**
     * JFrame的通用操作
     *
     * @param jf JFrame
     */
    public static void jFrameCommonAction(JFrame jf) {
        jf.pack();
        //居中显示
        jf.setLocationRelativeTo(null);
        //关闭
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //显示
        jf.setVisible(true);

    }


    /**
     * 带样式的滚动条
     *
     * @param component 需要加滚动条的组件
     * @return 滚动条
     */
    public static JScrollPane createJscrollPane(Component component) {
        JScrollPane scrollPane = new JScrollPane(component);
        scrollPane.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.scrollpaneTransparentButtonless);
        return scrollPane;
    }



    /**
     * 带样式的滚动条
     *
     * @param component 需要加滚动条的组件
     * @return 滚动条
     */
    public static JScrollPane createJscrollPane(Component component, int with, int height) {
        JScrollPane scrollPane = new JScrollPane(component);
        scrollPane.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.scrollpaneTransparentButtonless);
        scrollPane.setPreferredSize(new Dimension(with, height));
        return scrollPane;
    }




    /**
     * 展示
     * @param container 父容器
     * @param message 提示信息
     * @return 是否选中yes
     */
    public static boolean createYesNoOptionPane(Component container, String message) {
        int opt = JOptionPane.showConfirmDialog(container, message, "确认信息", JOptionPane.YES_NO_OPTION);
        return opt == JOptionPane.YES_OPTION;
    }

    /**
     * 展示
     * @param container 父容器
     * @param message 提示信息
     * @param optionType optionType
     */
    public static void createOptionPane(Component container, String message, int optionType) {
        JOptionPane.showConfirmDialog(container, message, "确认信息", optionType);
    }


    /**
     * 创建一个Label
     * @param text 文字
     * @param horizontalAlignment 对齐方式
     * @param fontSize 字体大小
     * @return label
     */
    public static JLabel createLabel(String text, int horizontalAlignment, int fontSize) {
        JLabel jLabel = new JLabel(text, horizontalAlignment);
        return FontMethodsImpl.setFontSize(jLabel, fontSize);
    }



    /**
     * 创建一个Label
     * @param text 文字
     * @param horizontalAlignment 对齐方式
     * @param fontName 字体名字
     * @return label
     */
    public static JLabel createLabel(String text, int horizontalAlignment, String fontName) {
        JLabel jLabel = new JLabel(text, horizontalAlignment);
        return FontMethodsImpl.setFontName(jLabel, fontName);
    }



    /**
     * 创建一个Label
     * @param text 文字
     * @param verticalTextPosition 对齐方式
     * @param icon 图标
     * @return label
     */
    public static JLabel createLabel(String text, int verticalTextPosition, int horizontalTextPosition, int horizontalAlignment, Icon icon) {
        JLabel jLabel = new JLabel(text, icon, horizontalAlignment);
        jLabel.setVerticalTextPosition(verticalTextPosition);
        jLabel.setHorizontalTextPosition(horizontalTextPosition);
        return jLabel;
    }



    /**
     * 创建按钮
     * @param text 文字
     * @param icon 图标
     * @param styleId 样式
     * @return 按钮
     */
    public static JButton createButton(String text, Icon icon, StyleId styleId){
        JButton jButton = new JButton(text,icon);
        jButton.putClientProperty(StyleId.STYLE_PROPERTY, styleId);
        return jButton;
    }


    /**
     * 创建按钮
     * @param text 文字
     * @param icon 图标
     * @param styleId 样式
     * @return 按钮
     */
    public static JButton createButton(String text, Icon icon, StyleId styleId, ActionListener listener){
        JButton jButton = new JButton(text,icon);
        jButton.putClientProperty(StyleId.STYLE_PROPERTY, styleId);
        jButton.addActionListener(listener);
        return jButton;
    }


    /**
     * 创建按钮
     * @param text 文字
     * @param icon 图标
     * @param styleId 样式
     * @return 按钮
     */
    public static JButton createButton(String text, Icon icon, StyleId styleId, ActionListener listener,  Font font){
        JButton jButton = createButton(text, icon, styleId, listener);
        jButton.setFont(font);
        return jButton;
    }


    /**
     * 创建输入框
     * @param column 宽度
     * @param styleId 样式
     * @return 输入框
     */
    public static JTextField createTextField(int column, StyleId styleId) {
        return createTextField(column, styleId, null);
    }



    /**
     * 创建输入框
     * @param column 宽度
     * @param styleId 样式
     * @return 输入框
     */
    public static JTextField createTextField(int column, StyleId styleId, String fontName, String text) {
        JTextField textField = createTextField(column, styleId, fontName);
        textField.setText(text);
        return textField;
    }


    /**
     * 创建输入框
     * @param column 宽度
     * @param styleId 样式
     * @param toolTip 提示
     * @return 输入框
     */
    public static JTextField createTextField(int column, StyleId styleId, String fontName, String text, String toolTip) {
        JTextField textField = createTextField(column, styleId, fontName, text);
        textField.setToolTipText(toolTip);
        return textField;
    }


    /**
     * 创建输入框
     * @param column 宽度
     * @param styleId 样式
     * @param fontName 字体
     * @return 输入框
     */
    public static JTextField createTextField(int column, StyleId styleId, String fontName) {
        JTextField textField = new JTextField(column);
        if (StringUtils.isNotBlank(fontName)) {
            FontMethodsImpl.setFontName(textField, fontName);
        }
        textField.putClientProperty(StyleId.STYLE_PROPERTY, styleId);
        return textField;
    }




    /**
     *  创建横向的box
     * @param components 要添加的元素
     * @return 横向的box
     */
    public static Box createHorizontalBox(Component... components) {
        Box horizontalBox = Box.createHorizontalBox();
        for (Component component : components) {
            horizontalBox.add(component);
        }
        return horizontalBox;
    }




    /**
     * 创建竖向的box
     * @param components 要添加的元素
     * @return 竖向的box
     */
    public static Box createVerticalBox(Component... components) {
        Box verticalBox = Box.createVerticalBox();
        for (Component component : components) {
            verticalBox.add(component);
        }
        return verticalBox;
    }


    /**
     * 复选框
     * @param text 文字
     * @param selected 是否选中
     * @return 复选框
     */
    public static JCheckBox createCheckBox (String text, boolean selected) {
        JCheckBox checkBox = new JCheckBox(text, selected );
        checkBox.setFont(CatFonts.MICRO_SOFT_13);
        checkBox.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.checkbox);
        return checkBox;
    }


    /**
     * 复选框
     * @param text 文字
     * @param selected 是否选中
     * @param toolTip 提示
     * @return 复选框
     */
    public static JCheckBox createCheckBox (String text, boolean selected, String toolTip) {
        JCheckBox checkBox = createCheckBox(text, selected);
        checkBox.setToolTipText(toolTip);
        return checkBox;
    }

}
