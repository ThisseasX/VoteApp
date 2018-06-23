package utils;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBUtils {

    public static Connection getConnection() throws SQLException, NamingException {
        return ((DataSource) InitialContext
                .doLookup("java:/comp/env/jdbc/VoteApp"))
                .getConnection();
    }
}
