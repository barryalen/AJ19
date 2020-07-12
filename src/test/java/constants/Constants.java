package constants;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    // 获取excel路径
    public static final String EXCEL_PATH = System.getProperty("user.dir")+"/src/test/resources/exam.xlsx";

    // 获取params文件路径
    public static final String PARAMS_PATH = System.getProperty("user.dir")+"/src/test/resources/params.properties";

    // 默认请求头
    public static final Map<String, String> HEADERS = new HashMap<String, String>();

    // 回写响应到Excel列值
    public static final int WRITE_BACK_CELL_NUM_BODY = 12;

    // 回写执行结果到Excel
    public static final int WRITE_BACK_CELL_NUM_EXECUTE_RESULT = 1;

    //连接数据库URL 写法固定，区分数据类型  jdbc:数据库类型://ip:port/数据库名称
    public static final String JDBC_URL = "jdbc:mysql://api.lemonban.com:3306/futureloan?useUnicode=true&characterEncoding=utf-8";
    //数据库登录用户
    public static final String JDBC_USERNAME = "future";
    //数据库登录密码
    public static final String JDBC_PASSWORD = "123456";

//    public static void main(String[] args) throws InterruptedException {
//        System.out.println(EXCEL_PATH);
//        String filepath = System.getProperty("user.dir");
//        System.out.println(filepath);
//    }

}
