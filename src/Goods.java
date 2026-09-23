import java.math.BigDecimal;
public class Goods {
    private Integer gid;
    private String gname;
    private String spec;
    private Integer stock;
    private Integer minStock;
    private BigDecimal priceIn;
    private String gimg;

    public Integer getGid() { return gid; }
    public void setGid(Integer gid) { this.gid = gid; }
    public String getGname() { return gname; }
    public void setGname(String gname) { this.gname = gname; }
    public String getSpec() { return spec; }
    public void setSpec(String spec) { this.spec = spec; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    public Integer getMinStock() { return minStock; }
    public void setMinStock(Integer minStock) { this.minStock = minStock; }
    public BigDecimal getPriceIn() { return priceIn; }
    public void setPriceIn(BigDecimal priceIn) { this.priceIn = priceIn; }
    public String getGimg() { return gimg; }
    public void setGimg(String gimg) { this.gimg = gimg; }
}