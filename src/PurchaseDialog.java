import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;

public class PurchaseDialog extends JDialog {
    private JTextField supplierIdField;
    private JTextField goodsIdField;
    private JTextField numField;
    private JTextField priceField;

    public PurchaseDialog(JFrame parent) {
        super(parent, "录入进货单", true);
        setSize(320, 240);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(5, 2, 8, 8));
        setResizable(false);

        add(new JLabel("供应商ID："));
        supplierIdField = new JTextField();
        add(supplierIdField);

        add(new JLabel("商品ID："));
        goodsIdField = new JTextField();
        add(goodsIdField);

        add(new JLabel("进货数量："));
        numField = new JTextField();
        add(numField);

        add(new JLabel("进货单价："));
        priceField = new JTextField();
        add(priceField);

        JButton saveBtn = new JButton("保存进货单");
        add(saveBtn);
        add(new JLabel());

        saveBtn.addActionListener(e -> {
            try {
                String sidStr = supplierIdField.getText().trim();
                String gidStr = goodsIdField.getText().trim();
                String numStr = numField.getText().trim();
                String priceStr = priceField.getText().trim();

                if (sidStr.isEmpty() || gidStr.isEmpty() || numStr.isEmpty() || priceStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "所有输入框不能为空！");
                    return;
                }

                int sid = Integer.parseInt(sidStr);
                int gid = Integer.parseInt(gidStr);
                int num = Integer.parseInt(numStr);
                BigDecimal price = new BigDecimal(priceStr);

                if (num <= 0) {
                    JOptionPane.showMessageDialog(this, "数量必须大于0");
                    return;
                }

                PurchaseItem item = new PurchaseItem();
                item.setGid(gid);
                item.setNum(num);
                item.setPrice(price);

                ArrayList<PurchaseItem> list = new ArrayList<>();
                list.add(item);

                // 自动计算总价
                BigDecimal total = price.multiply(new BigDecimal(num));

                PurchaseOrder order = new PurchaseOrder();
                order.setSid(sid);
                order.setItemList(list);
                order.setTotalMoney(total);

                PurchaseOrderDAO dao = new PurchaseOrderDAO();
                boolean ok = dao.addPurchaseOrder(order);
                if (ok) {
                    JOptionPane.showMessageDialog(this, "进货成功，总价：" + total);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "进货失败");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID、数量必须是数字，单价为小数");
            }
        });
    }
}