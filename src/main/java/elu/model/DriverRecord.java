package elu.model;

public class DriverRecord {
    private Integer id;

    private Integer userId;

    private Integer carId;

    private String dStart;

    private String dStartStr;

    private String dEnd;

    private String dEndStr;

    private Integer dCount;

    private Long dDate;

    private Double dPrice;

    private String dRemark;

    private Integer dStatus;

    private Long createTime;
    
    private Integer userStatus;
    private Integer carStatus ;
    
    private Integer status;

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

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getdStart() {
        return dStart;
    }

    public void setdStart(String dStart) {
        this.dStart = dStart;
    }

    public String getdStartStr() {
        return dStartStr;
    }

    public void setdStartStr(String dStartStr) {
        this.dStartStr = dStartStr;
    }

    public String getdEnd() {
        return dEnd;
    }

    public void setdEnd(String dEnd) {
        this.dEnd = dEnd;
    }

    public String getdEndStr() {
        return dEndStr;
    }

    public void setdEndStr(String dEndStr) {
        this.dEndStr = dEndStr;
    }

    public Integer getdCount() {
        return dCount;
    }

    public void setdCount(Integer dCount) {
        this.dCount = dCount;
    }

    public Long getdDate() {
        return dDate;
    }

    public void setdDate(Long dDate) {
        this.dDate = dDate;
    }

    public Double getdPrice() {
        return dPrice;
    }

    public void setdPrice(Double dPrice) {
        this.dPrice = dPrice;
    }

    public String getdRemark() {
        return dRemark;
    }

    public void setdRemark(String dRemark) {
        this.dRemark = dRemark;
    }

    public Integer getdStatus() {
        return dStatus;
    }

    public void setdStatus(Integer dStatus) {
        this.dStatus = dStatus;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

	public Integer getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}

	public Integer getCarStatus() {
		return carStatus;
	}

	public void setCarStatus(Integer carStatus) {
		this.carStatus = carStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
    
    
}