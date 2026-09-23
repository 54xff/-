import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class StockCheckFrame extends JFrame {
    JTable table;
    DefaultTableModel model;
    JButton btnSave;

    public StockCheckFrame() {
        setTitle("库存盘点窗口");
        setSize(700, 450);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] cols = {"商品ID", "商品名称", "系统库存", "实际盘点数", "盘盈盘亏"};
        model = new DefaultTableModel(cols, 0){
            // 只有【实际盘点数】允许编辑
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // 单元格修改监听，自动计算盈亏
        table.getModel().addTableModelListener(e -> {
            int row = e.getFirstRow();
            int col = e.getColumn();
            if(col == 3){
                try{
                    int sysStock = Integer.parseInt(model.getValueAt(row,2).toString());
                    String realStr = model.getValueAt(row,3).toString();
                    int realStock = Integer.parseInt(realStr);
                    int diff = realStock - sysStock;
                    model.setValueAt(diff, row,4);
                }catch (Exception ignored){
                    model.setValueAt("", row,4);
                }
            }
        });

        // 底部保存按钮
        JPanel panelBottom = new JPanel();
        btnSave = new JButton("确认保存盘点，更新库存");
        panelBottom.add(btnSave);
        add(panelBottom, BorderLayout.SOUTH);

        btnSave.addActionListener(e -> saveCheckData());
        loadGoodsData();
    }

    void loadGoodsData() {
        String sql = "SELECT gid, gname, stock FROM goods";
        try (Connection conn = DBUtil.getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("gid"),
                        rs.getString("gname"),
                        rs.getInt("stock"),
                        "",
                        ""
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "加载商品库存失败");
        }
    }

    void saveCheckData(){
        String sqlUpdate = "UPDATE goods SET stock=? WHERE gid=?";
        try(Connection conn = DBUtil.getConn()){
            conn.setAutoCommit(false);
            for(int i=0;i<model.getRowCount();i++){
                Object realObj = model.getValueAt(i,3);
                if(realObj == null) continue;
                String realStr = realObj.toString().trim();
                if(realStr.isEmpty()) continue;

                int gid = Integer.parseInt(model.getValueAt(i,0).toString());
                int realStock = Integer.parseInt(realObj.toString());

                PreparedStatement pstmt = conn.prepareStatement(sqlUpdate);
                pstmt.setInt(1, realStock);
                pstmt.setInt(2, gid);
                pstmt.executeUpdate();
            }
            conn.commit();
            JOptionPane.showMessageDialog(this,"盘点保存成功，库存已更新！");
        }catch (Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,"盘点保存失败！");
        }
    }
}