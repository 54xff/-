import java.math.BigDecimal;
import java.util.ArrayList;

public class PurchaseOrder {
    private int sid;
    private ArrayList<PurchaseItem> itemList;
    private BigDecimal totalMoney;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public ArrayList<PurchaseItem> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<PurchaseItem> itemList) {
        this.itemList = itemList;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }
}