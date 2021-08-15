package com.dingyabin.work.common.cons;

import java.io.File;

/**
 * @author 丁亚宾
 * Date: 2021/8/7.
 * Time:11:00
 */
public class Const {

    public static final String BASE_PATH = System.getProperty("user.home") + File.separator + "anotherCat" + File.separator;

    public static final String CONNECT_CONFIG_FILE = BASE_PATH + "db.dat";

    public static final String CAT_LOG_PATH = BASE_PATH + "log";

    public static  final String GIT_ADDRESS = "https://github.com/dingyabin/AnotherCat";

    public static  final String ABOUT = "<html>&nbsp;&nbsp;&nbsp;AnotherCat是一个基于Java Swing的开源数据库连接工具客户端。体积轻盈，没有繁杂的功能，欢迎下载使用，并提出宝贵意见！<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; —— 作者：Mr.丁<br/><br/></html>";

    public static  final int MAX_LOG_ROWS = 500;

    public static  final String MAX_LOG_ROWS_TIPS = "日志已经超过"+MAX_LOG_ROWS+"行，不再显示";

    public static  final String INFO_LOGS = "-----------------------以下是INFO日志----------------------\n";

    public static  final String ERROR_LOGS = "-----------------------以下是ERROR日志-----------------------\n";

    public static  final String INFO_LOG_KEY = "INFO";

    public static  final String ERROR_LOG_KEY = "ERROR";

    public static  final String CONFIRM_TO_CLEAR_LOG = "确认要清空日志么?";

    public static  final String ACCORDING_META = "accordionPane.connect.meta";

    public static  final String ACCORDING_LOAD = "accordionPane.connect.load";




}
