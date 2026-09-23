import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PurchaseDAO {
    public List<Purchase> findAll(){
        List<Purchase> list = new ArrayList<>();
        Connection conn = DBUtil.getConn();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM purchase";
        try{
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while(rs.next()){
                Purchase pur = new Purchase();
                pur.setPid(rs.getInt("pid"));
                pur.setGid(rs.getInt("gid"));
                pur.setpNum(rs.getInt("p_num"));
                pur.setpPrice(rs.getBigDecimal("p_price"));
                pur.setSid(rs.getInt("sid"));
                pur.setpDate(rs.getTimestamp("p_date"));
                list.add(pur);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUtil.closeConn(conn,pstmt,rs);
        }
        return list;
    }
}