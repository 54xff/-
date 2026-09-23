import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.math.BigDecimal;

public class GoodsDAO {

    // 根据id查询商品
    public Goods findById(int gid) {
        Connection conn = DBUtil.getConn();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Goods g = null;
        String sql = "SELECT * FROM goods WHERE gid=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, gid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                g = new Goods();
                g.setGid(rs.getInt("gid"));
                g.setGname(rs.getString("gname"));
                g.setSpec(rs.getString("spec"));
                g.setStock(rs.getInt("stock"));
                g.setMinStock(rs.getInt("min_stock"));
                g.setPriceIn(rs.getBigDecimal("price_in"));
                g.setGimg(rs.getString("gimg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConn(conn, pstmt, rs);
        }
        return g;
    }

    // 根据商品编号删除商品
    public int delete(int gid){
        Connection conn = DBUtil.getConn();
        PreparedStatement pstmt = null;
        String sql = "DELETE FROM goods WHERE gid=?";
        int rows = 0;
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, gid);
            rows = pstmt.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            DBUtil.closeConn(conn,pstmt);
        }
        return rows;
    }

    // 新增商品
    public int add(Goods goods) {
        Connection conn = DBUtil.getConn();
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO goods(gname,spec,stock,min_stock,price_in,gimg) VALUES (?,?,?,?,?,?)";
        int rows = 0;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, goods.getGname());
            pstmt.setString(2, goods.getSpec());
            pstmt.setInt(3, goods.getStock());
            pstmt.setInt(4, goods.getMinStock());
            pstmt.setBigDecimal(5, goods.getPriceIn());
            pstmt.setString(6, goods.getGimg());
            rows = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConn(conn, pstmt);
        }
        return rows;
    }

    // 修改商品
    public int update(Goods goods) {
        Connection conn = DBUtil.getConn();
        PreparedStatement pstmt = null;
        String sql = "UPDATE goods SET gname=?,spec=?,stock=?,min_stock=?,price_in=?,gimg=? WHERE gid=?";
        int rows = 0;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, goods.getGname());
            pstmt.setString(2, goods.getSpec());
            pstmt.setInt(3, goods.getStock());
            pstmt.setInt(4, goods.getMinStock());
            pstmt.setBigDecimal(5, goods.getPriceIn());
            pstmt.setString(6, goods.getGimg());
            pstmt.setInt(7, goods.getGid());
            rows = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConn(conn, pstmt);
        }
        return rows;
    }
}