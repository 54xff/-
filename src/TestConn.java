import java.sql.Connection;

public class TestConn {
    public static void main(String[] args) {
        Connection conn = null;
        try {
            conn = DBUtil.getConn();
            if(conn != null){
                System.out.println("✅数据库连接成功！");
            }else{
                System.out.println("❌连接失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌连接异常");
        }finally {
            DBUtil.closeConn(conn,null,null);
        }
    }
}