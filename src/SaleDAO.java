import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;
import javax.swing.*;

public class SaleDAO {
    public boolean addSale(Sale sale) {
        Connection conn = DBUtil.getConn();
        PreparedStatement pstmt = null;
        try {
            conn.setAutoCommit(false);

            // 插入销售主表
            String sqlMain = "INSERT INTO sale(cid,total_money) VALUES (?,?)";
            pstmt = conn.prepareStatement(sqlMain, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, sale.getCid());
            pstmt.setBigDecimal(2, sale.getTotalMoney());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            int sid = 0;
            if (rs.next()) {
                sid = rs.getInt(1);
            }
            rs.close();
            pstmt.close();

            // 循环处理明细、校验库存
            for (SaleItem item : sale.getItemList()) {
                // 查询现有库存
                String sqlCheck = "select stock from goods where gid=?";
                pstmt = conn.prepareStatement(sqlCheck);
                pstmt.setInt(1, item.getGid());
                ResultSet rsStock = pstmt.executeQuery();
                int stock = 0;
                if (rsStock.next()) {
                    stock = rsStock.getInt("stock");
                }
                rsStock.close();
                pstmt.close();

                if (stock < item.getNum()) {
                    conn.rollback();
                    JOptionPane.showMessageDialog(null, "库存不足！现有库存：" + stock);
                    return false;
                }

                // 插入销售明细
                String sqlItem = "insert into saleitem(saleid,gid,num,price) values (?,?,?,?)";
                pstmt = conn.prepareStatement(sqlItem);
                pstmt.setInt(1, sid);
                pstmt.setInt(2, item.getGid());
                pstmt.setInt(3, item.getNum());
                pstmt.setBigDecimal(4, item.getPrice());
                pstmt.executeUpdate();
                pstmt.close();

                // 扣减商品库存
                String sqlStock = "update goods set stock = stock - ? where gid=?";
                pstmt = conn.prepareStatement(sqlStock);
                pstmt.setInt(1, item.getNum());
                pstmt.setInt(2, item.getGid());
                pstmt.executeUpdate();
                pstmt.close();
            }
            conn.commit();
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