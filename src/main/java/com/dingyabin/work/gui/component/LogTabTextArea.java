package com.dingyabin.work.gui.component;

import com.alee.utils.swing.extensions.FontMethodsImpl;
import com.dingyabin.work.common.cons.Const;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

import javax.swing.*;
import javax.swing.text.Document;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 丁亚宾
 * Date: 2021/8/11.
 * Time:0:24
 */
@Slf4j
public class LogTabTextArea extends JTextArea {

    private int rows = 0;

    public LogTabTextArea() {
        super();
    }

    public LogTabTextArea(String text) {
        super(text);
    }

    public LogTabTextArea(int rows, int columns) {
        super(rows, columns);
    }

    public LogTabTextArea(String text, int rows, int columns) {
        super(text, rows, columns);
    }

    public LogTabTextArea(Document doc) {
        super(doc);
    }

    public LogTabTextArea(Document doc, String text, int rows, int columns) {
        super(doc, text, rows, columns);
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


    public void showLog(){
        File[] files = new File(Const.CAT_LOG_PATH).listFiles();
        if (files == null) {
            return;
        }
        List<File> infoLogs = new ArrayList<>();
        List<File> errorLogs = new ArrayList<>();
        for (File logFile : files) {
            if (logFile.isDirectory()) {
                continue;
            }
            String name = FilenameUtils.getName(logFile.getAbsolutePath());
            if (name.contains(".info.log")) {
                infoLogs.add(logFile);
            }
            if (name.contains(".error.log")) {
                errorLogs.add(logFile);
            }
        }
        append(Const.INFO_LOGS);
        //读取info日志
        if (!infoLogs.isEmpty()) {
            infoLogs.forEach(this::readLogs);
        }
        append(Const.ERROR_LOGS);
        //读取error日志
        if (!errorLogs.isEmpty()) {
            errorLogs.forEach(this::readLogs);
        }
    }



    private void readLogs(File file) {
        try (FileInputStream inputStream = new FileInputStream(file)) {
            LineIterator lineIterator = IOUtils.lineIterator(inputStream, StandardCharsets.UTF_8);
            while (lineIterator.hasNext()) {
                if (rows > Const.MAX_LOG_ROWS) {
                    append(Const.MAX_LOG_ROWS_TIPS);
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
}
