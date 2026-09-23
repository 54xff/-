import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    public MainFrame(){
        setTitle("仓储管理系统");
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar bar = new JMenuBar();
        JMenu menuBusiness = new JMenu("业务管理");
        JMenuItem miPurchase = new JMenuItem("进货单");
        JMenuItem miSale = new JMenuItem("销售单");
        JMenuItem miStockCheck = new JMenuItem("库存盘点");
        JMenuItem miStat = new JMenuItem("库存统计");
        JMenuItem miBackup = new JMenuItem("数据库备份恢复");

        menuBusiness.add(miPurchase);
        menuBusiness.add(miSale);
        menuBusiness.add(miStockCheck);
        menuBusiness.add(miStat);
        menuBusiness.add(miBackup);
        bar.add(menuBusiness);
        setJMenuBar(bar);

        miPurchase.addActionListener(e -> new PurchaseDialog(this).setVisible(true));
        miSale.addActionListener(e -> new SaleDialog(this).setVisible(true));
        miStockCheck.addActionListener(e -> new StockCheckFrame().setVisible(true));
        miStat.addActionListener(e -> new StatDialog(this).setVisible(true));
        miBackup.addActionListener(e -> new DbBackupFrame().setVisible(true));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->new MainFrame().setVisible(true));
    }
}