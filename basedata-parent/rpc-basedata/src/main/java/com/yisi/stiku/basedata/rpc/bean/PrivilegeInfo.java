package com.yisi.stiku.basedata.rpc.bean;

/**
 * 
 * @author shangfeng
 *
 */
public class PrivilegeInfo {

	private String id;
	
	private String aclType;
	
	private Integer version;

	private String category;
	
	private Integer orderRank;
	
	private String url;
	
	private String title;
	
	private String code;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAclType() {
		return aclType;
	}

	public void setAclType(String aclType) {
		this.aclType = aclType;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getOrderRank() {
		return orderRank;
	}

	public void setOrderRank(Integer orderRank) {
		this.orderRank = orderRank;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
