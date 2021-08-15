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
import com.dingyabin.work.common.model.TableSchema;
import com.dingyabin.work.ctrl.adapter.CatAdapterService;
import com.dingyabin.work.ctrl.config.ExecutorUtils;
import com.dingyabin.work.gui.utils.GuiUtils;
import org.apache.commons.collections4.CollectionUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Set;

import static com.dingyabin.work.common.model.ConnectConfigManager.removeConnectConfig;

/**
 * @author 丁亚宾
 * Date: 2021/8/15.
 * Time:13:00
 */
public class ConnectDisplayAccording extends WebAccordion implements AccordionPaneListener, ActionListener {


    private Set<ConnectConfig> connectConfigs;

    private JFrame jFrame;

    private JTabbedPane tabbedPane;

    private JMenuItem see = new JMenuItem("查看连接", CatIcons.watch);

    private JMenuItem edit = new JMenuItem("编辑连接", CatIcons.edit);

    private JMenuItem delete = new JMenuItem("删除连接", CatIcons.delete);

    private JPopupMenu jPopupMenu = new JPopupMenu();

    private CatAdapterService catAdapterService;



    public ConnectDisplayAccording(JFrame jFrame,JTabbedPane tabbedPane) {
        super(StyleId.accordion, BoxOrientation.top, 0, 1);
        this.jFrame = jFrame;
        this.tabbedPane = tabbedPane;
        initPopupMenu();
    }


    @Override
    public AccordionPane addPane(Icon icon, String title, Component content) {
        AccordionPane accordionPane = super.addPane(icon, title, content);
        //监听,展开的时候做一些操作
        accordionPane.addAccordionPaneListener(this);
        //设置字体
        JComponent header = accordionPane.getHeader();
        FontMethodsImpl.setFontSize(header.getComponent(0), 13);
        FontMethodsImpl.setFontName(header.getComponent(0), "微软雅黑");
        //添加右键菜单
        addPopupMenu(accordionPane, header);
        //默认收起
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
            if (CollectionUtils.isEmpty(catRet.getData())) {
                GuiUtils.createOptionPane(jFrame, "该连接下暂无数据库!", JOptionPane.DEFAULT_OPTION);
                return;
            }
            JList<DataBaseSchema> dataBaseList = getDataBaseList(catRet, connectConfig);
            SwingUtilities.invokeLater(() -> {
                pane.setContent(GuiUtils.createJscrollPane(dataBaseList));
                //标记已经加载过了
                pane.putClientProperty(Const.ACCORDING_LOAD, Boolean.TRUE);
            });
        });
    }



    private JList<DataBaseSchema> getDataBaseList(CatRet<List<DataBaseSchema>> catRet, ConnectConfig connectConfig) {
        //数据库列表
        CatList<DataBaseSchema> schemaCatList = new CatList<>(CatIcons.db, catRet.getData()).fontSize(14).fontName("Consolas");
        //双击打开
        schemaCatList.addDoubleClickListener(mouseEvent -> {
            //当前选中的数据库
            DataBaseSchema dataBaseSchema = schemaCatList.getSelectedValue();
            //查询这个库下面的表
            List<TableSchema> tables = catAdapterService.getTablesWithDb(connectConfig, dataBaseSchema);
            //组装List
            CatList<TableSchema> tableCatList = new CatList<>(CatIcons.table, tables).fontSize(14).fontName("Consolas").layoutVW().visCount(0);
            //用JScrollPane包装一下
            JScrollPane jscrollPane = GuiUtils.createJscrollPane(tableCatList);
            //总是放在第一个位置
            if (tabbedPane.getTabCount() == 0) {
                tabbedPane.addTab("表", jscrollPane);
                tabbedPane.setTabComponentAt(0, GuiUtils.createTabBarComponent("表", CatIcons.table, tabbedPane, 0));
            } else {
                tabbedPane.setComponentAt(0, jscrollPane);
            }
        });
        return schemaCatList;
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
        Object source = e.getSource();
        if (!(source instanceof JMenuItem)) {
            return;
        }
        Object conMeta = ((JMenuItem) source).getClientProperty(Const.ACCORDING_META);
        if (!(conMeta instanceof ConnectConfig)) {
            return;
        }
        ConnectConfig connectConfig = (ConnectConfig) conMeta;
        CatNewConnectDialog dialog = new CatNewConnectDialog(jFrame, connectConfig.typeEnum(), e.getActionCommand(), false);
        dialog.setConnectMeta(connectConfig);
        if (source == see) {
            dialog.enAbleInput(false);
            dialog.showSelf();
        }
        if (source == edit) {
            dialog.showSelf();
        }
        if (source == delete) {
            dialog.enAbleInput(false);
            dialog.showSelf();

            //删除连接
            boolean deleteRet = GuiUtils.createYesNoOptionPane(dialog, "确定要删除么") && removeConnectConfig(connectConfig);
            GuiUtils.createOptionPane(dialog, deleteRet?"删除成功!":"删除失败，请稍后再试!", JOptionPane.DEFAULT_OPTION);

            //关闭弹窗
            dialog.dispose();
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



    /**
     * 添加右键菜单
     * @param accordionPane accordionPane
     * @param component 需要添加菜单的组件
     */
    private void addPopupMenu(AccordionPane accordionPane, JComponent component) {
        //右键菜单
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    Object connectMeta = accordionPane.getClientProperty(Const.ACCORDING_META);
                    see.putClientProperty(Const.ACCORDING_META, connectMeta);
                    edit.putClientProperty(Const.ACCORDING_META, connectMeta);
                    delete.putClientProperty(Const.ACCORDING_META, connectMeta);
                    jPopupMenu.show(component, e.getX(), e.getY());
                }
            }
        });
    }

}
