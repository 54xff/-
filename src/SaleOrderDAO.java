import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SaleOrderDAO {
    public boolean addSaleOrder(SaleOrder order) {
        Connection conn = DBUtil.getConn();
        PreparedStatement pstmtSale = null;
        PreparedStatement pstmtCheck = null;
        PreparedStatement pstmtUpdateStock = null;
        try {
            conn.setAutoCommit(false);
            for (SaleItem item : order.getItemList()) {
                //先校验库存是否足够
                String sqlCheck = "SELECT stock FROM goods WHERE gid=?";
                pstmtCheck = conn.prepareStatement(sqlCheck);
                pstmtCheck.setInt(1, item.getGid());
                ResultSet rs = pstmtCheck.executeQuery();
                if(rs.next()){
                    int stock = rs.getInt("stock");
                    if(stock < item.getNum()){
                        conn.rollback();
                        return false; //库存不足
                    }
                }
                rs.close();

                //插入销售记录
                String sql1 = "INSERT INTO sale(gid,sale_num,sale_price,cid) VALUES (?,?,?,?)";
                pstmtSale = conn.prepareStatement(sql1);
                pstmtSale.setInt(1, item.getGid());
                pstmtSale.setInt(2, item.getNum());
                pstmtSale.setBigDecimal(3, item.getPrice());
                pstmtSale.setInt(4, order.getCid());
                pstmtSale.executeUpdate();

                //扣减库存
                String sql2 = "UPDATE goods SET stock = stock - ? WHERE gid=?";
                pstmtUpdateStock = conn.prepareStatement(sql2);
                pstmtUpdateStock.setInt(1, item.getNum());
                pstmtUpdateStock.setInt(2, item.getGid());
                pstmtUpdateStock.executeUpdate();
            }
            conn.commit();
            StockEventManager.notifyStockChange();
            return true;
        } catch (SQLException e) {
            try {
                if(conn != null) conn.rollback();
            } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.closeConn(conn, pstmtSale);
            DBUtil.closeConn(null, pstmtCheck);
            DBUtil.closeConn(null, pstmtUpdateStock);
        }
    }
}