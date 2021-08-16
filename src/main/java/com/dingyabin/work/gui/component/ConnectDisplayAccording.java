package com.dingyabin.work.gui.component;

import com.alee.api.annotations.NotNull;
import com.alee.api.annotations.Nullable;
import com.alee.api.data.BoxOrientation;
import com.alee.extended.accordion.AccordionPane;
import com.alee.extended.accordion.AccordionPaneListener;
import com.alee.extended.accordion.WebAccordion;
import com.alee.managers.style.StyleId;
import com.alee.utils.swing.extensions.FontMethodsImpl;
import com.dingyabin.work.common.cons.Const;
import com.dingyabin.work.common.listeners.SaveConnectListener;
import com.dingyabin.work.common.model.*;
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
public class ConnectDisplayAccording extends WebAccordion implements AccordionPaneListener, ActionListener, SaveConnectListener {

    private JFrame jFrame;

    private JTabbedPane tabbedPane;

    private JMenuItem see = new JMenuItem("查看连接", CatIcons.watch);

    private JMenuItem edit = new JMenuItem("编辑连接", CatIcons.edit);

    private JMenuItem delete = new JMenuItem("删除连接", CatIcons.delete);

    private JPopupMenu jPopupMenu = new JPopupMenu();

    private CatAdapterService catAdapterService;



    public ConnectDisplayAccording(JFrame jFrame, JTabbedPane tabbedPane) {
        super(StyleId.accordion, BoxOrientation.top, 0, 1);
        this.jFrame = jFrame;
        this.tabbedPane = tabbedPane;
        initPopupMenu();
    }


    @Override
    public AccordionPane addPane(Icon icon, String title, Component content) {
        AccordionPane accordionPane = addPane(createAccordionPane(icon, title, content));
        //默认收起
        collapsePane(accordionPane.getId());
        return accordionPane;
    }




    public void setConnectConfigs(Set<ConnectConfig> connectConfigs) {
        if (CollectionUtils.isEmpty(connectConfigs)) {
            return;
        }
        connectConfigs.forEach(connect -> {
            AccordionPane accordionPane = addPane(CatIcons.dbcon, connect.getName(), null);
            accordionPane.putClientProperty(Const.ACCORDING_META, connect);
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




    @Override
    public void expanded(WebAccordion accordion, AccordionPane pane) {

    }

    @Override
    public void collapsing(WebAccordion accordion, AccordionPane pane) {

    }

    @Override
    public void collapsed(WebAccordion accordion, AccordionPane pane) {

    }



    @Override
    public void onSaveFinish(SaveConnectEvent saveConnectEvent) {
        CatNewConModel catNewConModel = saveConnectEvent.getCatNewConModel();
        if (catNewConModel.isEditMode()) {
            return;
        }
        ConnectConfig connect = saveConnectEvent.getSavedConnectConfig();
        AccordionPane accordionPane = addPane(CatIcons.dbcon, connect.getName(), null);
        accordionPane.putClientProperty(Const.ACCORDING_META, connect);
    }





    public void setCatAdapterService(CatAdapterService catAdapterService) {
        this.catAdapterService = catAdapterService;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (!(source instanceof JMenuItem)) {
            return;
        }
        //AccordionPane 的id
        Object accordionPaneId = ((JMenuItem) source).getClientProperty(Const.ACCORDING_PANE_ID);
        if (accordionPaneId == null) {
            return;
        }
        //获取AccordionPane
        AccordionPane accordionPane = getPane(accordionPaneId.toString());
        if (accordionPane == null) {
            return;
        }
        //获取连接信息
        Object conMeta = accordionPane.getClientProperty(Const.ACCORDING_META);
        if (!(conMeta instanceof ConnectConfig)) {
            return;
        }
        ConnectConfig connectConfig = ((ConnectConfig) conMeta).copy();
        CatNewConnectDialog dialog = new CatNewConnectDialog(jFrame, connectConfig.typeEnum(), e.getActionCommand(), false);
        //设置编辑模式
        dialog.editMode(connectConfig);

        //查看操作
        if (source == see) {
            dialog.enAbleInput(false);
            dialog.showSelf();
        }

        //删除操作
        if (source == delete) {
            dialog.enAbleInput(false);
            dialog.showSelf();
            //删除连接
            boolean deleteRet = GuiUtils.createYesNoOptionPane(dialog, "确定要删除么") && removeConnectConfig(connectConfig);
            if (deleteRet) {
                removePane(accordionPane);
            }
            //关闭弹窗
            dialog.dispose();
        }


        //编辑操作
        if (source == edit) {
            dialog.addSaveConnectListener(saveConnectEvent -> {
                CatNewConModel catNewConModel = saveConnectEvent.getCatNewConModel();
                //保存-新建模式下，不处理
                if (catNewConModel.isSaveMode()) {
                    return;
                }
                //只处理编辑模式
                ConnectConfig savedConfig = saveConnectEvent.getSavedConnectConfig();
                //如果没有变化，不处理
                if (savedConfig.equals(catNewConModel.getOldConnectConfig())) {
                    return;
                }
                AccordionPane newAccordionPane = createAccordionPane(CatIcons.dbcon, savedConfig.getName(), null);
                newAccordionPane.putClientProperty(Const.ACCORDING_META, savedConfig);
                replaceAccordionPane(accordionPane, newAccordionPane);
            });
            dialog.showSelf();
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
                    see.putClientProperty(Const.ACCORDING_PANE_ID, accordionPane.getId());
                    edit.putClientProperty(Const.ACCORDING_PANE_ID, accordionPane.getId());
                    delete.putClientProperty(Const.ACCORDING_PANE_ID, accordionPane.getId());
                    jPopupMenu.show(component, e.getX(), e.getY());
                }
            }
        });
    }


    protected AccordionPane createAccordionPane(@Nullable final Icon icon, @Nullable final String title, @NotNull final Component content) {
        String accordionPaneId = createAccordionPaneId();
        AccordionPane accordionPane = super.createAccordionPane(accordionPaneId, icon, title, content);
        //标记
        accordionPane.putClientProperty(Const.ACCORDING_LOAD, Boolean.FALSE);
        //监听,展开的时候做一些操作
        accordionPane.addAccordionPaneListener(this);
        //设置字体
        JComponent header = accordionPane.getHeader();
        FontMethodsImpl.setFontSize(header.getComponent(0), 13);
        FontMethodsImpl.setFontName(header.getComponent(0), "微软雅黑");
        //添加右键菜单
        addPopupMenu(accordionPane, header);
        return accordionPane;
    }



    public AccordionPane replaceAccordionPane(AccordionPane oldAccordionPane, AccordionPane newAccordionPane) {
        int paneIndex = getPaneIndex(oldAccordionPane.getId());
        if (paneIndex < 0) {
            return oldAccordionPane;
        }
        //先删除老的pane
        removePane(oldAccordionPane);
        //再添加一个新的pane
        addPane(paneIndex, newAccordionPane);

        return newAccordionPane;
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

            tabbedPane.addTab("表", CatIcons.table, jscrollPane);
            int index = tabbedPane.indexOfComponent(jscrollPane);
            tabbedPane.setTabComponentAt(index, GuiUtils.createTabBarComponent("表", CatIcons.table, tabbedPane, jscrollPane));
        });
        return schemaCatList;
    }

}
