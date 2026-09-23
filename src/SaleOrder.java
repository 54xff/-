import java.util.List;
public class SaleOrder {
    private Integer cid;
    private List<SaleItem> itemList;

    public Integer getCid() { return cid; }
    public void setCid(Integer cid) { this.cid = cid; }
    public List<SaleItem> getItemList() { return itemList; }
    public void setItemList(List<SaleItem> itemList) { this.itemList = itemList; }
}