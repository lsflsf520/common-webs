package com.yisi.stiku.basedata.entity;

import com.yisi.stiku.common.bean.BaseEntity;

public class TblUserDataPriv extends BaseEntity<Integer> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Long userId;

    private Integer provinceId;

    private Integer cityId;

    private Integer countyId;

    private Integer schoolId;

    private Integer classId;
    
    private String userName;
    
    private String provinceName;

    private String cityName;

    private String countyName;

    private String schoolName;
    
    private Integer userType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    

    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCountyId() {
        return countyId;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }
    
    public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public Integer getUserType() {
		return this.userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

//	public UserType getDbUserType() {
//		return dbUserType;
//	}
//
//	public void setDbUserType(UserType dbUserType) {
//		this.dbUserType = dbUserType;
//	}

	@Override
    public Integer getPK() {
        return id;
    }
}