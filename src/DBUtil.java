import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/cangchu?useSSL=false&serverTimezone=Asia/Shanghai";
    private static final String USER = "root";
    private static final String PWD = "hyx060517";

    public static Connection getConn() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PWD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    // 关闭：连接+预编译语句（增删改）
    public static void closeConn(Connection conn, PreparedStatement pstmt) {
        closeConn(conn, pstmt, null);
    }

    // 关闭全套：连接+预编译+结果集（查询）
    public static void closeConn(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}