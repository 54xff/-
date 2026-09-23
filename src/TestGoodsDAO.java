import java.math.BigDecimal;

public class TestGoodsDAO {
    public static void main(String[] args) {
        GoodsDAO dao = new GoodsDAO();

        // 删除 id=1 的商品
        int ret = dao.delete(1);
        if(ret > 0){
            System.out.println("删除成功");
            // 删除后查询验证
            System.out.println(dao.findById(1));
        }else{
            System.out.println("删除失败，该id不存在");
        }
    }
}