import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.math.BigDecimal;

public class PurchaseOrderDAO {
    public boolean addPurchaseOrder(PurchaseOrder order) {
        Connection conn = DBUtil.getConn();
        PreparedStatement pstmt = null;
        try {
            conn.setAutoCommit(false);

            String sqlMain = "INSERT INTO purchase(sid, ptime, total_money) VALUES (?,now(),?)";
            pstmt = conn.prepareStatement(sqlMain, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, order.getSid());
            pstmt.setBigDecimal(2, order.getTotalMoney());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            int pid = 0;
            if (rs.next()) {
                pid = rs.getInt(1);
            }
            rs.close();
            pstmt.close();


            for (PurchaseItem item : order.getItemList()) {
                String sqlItem = "INSERT INTO purchaseitem(pid,gid,num,price) VALUES (?,?,?,?)";
                pstmt = conn.prepareStatement(sqlItem);
                pstmt.setInt(1, pid);
                pstmt.setInt(2, item.getGid());
                pstmt.setInt(3, item.getNum());
                pstmt.setBigDecimal(4, item.getPrice());
                pstmt.executeUpdate();
                pstmt.close();

                String sqlUpdateStock = "UPDATE goods SET stock = stock + ? WHERE gid=?";
                pstmt = conn.prepareStatement(sqlUpdateStock);
                pstmt.setInt(1, item.getNum());
                pstmt.setInt(2, item.getGid());
                pstmt.executeUpdate();
                pstmt.close();
            }

            conn.commit();
            StockEventManager.notifyStockChange();
            return true;
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.closeConn(conn, pstmt);
        }
    }
}