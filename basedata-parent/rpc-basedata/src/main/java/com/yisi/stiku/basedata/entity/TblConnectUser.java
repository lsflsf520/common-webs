package com.yisi.stiku.basedata.entity;

import org.apache.commons.lang.StringUtils;

import com.yisi.stiku.basedata.rpc.constant.ThirdLoginType;
import com.yisi.stiku.common.bean.BaseEntity;
import com.yisi.stiku.common.bean.EntityState;

public class TblConnectUser extends BaseEntity<Integer> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String openId;

    private String avatar;
    
    private String nick;
    
    private ThirdLoginType loginType;

    private String accessToken;

    private Long userId;

    private EntityState dbState;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public String getSource() {
        return loginType == null ? null : loginType.name();
    }

    public void setSource(String source) {
        if(StringUtils.isNotBlank(source)){
        	setLoginType(ThirdLoginType.valueOf(source.trim()));
        }
    }

    public ThirdLoginType getLoginType() {
		return loginType;
	}

	public void setLoginType(ThirdLoginType loginType) {
		this.loginType = loginType;
	}

	public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken == null ? null : accessToken.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public Byte getState() {
        return getDbState() == null ? null : getDbState().getDbCode();
    }

    public void setState(Byte state) {
        setDbState(EntityState.getByDbCode(state));
    }
    
    public EntityState getDbState() {
		return dbState;
	}

	public void setDbState(EntityState dbState) {
		this.dbState = dbState;
	}

	@Override
    public Integer getPK() {
        return id;
    }
}