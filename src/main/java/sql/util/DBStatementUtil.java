package sql.util;

import java.sql.SQLException;
import java.sql.Statement;

public class DBStatementUtil {

    private static volatile Statement statement;

    synchronized public static Statement getStatement() {
        try {
            if (statement == null || statement.isClosed()) {
                statement = DBConnectionUtil.getConnection().createStatement();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return statement;
    }

    synchronized public static void close() {
        try {
            if (!statement.isClosed())
                statement.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

}