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

    public static  final String ABOUT = "<html>&nbsp;&nbsp;&nbsp;AnotherCat是一个基于Java Swing<br/>的开源数据库连接工具客户端。体积轻盈，<br/>没有繁杂的功能。<br/>&nbsp;&nbsp;&nbsp;欢迎下载使用，并提出宝贵意见！<br/><br/><br/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;—— 作者：丁同学<br/><br/></html>";

    public static  final int MAX_LOG_ROWS = 500;

    public static  final String MAX_LOG_ROWS_TIPS = "日志已经超过"+MAX_LOG_ROWS+"行，不再显示\n";

    public static  final String INFO_LOGS = "-----------------------以下是INFO日志----------------------\n";

    public static  final String ERROR_LOGS = "-----------------------以下是ERROR日志-----------------------\n";

    public static  final String INFO_LOG_KEY = "INFO";

    public static  final String ERROR_LOG_KEY = "ERROR";

    public static  final String CONFIRM_TO_CLEAR_LOG = "确认要清空日志么?";

    public static  final String ACCORDING_META = "accordionPane.connect.meta";

    public static  final String ACCORDING_LOAD = "accordionPane.connect.load";

    public static  final String ACCORDING_PANE_ID = "accordionPane.connect.paneId";

    public static  final String JLIST_CURRENT_SELECTED_INDEX = "jlist.selected.index";

    public static final String GENERATOR_DEFAULT_CODE_PATH = "src" + File.separator + "main" + File.separator + "java";

    public static final String GENERATOR_DEFAULT_RESOURCES = "src" + File.separator + "main" + File.separator + "resources";


    public static final String ADD_REMARK_COMMENTS = "是否把表和字段的注释加到javaModel里";

    public static final String FORCE_BIG_DECIMALS = "<html>对于数据库里的DECIMAL 和 NUMERIC 字段，<br/> javaModel是否强制使用BigDecimal与之对应，<br/> 选中表示是，否则的话则智能选择Long或者Integer、Short</html>";

    public static final String USE_DATE_IN_MODEL = "<html>对于数据库里的DATE, TIME , TIMESTAMP字段，<br/> Model是否使用LocalDate或者LocalDateTime与之对应 <br/> 选中表示是，否则的话则统一使用java.util.Date </html>";

    public static final String DAO_RENAME_DESC = "DAO的命名规则是Model类名+后缀，例如: Model名字是Student，后缀是Mapper，则DAO名字是: StudentMapper";


}
