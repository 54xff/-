import javax.swing.*;
public class GoodsSwing extends JFrame {
    public GoodsSwing(){
        setTitle("商品详情");
        setSize(400,300);
        setLocationRelativeTo(null);
        add(new JLabel("商品图片管理、修改规格库存"));
    }
}