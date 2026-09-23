import javax.swing.*;
import java.awt.*;

public class DbBackupFrame extends JFrame {
    public DbBackupFrame(){
        setTitle("数据库备份与恢复");
        setSize(350,200);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        JButton btnBackup = new JButton("一键备份");
        JButton btnRestore = new JButton("恢复备份");
        JProgressBar bar = new JProgressBar();
        bar.setPreferredSize(new Dimension(250,20));
        add(btnBackup);
        add(btnRestore);
        add(bar);

        btnBackup.addActionListener(e->{
            new Thread(()->{
                bar.setValue(0);
                try {
                    bar.setValue(50);
                    boolean ok = DbBackupUtil.backup("c:/cangchu_backup.sql");
                    bar.setValue(100);
                    if(ok) JOptionPane.showMessageDialog(this,"备份完成");
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(this,"备份失败");
                }
            }).start();
        });

        btnRestore.addActionListener(e->{
            new Thread(()->{
                bar.setValue(0);
                try {
                    bar.setValue(50);
                    boolean ok = DbBackupUtil.restore("c:/cangchu_backup.sql");
                    bar.setValue(100);
                    if(ok) JOptionPane.showMessageDialog(this,"恢复完成");
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(this,"恢复失败");
                }
            }).start();
        });
    }
}