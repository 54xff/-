import javax.swing.*;
import java.awt.*;

public class StockStatFrame extends JFrame {
    private JLabel statLabel;
    private JButton btnMonth;

    public StockStatFrame() {
        setTitle("库存统计");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 30));

        btnMonth = new JButton("本月统计");
        statLabel = new JLabel("进货总量：0  销售总量：0");
        statLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));

        add(btnMonth);
        add(statLabel);

        // 按钮点击事件
        btnMonth.addActionListener(e -> {
            StockStatDAO dao = new StockStatDAO();
            long inTotal = dao.getMonthInTotal();
            long outTotal = dao.getMonthOutTotal();
            statLabel.setText("进货总量：" + inTotal + "  销售总量：" + outTotal);
        });
    }
}