import javax.swing.*;
public class GoodsMenu extends JFrame {
    public GoodsMenu(){
        setTitle("商品管理");
        setSize(400,300);
        setLocationRelativeTo(null);
        add(new JLabel("商品维护页面，增删改查商品"));
    }
}