import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;

public class SaleDialog extends JDialog {
    private JTextField customerIdField;
    private JTextField goodsIdField;
    private JTextField numField;
    private JTextField priceField;

    public SaleDialog(JFrame parent) {
        super(parent, "录入销售单", true);
        setSize(320, 240);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(5, 2, 8, 8));
        setResizable(false);

        add(new JLabel("客户ID："));
        customerIdField = new JTextField();
        add(customerIdField);

        add(new JLabel("商品ID："));
        goodsIdField = new JTextField();
        add(goodsIdField);

        add(new JLabel("销售数量："));
        numField = new JTextField();
        add(numField);

        add(new JLabel("销售单价："));
        priceField = new JTextField();
        add(priceField);

        JButton saveBtn = new JButton("保存销售单");
        add(saveBtn);
        add(new JLabel());

        saveBtn.addActionListener(e -> {
            try {
                String cidStr = customerIdField.getText().trim();
                String gidStr = goodsIdField.getText().trim();
                String numStr = numField.getText().trim();
                String priceStr = priceField.getText().trim();

                if (cidStr.isEmpty() || gidStr.isEmpty() || numStr.isEmpty() || priceStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "所有输入框不能为空！");
                    return;
                }

                int cid = Integer.parseInt(cidStr);
                int gid = Integer.parseInt(gidStr);
                int num = Integer.parseInt(numStr);
                BigDecimal price = new BigDecimal(priceStr);

                if (num <= 0) {
                    JOptionPane.showMessageDialog(this, "销售数量必须大于0");
                    return;
                }

                SaleItem item = new SaleItem();
                item.setGid(gid);
                item.setNum(num);
                item.setPrice(price);

                ArrayList<SaleItem> list = new ArrayList<>();
                list.add(item);

                // 自动计算销售总金额
                BigDecimal total = price.multiply(new BigDecimal(num));

                Sale sale = new Sale();
                sale.setCid(cid);
                sale.setItemList(list);
                sale.setTotalMoney(total);

                SaleDAO dao = new SaleDAO();
                boolean ok = dao.addSale(sale);
                if (ok) {
                    JOptionPane.showMessageDialog(this, "销售成功，总金额：" + total);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "销售失败，库存不足或数据库错误");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID、数量必须为数字，单价为小数");
            }
        });
    }
}