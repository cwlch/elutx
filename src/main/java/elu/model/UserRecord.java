package elu.model;

public class UserRecord {
    private Integer id;

    private Integer userId;

    private String uStart;

    private String uStartStr;

    private String uEnd;

    private String uEndStr;

    private Integer uCount;

    private Long uDate;

    private String remark;

    private Integer uStatus;

    private Long createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getuStart() {
        return uStart;
    }

    public void setuStart(String uStart) {
        this.uStart = uStart;
    }

    public String getuStartStr() {
        return uStartStr;
    }

    public void setuStartStr(String uStartStr) {
        this.uStartStr = uStartStr;
    }

    public String getuEnd() {
        return uEnd;
    }

    public void setuEnd(String uEnd) {
        this.uEnd = uEnd;
    }

    public String getuEndStr() {
        return uEndStr;
    }

    public void setuEndStr(String uEndStr) {
        this.uEndStr = uEndStr;
    }

    public Integer getuCount() {
        return uCount;
    }

    public void setuCount(Integer uCount) {
        this.uCount = uCount;
    }

    public Long getuDate() {
        return uDate;
    }

    public void setuDate(Long uDate) {
        this.uDate = uDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getuStatus() {
        return uStatus;
    }

    public void setuStatus(Integer uStatus) {
        this.uStatus = uStatus;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}