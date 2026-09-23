import java.math.BigDecimal;
import java.util.ArrayList;

public class Sale {
    private int cid;
    private ArrayList<SaleItem> itemList;
    private BigDecimal totalMoney;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public ArrayList<SaleItem> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<SaleItem> itemList) {
        this.itemList = itemList;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }
}