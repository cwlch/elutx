package elu.model;

public class VerifyCode {
    private Integer id;

    private String uid;

    private Long updateTime;

    private Long createTime;

    private String vfCode;

    private String vfType;

    private Integer sendtimes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getVfCode() {
        return vfCode;
    }

    public void setVfCode(String vfCode) {
        this.vfCode = vfCode;
    }

    public String getVfType() {
        return vfType;
    }

    public void setVfType(String vfType) {
        this.vfType = vfType;
    }

    public Integer getSendtimes() {
        return sendtimes;
    }

    public void setSendtimes(Integer sendtimes) {
        this.sendtimes = sendtimes;
    }
}