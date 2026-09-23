import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class StockStatDAO {

    public Map<String,Object> getStat(){
        Map<String,Object> map = new HashMap<>();
        map.put("inTotal",0L);
        map.put("outTotal",0L);

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        long inTotal = 0;
        long outTotal = 0;

        try{
            conn = DBUtil.getConn();
            String sqlIn = "SELECT IFNULL(SUM(pi.num),0) inTotal " +
                    "FROM purchase p LEFT JOIN purchaseitem pi ON p.pid = pi.pid " +
                    "WHERE DATE_FORMAT(p.ptime,'%Y-%m') = DATE_FORMAT(NOW(),'%Y-%m')";
            pstmt = conn.prepareStatement(sqlIn);
            rs = pstmt.executeQuery();
            if(rs.next()){
                inTotal = rs.getLong("inTotal");
            }
            rs.close();
            pstmt.close();

            String sqlOut = "SELECT IFNULL(SUM(si.num),0) outTotal " +
                    "FROM saleitem si LEFT JOIN sale s ON si.saleid = s.saleid " +
                    "WHERE DATE_FORMAT(s.saletime,'%Y-%m') = DATE_FORMAT(NOW(),'%Y-%m')";
            pstmt = conn.prepareStatement(sqlOut);
            rs = pstmt.executeQuery();
            if(rs.next()){
                outTotal = rs.getLong("outTotal");
            }
            map.put("inTotal",inTotal);
            map.put("outTotal",outTotal);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{ if(rs!=null) rs.close(); }catch (Exception e){}
            try{ if(pstmt!=null) pstmt.close(); }catch (Exception e){}
            try{ if(conn!=null) conn.close(); }catch (Exception e){}
        }
        return map;
    }

    public long getMonthInTotal() {
        long total = 0;
        String sql = "SELECT IFNULL(SUM(pi.num),0) " +
                "FROM purchase p LEFT JOIN purchaseitem pi ON p.pid = pi.pid " +
                "WHERE DATE_FORMAT(p.ptime,'%Y-%m') = DATE_FORMAT(NOW(),'%Y-%m')";
        try (Connection conn = DBUtil.getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                total = rs.getLong(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    public long getMonthOutTotal() {
        long total = 0;
        String sql = "SELECT IFNULL(SUM(si.num),0) " +
                "FROM saleitem si LEFT JOIN sale s ON si.saleid = s.saleid " +
                "WHERE DATE_FORMAT(s.saletime,'%Y-%m') = DATE_FORMAT(NOW(),'%Y-%m')";
        try (Connection conn = DBUtil.getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                total = rs.getLong(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }
}