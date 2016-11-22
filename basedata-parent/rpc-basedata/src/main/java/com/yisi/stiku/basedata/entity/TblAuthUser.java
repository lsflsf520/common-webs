package com.yisi.stiku.basedata.entity;

import com.yisi.stiku.common.bean.BaseEntity;
import com.yisi.stiku.common.bean.EntityState;
import com.yisi.stiku.common.checker.annotation.Validation;
import com.yisi.stiku.common.checker.constant.RequiredType;
import com.yisi.stiku.common.utils.UserInfoUtil;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class TblAuthUser extends BaseEntity<Long> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String realName;

    private String nick;

    private String signName;

    private String email;

    private String mobile;

    private String password;

    private String lastLogonIp;

    private Date lastLogonTime;

    private Date createTime;

    private Date lastUptime;

    private Integer type;

//    private Byte state;
//    private UserType dbType;
    private EntityState dbState;

    private Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRealName() {
    	return realName;
    }
    
    public String getShowName(){
    	if(StringUtils.isNotBlank(realName)){
    		return realName;
    	}else if(StringUtils.isNotBlank(nick)){
    		return nick;
    	}
    	
        return signName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick == null ? null : nick.trim();
    }

    @Validation(required=RequiredType.INSERT, length="[2,30]", fieldCnName="用户名")
    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName == null ? null : signName.trim();
    }

    @Validation(email=true, fieldCnName="邮箱")
    public String getEmail() {
        return StringUtils.isBlank(email) ? null : email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    @Validation(mobile=true, fieldCnName="手机号")
    public String getMobile() {
        return StringUtils.isBlank(mobile) ? null : mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getLastLogonIp() {
        return lastLogonIp;
    }

    public void setLastLogonIp(String lastLogonIp) {
        this.lastLogonIp = lastLogonIp == null ? null : lastLogonIp.trim();
    }

    public Date getLastLogonTime() {
        return lastLogonTime;
    }

    public void setLastLogonTime(Date lastLogonTime) {
        this.lastLogonTime = lastLogonTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUptime() {
        return lastUptime;
    }

    public void setLastUptime(Date lastUptime) {
        this.lastUptime = lastUptime;
    }

    /**
     * 
     * @return 返回用户类型
     * @see com.yisi.stiku.basedata.rpc.constant.UserType
     */
    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
//    	this.dbType = UserType.getByDbCode(type);
    }

    public Byte getState() {
        return getDbState() == null ? null : getDbState().getDbCode();
    }

    public void setState(Byte state) {
//        this.state = state;
    	this.dbState = EntityState.getByDbCode(state);
    }
    
//    public UserType getDbType() {
//		return dbType;
//	}
//
//	public void setDbType(UserType dbType) {
//		this.dbType = dbType;
//	}

	public EntityState getDbState() {
		return dbState;
	}

	public void setDbState(EntityState dbState) {
		this.dbState = dbState;
	}

	public Integer getVersion() {
        return version == null ? 0 : version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
    
    public boolean isTeacher(){
    	return UserInfoUtil.isTeacher(this.getType());
    }
    
    public boolean isStudent(){
    	return UserInfoUtil.isStudent(this.getType());
    }
    
    public boolean isAgent(){
    	return UserInfoUtil.isAgent(this.getType());
    }
    
    public boolean isSuperAdmin(){
    	return UserInfoUtil.isSuperAdmin(this.getType());
    }
    
    public boolean isCoach(){
    	return UserInfoUtil.isCoach(this.getType());
    }
    
    @Override
    public Long getPK() {
        return id;
    }
}