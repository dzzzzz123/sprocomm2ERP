package ext.kingdeeERP.supply;

import ext.kingdeeERP.Config;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.List;

public class Service {

    static Logger logger = LogManager.getLogger(Service.class);

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(Config.oracleDriver());
            conn = DriverManager.getConnection(Config.oracleUrl(),
                    Config.oracleUserName(), Config.oraclePassWord());
            if (!conn.isClosed()) {
                System.out.println("数据库已经连接成功！");
            }
        } catch (Exception e) {
            logger.error("连接数据库链接时出错", e);
        }
        return conn;
    }

    public static ResultEntity process(List<Entity> entities) {
        int affectedRows = 0;
        for (Entity entity : entities) {
            affectedRows += addOrUpdate(entity);
        }
        return ResultEntity.success(affectedRows, "操作成功");
    }

    /**
     * 根据查询出的结果来判断该创建或者更新
     *
     * @param entity 实体类
     * @return int 更新/创建是否成功
     */
    public static int addOrUpdate(Entity entity) {
        System.out.println("entity" + entity);
        Entity sqlEntity = select(entity.getNumber());
        System.out.println("sqlEntity" + sqlEntity);
        boolean isUpdate = sqlEntity != null && StringUtils.isNotBlank(sqlEntity.getNumber());
        return (isUpdate ? update(entity) : insert(entity)) == 1 ? 1 : 0;
    }

    private static Entity select(String number) {
        String sql = "SELECT * FROM PDM_ERP_SUPPLIER WHERE FNUMBER = ? ";
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        try {
            Connection connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, number);
            sqlLog(sql, number);
            resultSet = statement.executeQuery();
            Entity entity = new Entity();
            while (resultSet.next()) {
                entity.setNumber(resultSet.getString("FNUMBER"));
                entity.setName(resultSet.getString("FNAME"));
                entity.setEngName(resultSet.getString("FENGNAME"));
                entity.setAcctNO(resultSet.getString("FACCTNO"));
                entity.setStatus(resultSet.getString("FDELETED"));
            }
            return entity;
        } catch (Exception e) {
            logger.error("查询供应商对象操作时出错", e);
        } finally {
            closeResources(statement, resultSet);
        }
        return null;
    }

    private static int insert(Entity entity) {
        String sql = "INSERT INTO PDM_ERP_SUPPLIER (FNUMBER, FNAME, FENGNAME, FACCTNO, FDELETED) VALUES ( ? , ? , ? , ? , ?) ";
        String number = entity.getNumber();
        String name = entity.getName();
        String engName = entity.getEngName();
        String acctNO = Config.DefaultAcctNO();
        String status = entity.getStatus();
        int affectedRows = 0;
        PreparedStatement statement = null;
        try {
            Connection connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, number);
            statement.setString(2, name);
            statement.setString(3, engName);
            statement.setString(4, acctNO);
            statement.setString(5, status);
            sqlLog(sql, number, name, engName, acctNO, status);
            affectedRows = statement.executeUpdate();
            return affectedRows;
        } catch (Exception e) {
            logger.error("插入供应商对象操作时出错", e);
        } finally {
            closeResources(statement, null);
        }
        return affectedRows;
    }

    private static int update(Entity entity) {
        String sql = "UPDATE PDM_ERP_SUPPLIER SET FNAME = ? , FENGNAME = ? , FDELETED = ? WHERE FNUMBER = ?";
        String number = entity.getNumber();
        String name = entity.getName();
        String engName = entity.getEngName();
        String status = entity.getStatus();
        int affectedRows = 0;
        PreparedStatement statement = null;
        try {
            Connection connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, engName);
            statement.setString(3, status);
            statement.setString(4, number);

            sqlLog(sql, name, engName, status, number);
            affectedRows = statement.executeUpdate();
            return affectedRows;
        } catch (Exception e) {
            logger.error("更新供应商对象操作时出错", e);
        } finally {
            closeResources(statement, null);
        }
        return affectedRows;
    }

    /**
     * 打印执行的SQL语句
     *
     * @param sql    SQL语句
     * @param params 参数
     */
    private static void sqlLog(String sql, String... params) {
        // 输出当前执行更新操作的SQL语句
        String fullSql = sql;
        for (String param : params) {
            fullSql = fullSql.replaceFirst("\\?", "\"" + param + "\"");
        }
        System.out.println("--------当前执行插入/更新操作的SQL语句为--------");
        System.out.println(fullSql);
    }

    // 关闭资源的方法
    public static void closeResources(PreparedStatement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            logger.error("关闭数据库资源操作时出错", e);
        }
    }
}
