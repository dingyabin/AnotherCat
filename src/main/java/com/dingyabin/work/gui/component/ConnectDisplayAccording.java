package com.dingyabin.work.gui.component;

import com.alee.api.data.BoxOrientation;
import com.alee.extended.accordion.AccordionPane;
import com.alee.extended.accordion.AccordionPaneListener;
import com.alee.extended.accordion.WebAccordion;
import com.alee.managers.style.StyleId;
import com.alee.utils.swing.extensions.FontMethodsImpl;
import com.dingyabin.work.common.cons.Const;
import com.dingyabin.work.common.model.CatRet;
import com.dingyabin.work.common.model.ConnectConfig;
import com.dingyabin.work.common.model.DataBaseSchema;
import com.dingyabin.work.ctrl.adapter.CatAdapterService;
import com.dingyabin.work.ctrl.config.ExecutorUtils;
import com.dingyabin.work.gui.utils.GuiUtils;
import org.apache.commons.collections4.CollectionUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;

/**
 * @author 丁亚宾
 * Date: 2021/8/15.
 * Time:13:00
 */
public class ConnectDisplayAccording extends WebAccordion implements AccordionPaneListener, ActionListener {


    private Set<ConnectConfig> connectConfigs;

    private JFrame jFrame;

    private JMenuItem see = new JMenuItem("查看连接", CatIcons.watch);

    private JMenuItem edit = new JMenuItem("编辑连接", CatIcons.edit);

    private JMenuItem delete = new JMenuItem("删除连接", CatIcons.delete);

    private JPopupMenu jPopupMenu = new JPopupMenu();

    private CatAdapterService catAdapterService;



    public ConnectDisplayAccording(JFrame jFrame) {
        super(StyleId.accordion, BoxOrientation.top, 0, 1);
        this.jFrame = jFrame;
        initPopupMenu();
    }


    @Override
    public AccordionPane addPane(Icon icon, String title, Component content) {
        AccordionPane accordionPane = super.addPane(icon, title, content);
        accordionPane.addAccordionPaneListener(this);
        JComponent header = accordionPane.getHeader();
        FontMethodsImpl.setFontSize(header.getComponent(0), 13);
        FontMethodsImpl.setFontName(header.getComponent(0), "微软雅黑");
        header.setComponentPopupMenu(jPopupMenu);
        collapsePane(accordionPane.getId());
        return accordionPane;
    }



    public void setConnectConfigs(Set<ConnectConfig> connectConfigs) {
        this.connectConfigs = connectConfigs;
        if (CollectionUtils.isEmpty(connectConfigs)) {
            return;
        }
        connectConfigs.forEach(connect -> {
            AccordionPane accordionPane = addPane(CatIcons.dbcon, connect.getName(), null);
            accordionPane.putClientProperty(Const.ACCORDING_META, connect);
            accordionPane.putClientProperty(Const.ACCORDING_LOAD, Boolean.FALSE);
        });
    }


    @Override
    public void expanding(WebAccordion accordion, AccordionPane pane) {
        Object loaded = pane.getClientProperty(Const.ACCORDING_LOAD);
        //已经加载过了
        if (Boolean.TRUE.equals(loaded)) {
            return;
        }
        Object conMeta = pane.getClientProperty(Const.ACCORDING_META);
        ConnectConfig connectConfig = (ConnectConfig) conMeta;

        ExecutorUtils.execute(()-> {
        //查询对应的数据库
            CatRet<List<DataBaseSchema>> catRet = catAdapterService.getDbsWithConnect(connectConfig);
            if (!catRet.isSuccess()) {
                GuiUtils.createOptionPane(jFrame, catRet.getMsg(), JOptionPane.DEFAULT_OPTION);
                return;
            }
            SwingUtilities.invokeLater(() -> {
                pane.setContent(new JList<>(catRet.getData().toArray()));
                pane.putClientProperty(Const.ACCORDING_LOAD, Boolean.TRUE);
            });
        });
    }

    @Override
    public void expanded(WebAccordion accordion, AccordionPane pane) {

    }

    @Override
    public void collapsing(WebAccordion accordion, AccordionPane pane) {

    }

    @Override
    public void collapsed(WebAccordion accordion, AccordionPane pane) {

    }


    public void setCatAdapterService(CatAdapterService catAdapterService) {
        this.catAdapterService = catAdapterService;
    }


    public Set<ConnectConfig> getConnectConfigs() {
        return connectConfigs;
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == see) {

        }
        if (e.getSource() == edit) {

        }
        if (e.getSource() == delete) {

        }
    }



    private void initPopupMenu() {
        see.addActionListener(this);
        edit.addActionListener(this);
        delete.addActionListener(this);

        jPopupMenu.add(see);
        jPopupMenu.add(edit);
        jPopupMenu.addSeparator();
        jPopupMenu.add(delete);
    }

}
