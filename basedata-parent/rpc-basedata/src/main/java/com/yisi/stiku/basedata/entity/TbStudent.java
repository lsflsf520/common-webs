package com.yisi.stiku.basedata.entity;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yisi.stiku.common.bean.BaseEntity;

public class TbStudent extends BaseEntity<Long> {

	private final static Logger LOG = LoggerFactory.getLogger(TbStudent.class);
	private Long id;

	private Long classId;

	private Long schoolId;

	private String cardNum;

	private String password;

	private String realName;

	private String qqNumber;

	private Integer sex;

	private String email;

	private String phoneNum;

	private Integer freeType;

	private Integer testCount;

	private Integer gradeYear;

	private Date activeTime;

	private String pPassword;

	private Date lastLogin;

	private Integer loginCount;

	private Integer status;

	private Date birthday;

	private Integer indexActive;

	private Integer indexRate;

	private Integer indexMaster;

	private String userIcon;

	private Integer sType;

	private String parentTelephone;

	private String username;

	private String parentName;

	private String target;

	private String motto;

	private Integer classNo15;

	private Integer studentStatus;

	private String studyNum;

	private Integer studentSection;

	private Integer scoreSagmentStatus;

	public Long getId() {

		return id;
	}

	public void setId(Long id) {

		this.id = id;
	}

	public Long getClassId() {

		return classId;
	}

	public void setClassId(Long classId) {

		this.classId = classId;
	}

	public Long getSchoolId() {

		return schoolId;
	}

	public void setSchoolId(Long schoolId) {

		this.schoolId = schoolId;
	}

	public String getCardNum() {

		return cardNum;
	}

	public void setCardNum(String cardNum) {

		this.cardNum = cardNum == null ? null : cardNum.trim();
	}

	public String getPassword() {

		return password;
	}

	public void setPassword(String password) {

		this.password = password == null ? null : password.trim();
	}

	public String getRealName() {

		return realName;
	}

	public void setRealName(String realName) {

		this.realName = realName == null ? null : realName.trim();
	}

	public String getQqNumber() {

		return qqNumber;
	}

	public void setQqNumber(String qqNumber) {

		this.qqNumber = qqNumber == null ? null : qqNumber.trim();
	}

	public Integer getSex() {

		return sex;
	}

	public void setSex(Integer sex) {

		this.sex = sex;
	}

	public String getEmail() {

		return email;
	}

	public void setEmail(String email) {

		this.email = email == null ? null : email.trim();
	}

	public String getPhoneNum() {

		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {

		this.phoneNum = phoneNum == null ? null : phoneNum.trim();
	}

	public Integer getFreeType() {

		return freeType;
	}

	public void setFreeType(Integer freeType) {

		this.freeType = freeType;
	}

	public Integer getTestCount() {

		return testCount;
	}

	public void setTestCount(Integer testCount) {

		this.testCount = testCount;
	}

	public Integer getGradeYear() {

		return gradeYear;
	}

	public void setGradeYear(Integer gradeYear) {

		this.gradeYear = gradeYear;
	}

	public Date getActiveTime() {

		return activeTime;
	}

	public void setActiveTime(Date activeTime) {

		this.activeTime = activeTime;
	}

	public String getpPassword() {

		return pPassword;
	}

	public void setpPassword(String pPassword) {

		this.pPassword = pPassword == null ? null : pPassword.trim();
	}

	public Date getLastLogin() {

		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {

		this.lastLogin = lastLogin;
	}

	public Integer getLoginCount() {

		return loginCount;
	}

	public void setLoginCount(Integer loginCount) {

		this.loginCount = loginCount;
	}

	public Integer getStatus() {

		return status;
	}

	public void setStatus(Integer status) {

		this.status = status;
	}

	public Date getBirthday() {

		return birthday;
	}

	public void setBirthday(Date birthday) {

		this.birthday = birthday;
	}

	public Integer getIndexActive() {

		return indexActive;
	}

	public void setIndexActive(Integer indexActive) {

		this.indexActive = indexActive;
	}

	public Integer getIndexRate() {

		return indexRate;
	}

	public void setIndexRate(Integer indexRate) {

		this.indexRate = indexRate;
	}

	public Integer getIndexMaster() {

		return indexMaster;
	}

	public void setIndexMaster(Integer indexMaster) {

		this.indexMaster = indexMaster;
	}

	public String getUserIcon() {

		String[] parts = null;
		if (StringUtils.isBlank(this.userIcon)
				|| (parts = this.userIcon.split("/images")).length != 2) {
			// LOG.debug("illegal userIcon '" + this.userIcon + "' for userId "
			// + this.id);
			return this.userIcon;
		}
		return "/images" + parts[1];
	}

	public void setUserIcon(String userIcon) {

		this.userIcon = userIcon == null ? null : userIcon.trim();
	}

	public Integer getsType() {

		return sType;
	}

	public void setsType(Integer sType) {

		this.sType = sType;
	}

	public String getParentTelephone() {

		return parentTelephone;
	}

	public void setParentTelephone(String parentTelephone) {

		this.parentTelephone = parentTelephone == null ? null : parentTelephone.trim();
	}

	public String getUsername() {

		return username;
	}

	public void setUsername(String username) {

		this.username = username == null ? null : username.trim();
	}

	public String getParentName() {

		return parentName;
	}

	public void setParentName(String parentName) {

		this.parentName = parentName == null ? null : parentName.trim();
	}

	public String getTarget() {

		return target;
	}

	public void setTarget(String target) {

		this.target = target == null ? null : target.trim();
	}

	public String getMotto() {

		return motto;
	}

	public void setMotto(String motto) {

		this.motto = motto == null ? null : motto.trim();
	}

	public Integer getClassNo15() {

		return classNo15;
	}

	public void setClassNo15(Integer classNo15) {

		this.classNo15 = classNo15;
	}

	public Integer getStudentStatus() {

		return studentStatus;
	}

	public void setStudentStatus(Integer studentStatus) {

		this.studentStatus = studentStatus;
	}

	public String getStudyNum() {

		return studyNum;
	}

	public void setStudyNum(String studyNum) {

		this.studyNum = studyNum == null ? null : studyNum.trim();
	}

	public Integer getStudentSection() {

		return studentSection;
	}

	public void setStudentSection(Integer studentSection) {

		this.studentSection = studentSection;
	}

	public Integer getScoreSagmentStatus() {

		return scoreSagmentStatus;
	}

	public void setScoreSagmentStatus(Integer scoreSagmentStatus) {

		this.scoreSagmentStatus = scoreSagmentStatus;
	}

	@Override
	public Long getPK() {

		return id;
	}
}