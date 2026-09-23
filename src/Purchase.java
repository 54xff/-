import java.math.BigDecimal;
import java.util.Date;
public class Purchase {
    private Integer pid;
    private Integer gid;
    private Integer pNum;
    private BigDecimal pPrice;
    private Integer sid;
    private Date pDate;

    public Integer getPid() { return pid; }
    public void setPid(Integer pid) { this.pid = pid; }
    public Integer getGid() { return gid; }
    public void setGid(Integer gid) { this.gid = gid; }
    public Integer getpNum() { return pNum; }
    public void setpNum(Integer pNum) { this.pNum = pNum; }
    public BigDecimal getpPrice() { return pPrice; }
    public void setpPrice(BigDecimal pPrice) { this.pPrice = pPrice; }
    public Integer getSid() { return sid; }
    public void setSid(Integer sid) { this.sid = sid; }
    public Date getpDate() { return pDate; }
    public void setpDate(Date pDate) { this.pDate = pDate; }
}