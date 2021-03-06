package com.dingyabin.work.gui.component;

import com.alee.utils.swing.extensions.FontMethodsImpl;
import com.dingyabin.work.common.cons.Const;
import com.dingyabin.work.common.utils.CatUtils;
import com.dingyabin.work.ctrl.config.ExecutorUtils;
import com.dingyabin.work.gui.utils.GuiUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @author 丁亚宾
 * Date: 2021/8/11.
 * Time:0:24
 */
@Slf4j
public class LogTabTextArea extends JTextArea {

    private int rows = 0;

    private final ActionListener copyActionListener = e -> copy();

    private final ActionListener selectActionListener = e -> selectAll();

    private final ActionListener clearActionListener = e -> {
        if (GuiUtils.createYesNoOptionPane(LogTabTextArea.this, Const.CONFIRM_TO_CLEAR_LOG) && clearLogFile()) {
            setText(StringUtils.EMPTY);
            rows = 0;
        }
    };


    public LogTabTextArea() {
        super();
    }



    public LogTabTextArea(int rows, int columns) {
        super(rows, columns);
    }


    public LogTabTextArea lineWrap(boolean wrap) {
        super.setLineWrap(wrap);
        return this;
    }


    public LogTabTextArea fontSize(int fontSize) {
        FontMethodsImpl.setFontSize(this, fontSize);
        return this;
    }

    public LogTabTextArea fontName(String fontName) {
        FontMethodsImpl.setFontName(this, fontName);
        return this;
    }

    /**
     * 禁止编辑
     * @return self
     */
    public LogTabTextArea noEdit(){
        setEditable(false);
        return this;
    }


    /**
     * 展示日志
     */
    public void showLog() {
        //读取文件是耗时操作，异步进行
        ExecutorUtils.execute(() -> {
            Map<String, List<File>> logFileMap = CatUtils.getLogFileMap(Const.CAT_LOG_PATH);
            List<File> infoLogs = logFileMap.get(Const.INFO_LOG_KEY);
            List<File> errorLogs = logFileMap.get(Const.ERROR_LOG_KEY);
            //修改组件UI需要在事件线程里执行
            SwingUtilities.invokeLater(() -> {
                append(Const.INFO_LOGS);
                //读取info日志
                if (CollectionUtils.isNotEmpty(infoLogs)) {
                    infoLogs.forEach(LogTabTextArea.this::readLogs);
                }
                append(Const.ERROR_LOGS);
                //读取error日志
                if (CollectionUtils.isNotEmpty(errorLogs)) {
                    errorLogs.forEach(LogTabTextArea.this::readLogs);
                }
                //回到顶部
                LogTabTextArea.this.setCaretPosition(0);
            });
        });
    }



    private void readLogs(File file) {
        try (FileInputStream inputStream = new FileInputStream(file)) {
            LineIterator lineIterator = IOUtils.lineIterator(inputStream, StandardCharsets.UTF_8);
            while (lineIterator.hasNext()) {
                if (rows == Const.MAX_LOG_ROWS) {
                    append(Const.MAX_LOG_ROWS_TIPS);
                    rows++;
                    return;
                }
                if (rows > Const.MAX_LOG_ROWS) {
                    return;
                }
                String next = lineIterator.next();
                append(next);
                append("\n");
                rows++;
            }
        } catch (Exception e) {
            log.error("读取日志异常, path={}", file.getPath(), e);
        }
    }


    /**
     * 清空日志
     * @return 结果
     */
    private boolean clearLogFile() {
        Map<String, List<File>> logFileMap = CatUtils.getLogFileMap(Const.CAT_LOG_PATH);
        List<File> infoLogs = logFileMap.get(Const.INFO_LOG_KEY);
        List<File> errorLogs = logFileMap.get(Const.ERROR_LOG_KEY);
        //清空info日志
        if (CollectionUtils.isNotEmpty(infoLogs)) {
            infoLogs.forEach(CatUtils::clearFile);
        }
        //清空error日志
        if (CollectionUtils.isNotEmpty(errorLogs)) {
            errorLogs.forEach(CatUtils::clearFile);
        }
        return true;
    }



    /**
     * 添加右键菜单
     */
    public LogTabTextArea addPopupMenu() {
        JPopupMenu jPopupMenu = new JPopupMenu();

        JMenuItem selectItem = new JMenuItem("全选", CatIcons.selectAll);
        selectItem.addActionListener(selectActionListener);

        JMenuItem copyItem = new JMenuItem("复制", CatIcons.copy);
        copyItem.addActionListener(copyActionListener);

        JMenuItem clearItem = new JMenuItem("清空日志", CatIcons.clear);
        clearItem.addActionListener(clearActionListener);

        jPopupMenu.add(selectItem);
        jPopupMenu.add(copyItem);
        jPopupMenu.addSeparator();
        jPopupMenu.add(clearItem);

        //设置弹出框
        setComponentPopupMenu(jPopupMenu);
        return this;
    }


}
