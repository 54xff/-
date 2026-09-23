import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class StatDialog extends JDialog {
    public StatDialog(JFrame parent){
        super(parent,"库存统计",true);
        setSize(350,200);
        setLocationRelativeTo(parent);
        setLayout(new FlowLayout());
        JButton btnMonth = new JButton("本月统计");
        JLabel labRes = new JLabel("结果：");
        add(btnMonth);
        add(labRes);
        btnMonth.addActionListener(e->{
            Map<String,Object> map = new StockStatDAO().getStat();
            labRes.setText("进货总量："+map.get("inTotal")+" 销售总量："+map.get("outTotal"));
        });
    }
}