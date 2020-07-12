package utils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SQLUtils {
    public static Logger logger = Logger.getLogger(SQLUtils.class);
    public static void main(String[] args) {
        String sql = "SELECT m.leave_amount FROM member m where m.mobile_phone='18910012200'";
        Object o = singleResult(sql);
        System.out.println(o.getClass());
    }

    /**
     * 返回查询结果，单个值
     * @param sql
     * @return
     */
    public static Object singleResult(String sql){
        logger.info("============singleResult==" + sql);
        Object o = null;
        QueryRunner qr = new QueryRunner();
        Connection conn = JDBCUtils.getConnection();
        //String sql = "select * from member a where a.mobile_phone = '18900012000' ";
        try {
            // ScalarHandle 返回第一行第一列数据
            o = qr.query(conn, sql, new ScalarHandler<Object>());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtils.close(conn);
        }
        return o;
    }

    /**
     * 返回查询结果集
     * @param sql
     * @return
     */
    public static List map(String sql){
        List<Map<String, Object>> mapResult = null;
        QueryRunner qr = new QueryRunner();
        Connection conn = JDBCUtils.getConnection();
        try {
            // MapHandler 将结果集第一条数据封装到map中，key是字段名，value是字段值，返回map对象
            //Map<String, Object> map = qr.query(conn, sql, new MapHandler());
            // MapListHandler 将查询结果每一条记录封装到map中，key是字段名，value是字段值，并封装到List集合中，返回集合对象
            mapResult = qr.query(conn, sql, new MapListHandler());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtils.close(conn);
        }
        return mapResult;
    }

    /**
     * 打印查询结果
     * @param list  map的list集合
     */
    public static void printMapResult(List<Map> list){
        // 打印查询结果,map的list集合
            for (Map map : list) {
                Set<String> strings = map.keySet();
                for (String string : strings) {
                    System.out.print(string + "=" + map.get(string));
                }
                System.out.println();
            }
    }
}
